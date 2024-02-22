package moviesapp.controller.command_line;

import moviesapp.model.TmdbAPI;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.controller.command_line.CLController.*;

public class CLSearch extends CLMethods {

    protected final TmdbAPI apiObject = new TmdbAPI();

    /**
     * Ask title, a year span, vote average and genres information to the user to select a specific group of movies
     */
    void searchCommand(){
        String title = "";
        String minYear;
        String maxYear = "";
        String voteAverage = "";
        List<String> genres = new ArrayList<>();
        if(askToConfirm("Do you know the title?")){
            title = askValue("Title of the movie: ");
            minYear = askValue("Year of release: ");
        }
        else{
            minYear = askValue("Min year of release: ");
            maxYear = askValue("Max year of release: ");
            voteAverage = askValue("Movie's minimum rate: ");
            genres = specifiedGenres(apiObject);
        }
        if(title.isEmpty() && minYear.isEmpty() && maxYear.isEmpty() && voteAverage.isEmpty() && genres.isEmpty()){
            System.out.println("\n| No information sent. \n| Please give me more details for your next search.");
        }
        else{
            apiObject.searchMovies(title, minYear, maxYear, genres, voteAverage , "1");
            do{
                jsonReaderUpdate();
                System.out.println("\nYour list of movies found in your search: \n" + jsonReader.findAllMovies());
            } while(askPreviousOrNextPage(title,minYear, maxYear,genres,voteAverage, messageOfAskPreviousOrNextPage()));
        }
    }

    /**
     * Return the user's specified genres
     * @param apiObject the api that contains all the genres available
     * @return the user's specified genres
     */
    //TODO
    private List<String> specifiedGenres(TmdbAPI apiObject){
        List<String> genres = new ArrayList<>();

        if(askToConfirm("Do you want to specify one or more genres?")){
            System.out.println("\nList of genres: \n" + apiObject.genreList());

            do{
                List<String> genreNames = new ArrayList<>(TmdbAPI.GENRE_NAME_ID_MAP.keySet());
                String genreName = askValue("Enter genre name: ").trim().toLowerCase();
                genreName = genreName.substring(0,1).toUpperCase() + genreName.substring(1);
                if (TmdbAPI.GENRE_NAME_ID_MAP.containsKey(genreName)) {
                    genres.add(genreName);
                }
                else {
                    System.out.println("\n| Genre not found. Please enter a valid genre.");
                }
            } while(askToConfirm("Do you want to add more genres?"));
        }
        return genres;
    }

    /*
    /**
     * Selects a movie from the list based on the provided index.
     * @param movies The list of movies to select from.
     * @param message The message to display prompting the user for input.
     * @return The selected movie.
     */
    /*
    private Movies selectGenreByIndex(List<String> genres, String message) {
        for (;;) {
            try {
                int index = Integer.parseInt(askValue(message));
                if (isValidIndex(index, genres.size())) {
                    return getSelectedMovie(movies, index);
                } else {
                    printIndexErrorMessage();
                }
            } catch (NumberFormatException e) {
                printIndexErrorMessage();
            }
        }
    }
    */

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
