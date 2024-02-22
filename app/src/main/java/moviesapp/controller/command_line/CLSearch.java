package moviesapp.controller.command_line;

import moviesapp.model.movies.Movies;
import moviesapp.model.api.TheMovieDbAPI;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.controller.command_line.CLController.*;

public class CLSearch extends CLMethods {

    protected final TheMovieDbAPI apiObject = new TheMovieDbAPI();

    /**
     * Ask title, a year span, vote average and genres information to the user to select a specific group of movies
     */
    void searchCommand(){

        String title = askValue("Title of the movie: ");
        String yearOfReleaseOption = askValue("Select release year option: [0] Skip, [1] Single, [2] Range (min-max)");
        String singleYearOrMinYear = "";
        String maxYear = "";

        switch (yearOfReleaseOption){
            case "1" -> singleYearOrMinYear = askValue("Release year: ");
            case "2" -> {
                singleYearOrMinYear = askValue("Min release year: ");
                maxYear = askValue("Max release year: ");
            }
            default -> {
            }
        }

        String voteAverage = askValue("Movie's minimum rate: ");
        List<String> genres = specifiedGenres(apiObject);

        if(title.isEmpty() && singleYearOrMinYear.isEmpty() && maxYear.isEmpty() && voteAverage.isEmpty() && genres.isEmpty()){
            System.out.println("\n| No information sent. \n| Please give me more details for your next search.");
        }
        else{
            apiObject.searchMovies(title, singleYearOrMinYear, maxYear, genres, voteAverage , "1");
            do{
                jsonReaderUpdate();
                Movies moviesFromSearch = jsonReader.findAllMovies();
                System.out.println("\nYour list of movies found in your search: \n" + moviesFromSearch);
                if(Movies.noMovieFound(moviesFromSearch)){
                    break;
                }
            } while(askPreviousOrNextPage(title, singleYearOrMinYear, maxYear, genres, voteAverage, messageOfAskPreviousOrNextPage()));
        }
    }

    /**
     * Return the user's specified genres
     * @param apiObject the api that contains all the genres available
     * @return the user's specified genres
     */
    private List<String> specifiedGenres(TheMovieDbAPI apiObject){
        List<String> genres = new ArrayList<>();

        if(askToConfirm("Do you want to specify one or more genres?")){
            System.out.print("\nList of genres: \n" + apiObject.genreList());

            do{
                System.out.println("\nGenres already selected: " + genres);
                List<String> genreNames = new ArrayList<>(TheMovieDbAPI.GENRE_NAME_ID_MAP.keySet());
                String genreSelected = selectGenreByIndex(genreNames);

                if (TheMovieDbAPI.GENRE_NAME_ID_MAP.containsKey(genreSelected) && !genres.contains(genreSelected)) {
                    genres.add(genreSelected);
                }
                else {
                    System.out.println("\n| No genre added. Please enter a valid genre that is not already selected.");
                }
            } while(askToConfirm("Do you want to add more genres?"));
        }
        return genres;
    }

    /**
     * Selects a genre from the list based on the provided index.
     * @param genres The list of genres to select from.
     * @return The selected movie.
     */
    private String selectGenreByIndex(List<String> genres) {
        for (;;) {
            try {
                int index = Integer.parseInt(askValue("Enter genre index: ")) - 1;
                if (isValidIndex(index, genres.size())) {
                    return genres.get(index);
                } else {
                    printIndexErrorMessage();
                }
            } catch (NumberFormatException e) {
                printIndexErrorMessage();
            }
        }
    }

    /**
     * Ask the user to select an interaction with page management system (previous, next, stop)
     * @param title from the precedent search
     * @param minYear from the precedent search
     * @param maxYear from the precedent search
     * @param genres from the precedent search
     * @param voteAverage from the precedent search
     * @param message the message to print to the user to interact with page management system
     * @return the user's answer
     */
    protected boolean askPreviousOrNextPage(String title, String minYear, String maxYear, List<String> genres, String voteAverage , String message){
        String response = askValue(message);

        switch (response) {
            case "3" -> {
                int pageNumber = Integer.parseInt(askValue("Enter page number: "));
                if (pageNumber >= 1 && pageNumber <= jsonReader.numberOfPagesOfMoviesInJson()){
                    apiObject.searchMovies(title, minYear, maxYear, genres, voteAverage, String.valueOf(pageNumber));
                    return true;
                }
                System.out.println("\n| Page number unavailable.");
            }
            case "2" -> {
                if(jsonReader.getPageInJson() < jsonReader.numberOfPagesOfMoviesInJson()){
                    apiObject.searchMovies(title, minYear, maxYear, genres, voteAverage, String.valueOf(jsonReader.getPageInJson() + 1));
                    return true;
                }
                System.out.println("\n| There is no next page.");
            }
            case "1" -> {
                if(jsonReader.getPageInJson() > 1){
                    apiObject.searchMovies(title, minYear, maxYear, genres, voteAverage, String.valueOf(jsonReader.getPageInJson() - 1));
                    return true;
                }
                System.out.println("\n| There is no precedent page.");
            }
            case "0" -> {
                return false;
            }
            default -> System.out.println("\n| Please enter a valid option.");
        }

        return askPreviousOrNextPage(title, minYear, maxYear, genres, voteAverage , message);
    }

    /**
     * Return the message corresponding to the page management user interactive
     * @return the message corresponding to the page management user interactive
     */
    protected String messageOfAskPreviousOrNextPage(){
        return "Choose your action: [0] Continue/Leave command, [1] Previous Page, [2] Next Page, [3] Specify Page | page ("
                + jsonReader.getPageInJson()
                + "/"
                + jsonReader.numberOfPagesOfMoviesInJson() +")";
    }
}
