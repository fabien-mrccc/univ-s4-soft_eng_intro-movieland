package moviesapp.controller.command_line;

import moviesapp.model.api.Genres;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.*;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.*;

import static moviesapp.model.api.Genres.GENRE_NAME_ID_MAP;
import static moviesapp.model.api.Genres.genresToGenreIds;
import static moviesapp.model.movies.Movies.moviesFromPreviousSearch;
import static moviesapp.model.api.UrlRequestBuilder.*;
import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.json.JsonReader.SEARCH_READER;
import static moviesapp.model.json.JsonReader.updateSearchReader;
import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.json.JsonWriter.SEARCH_WRITER;
import static moviesapp.model.movies.Favorites.addByIndex;
import static moviesapp.model.movies.Movies.selectMovieByIndex;
import static moviesapp.model.exceptions.IndexException.isValidIndex;

public final class CLController {

    private CLHelp help;
    private final TheMovieDbAPI apiObject = new TheMovieDbAPI();
    private final Scanner scanner = new Scanner(System.in);


    public CLController() {
        initCommands();
    }

    /**
     * Initializes commands.
     * This method is responsible for initializing command-related objects.
     */
    private void initCommands(){
        help = new CLHelp();
        help.setupHelpCommandsDescription();
    }

    /**
     * Select a method to execute based on user input and execute it
     */
    public void select() {
        SEARCH_WRITER.clean();
        boolean exitRequested = false;

        do {
            help.helpCommand();

            String commandValue = askValue("\nInput your command: ").toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch (commandValue) {
                case "1":
                    catalogCommand();
                    break;

                case "2":
                    searchCommand();
                    break;

                case "3":
                    detailsCommand();
                    break;

                case "4":
                    addCommand();
                    break;

                case "5":
                    removeCommand();
                    break;

                case "6":
                    favoritesCommand();
                    break;

                case "7":
                    clearCommand();
                    break;

                case "8":
                    try {
                        exitCommand();
                    } catch (ExitException e) {
                        exitRequested = true;
                    }
                    break;

                default:
                    System.out.println("*** Command '" + commandValue + "' doesn't exist. ***");
                    break;
            }

        } while (!exitRequested);

        System.exit(0);
    }

    /**
     * Executes the add command, allowing the user to add movies to their favorites.
     */
    private void addCommand(){
        System.out.println("Add command has been started.");
        Movies movies = moviesFromPreviousSearch();

        try {
            Movies.searchableMovie(movies);
        }
        catch (NoMovieFoundException e) {
            System.out.println("\n| Make sure to use search command before add command, otherwise your search result was null.");
            return;
        }

        boolean addSuccess = false;

        while (!addSuccess) {
            try {
                addCommandTry(movies);
                addSuccess = true;
            } catch (IndexException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Attempts to add movies to favorites by index from the given list of movies.
     *
     * @param movies the list of movies from which to select movies to add.
     * @throws IndexException if an invalid index is provided during the addition process.
     */
    private void addCommandTry(Movies movies) throws IndexException {

        do{
            addByIndex(movies, retrieveAsInt("Index of the movie to add to your favorites: "));
        }
        while(askToConfirm("Do you want to add another movie?"));

        System.out.println();
        favoritesCommand();
    }

    /**
     * Displays the user's favorite movie list.
     */
    private void favoritesCommand(){
        System.out.print("Your favorite list:\n" + Favorites.instance);
    }

    /**
     * Asks the user to confirm an action.
     *
     * @param string the prompt to display asking for confirmation.
     * @return {@code true} if the user confirms (enters 'Y' or 'y'), {@code false} otherwise.
     */
    private boolean askToConfirm(String string){
        String answer;
        do{
            answer = askValue(string + " [Y/n]: ").trim().toLowerCase();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Asks the user for input with the given message and returns the input as a String.
     *
     * @param message the message to display prompting the user for input.
     * @return the user's input as a String.
     */
    private String askValue(String message){
        System.out.println(message);
        return scanner.nextLine();
    }



    /**
     * Remove to the favorites the movie chosen by the user
     */
    void removeCommand() {
        System.out.println("Remove command has been started.\n");

        try {
            do{
                favoritesCommand() ;
                System.out.println();
                Movies movies = Favorites.asMovies() ;

                Movies.searchableMovie(movies);

                if (movies.size() > 1){
                    removeMovieByIndex(movies);
                }
                else{
                    Favorites.remove(movies.get(0));
                }
                FAVORITES_WRITER.saveFavorites(Favorites.asMovies());

            }
            while(askToConfirm("Do you want to remove another movie?"));
            System.out.println();
            favoritesCommand();
        }
        catch (NoMovieFoundException e){
            System.out.println(e.getMessage() + "\n| Be sure to have movies in your favorites before trying to use remove command.");
        }
        catch (IndexException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove a specific movie chosen by the user with the index of the movie to the favorite list
     * @param movies chosen to browse
     */
    private void removeMovieByIndex(Movies movies) throws NoMovieFoundException, IndexException {
        Favorites.remove(selectMovieByIndex(movies, retrieveAsInt("Index of the movie to remove from your favorites: ")));
    }

    /**
     * Search a specific group of movies and print their detailed information
     */
    void detailsCommand(){

        boolean selectModeSuccess = false;
        while(!selectModeSuccess){
            try{
                String detailsMode = selectMode("Choose details command mode: [1] From Search, [2] From Favorites", Arrays.asList("1","2"));
                Movies movieList;

                switch(detailsMode){
                    case "1" -> movieList = moviesFromPreviousSearch();
                    case "2" -> {
                        movieList = Favorites.asMovies();
                        System.out.println("\nBelow the movies from your favorites: \n" + movieList);
                    }
                    default -> {
                        System.out.println(new SelectModeException().getMessage());
                        return;
                    }
                }

                selectModeSuccess = true;


                    Movie movie = selectMovieByIndex(movieList, retrieveAsInt("Enter the index of the movie to see its details: "));
                    assert movie != null;
                    System.out.print(movie.details());


            }
            catch (IndexException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prompts the user with a message to enter an integer value.
     * Continuously prompts until a valid integer value is entered.
     *
     * @param message the message to display when prompting the user for input
     * @return the integer value entered by the user
     */
    private int retrieveAsInt(String message){

        for(;;) {
            try{
                return Integer.parseInt(askValue(message));
            }
            catch (NumberFormatException e){
                System.out.println("\n| Enter a value that is an integer to continue.");
            }
        }
    }

    /**
     * Converts the given string value to an integer.
     *
     * @param valueToConvert the string value to convert to an integer
     * @return the integer value of the converted string
     * @throws NotAnIntegerException if the string cannot be parsed as an integer
     */
    private int convertAsInt(String valueToConvert) throws NotAnIntegerException {
        try{
            return Integer.parseInt(valueToConvert);
        }
        catch (NumberFormatException e){
            throw new NotAnIntegerException();
        }
    }

    /**
     * Test if askToConfirm is true ,and if it is, clear the favorite list
     */
    void clearCommand() {
        if (askToConfirm("Are you sure that you want to delete your favorites?")){
            Favorites.clear();
            FAVORITES_WRITER.clean();
            System.out.println("Your favorite list has been cleared.");
        }
    }

    /**
     * Checks if the user wants to exit the application.
     * If confirmed, throws an ExitException.
     *
     * @throws ExitException if the user confirms the exit action
     */
    void exitCommand() throws ExitException {
        if(askToConfirm("Are you sure that you want to leave the application?")){
            throw new ExitException();
        }
    }

    /**
     * Ask title, a year span, vote average and genres information to the user to select a specific group of movies
     */
    void searchCommand(){
        String title = "";
        String singleYearOrMinYear = "";
        String maxYear ="";
        String minVoteAverage = "";
        List <String> genres = new ArrayList<>();

        boolean searchModeSuccess = false;

        while(!searchModeSuccess){
            try {
                searchMode = selectMode("Search mode: [0] Exit Search, ["+ searchModeSearch +"] With Title, ["+ searchModeDiscover +"] Without Title",
                        Arrays.asList("0", searchModeSearch,searchModeDiscover));
                searchModeSuccess = true;
            }
            catch (IndexException e ){
                System.out.println(e.getMessage());
            }
        }

        switch(searchMode){
            case "0" -> {
                return;
            }
            case "1" -> {
                while(title.isEmpty()){
                    title = askValue("Title of the movie (required): ");
                }
                singleYearOrMinYear = getYear();
                if(informationSent(title, singleYearOrMinYear, maxYear, minVoteAverage, genres)){
                    maxYear = SINGLE_MODE_KEYWORD;
                }
                else{
                    return;
                }
            }
            case "2" -> {
                String[] years = getYears();
                if (years != null && (!years[0].isEmpty() || !years[1].isEmpty())){
                    singleYearOrMinYear = years[0];
                    maxYear = years[1];
                }
                minVoteAverage = getMinVoteAverage(0,10);
                genres = genresToGenreIds(specifiedGenresByUser());
            }
            default -> {
                System.out.println(new SelectModeException().getMessage());
                return;
            }
        }
        try {
            if(informationSent(title, singleYearOrMinYear, maxYear, minVoteAverage, genres)){
                apiObject.searchMovies(title, singleYearOrMinYear, maxYear, genres, minVoteAverage, "1");
                do{
                    updateSearchReader();
                    Movies moviesFromSearch = SEARCH_READER.findAllMovies();
                    Movies.searchableMovie(moviesFromSearch);
                    System.out.println("\nYour list of movies found in your search: \n" + moviesFromSearch);

                } while(askPreviousOrNextPage(title, singleYearOrMinYear, maxYear, genres, minVoteAverage, messageOfAskPreviousOrNextPage()));
            }
        }
        catch (NoMovieFoundException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return the user's specified genres
     * @return the user's specified genres
     */
    private List<String> specifiedGenresByUser(){
        List<String> genres = new ArrayList<>();

        if(askToConfirm("Do you want to specify one or more genres?")){
            System.out.print("\nList of genres: \n" + Genres.instance.getGenres());

            do{
                System.out.println("\nGenres already selected: " + genres);
                List<String> genreNames = new ArrayList<>(GENRE_NAME_ID_MAP.keySet());
                String genreSelected = selectGenreByIndex(genreNames);

                if (GENRE_NAME_ID_MAP.containsKey(genreSelected) && !genres.contains(genreSelected)) {
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
     * Retrieves the release year based on user input.
     * @return An array containing the selected release year.
     */
    private String getYear() {

        String yearOfReleaseOption = selectYearMode("Select release year option: [0] Skip, [1] Specify", Arrays.asList("0","1"));

        switch (yearOfReleaseOption){
            case "0" -> {
                return "";
            }
            case "1" -> {
                return getYearTry("Release year [" + minAcceptableYearValue + "-" + maxAcceptableYearValue + "]: ");
            }
            default -> {
                System.out.println(new SelectModeException().getMessage());
                return "";
            }
        }
    }

    /**
     * Prompts the user to input a release year within a specified range.
     *
     * @return the release year entered by the user
     */
    private String getYearTry(String message){
        String releaseYear = "";
        boolean getYearSuccess = false;

        while (!getYearSuccess){
            try {
                releaseYear = askValue(message);

                if(!releaseYear.isEmpty()) {
                    validateValueInterval(convertAsInt(releaseYear), minAcceptableYearValue, maxAcceptableYearValue);
                    getYearSuccess = true;
                }
                getYearSuccess = true;

            } catch (IntervalException | NotAnIntegerException e){
                System.out.println(e.getMessage());
            }
        }

        return releaseYear;
    }

    private String selectYearMode(String message, List<String> optionNumbers){
        String yearOfReleaseOption = "";
        boolean selectModeSuccess = false;

        while(!selectModeSuccess){
            try{
                yearOfReleaseOption = selectMode(message, optionNumbers);
                selectModeSuccess = true;
            }
            catch(IndexException e){
                System.out.println(e.getMessage());
            }
        }

        return yearOfReleaseOption;
    }

    /**
     * Checks if no search information is provided.
     *
     * @param title             The title of the movie.
     * @param singleYearOrMinYear   The single year or minimum year in the range.
     * @param maxYear           The maximum year in the range.
     * @param minVoteAverage    The minimum vote average.
     * @param genres            The list of genres.
     * @return True if no information is sent for the search, false otherwise.
     */
    private boolean informationSent(String title, String singleYearOrMinYear, String maxYear, String minVoteAverage, List<String> genres){
        if(title.isEmpty() && singleYearOrMinYear.isEmpty() && maxYear.isEmpty() && minVoteAverage.isEmpty() && genres.isEmpty()){
            System.out.println("\n| No information sent. \n| Please give me more details for your next search.");
            return false;
        }
        return true;
    }

    /**
     * Retrieves the release years based on user input.
     * @return An array containing the selected release years. Null if skipped.
     */
    private String[] getYears(){

        String yearOfReleaseOption = selectYearMode("Select release year option: [0] Skip, [1] Single, [2] Range (min-max)", Arrays.asList("0","1","2"));

        String[] yearsFromTheUser = new String[2];

        switch (yearOfReleaseOption){
            case "0" -> {
                return null;
            }
            case "1" -> {
                yearsFromTheUser[0] = getYearTry("Release year [" + minAcceptableYearValue + "-" + maxAcceptableYearValue + "]: ");
                yearsFromTheUser[1] = SINGLE_MODE_KEYWORD;
            }
            case "2" -> {
                yearsFromTheUser[0] = getYearTry("Min release year [ ≧ " + minAcceptableYearValue + "]: ");
                yearsFromTheUser[1] = getYearTry("Max release year [ ≦ " + maxAcceptableYearValue + "]: ");
            }
            default -> {
                System.out.println(new SelectModeException().getMessage());
                return getYears();
            }
        }

        return yearsFromTheUser;
    }

    /**
     * Validates if a given value falls within the acceptable interval defined by minimum and maximum values.
     *
     * @param value               The value to be validated.
     * @param minAcceptableValue  The minimum acceptable value of the interval.
     * @param maxAcceptableValue  The maximum acceptable value of the interval.
     * @throws IntervalException  If the value is not within the acceptable interval.
     */
    private void validateValueInterval(double value, double minAcceptableValue, double maxAcceptableValue) throws IntervalException {

        boolean validNumber = value >= minAcceptableValue && value <= maxAcceptableValue;

        if (!validNumber){
            throw new IntervalException();
        }
    }

    /**
     * Retrieves the minimum vote average for a movie.
     * @return The minimum vote average provided by the user.
     */
    private String getMinVoteAverage(int minAcceptableValue, int maxAcceptableValue){

        String minVoteAverage = "";
        int minVoteAverageValue = 0;
        boolean getMinVoteAverageSuccess = false;

        while (!getMinVoteAverageSuccess) {

            try {
                minVoteAverage = askValue("Movie's minimum rate [" + minAcceptableValue + "-" + maxAcceptableValue + "]: ");

                if(!minVoteAverage.isEmpty()) {
                    minVoteAverageValue = Integer.parseInt(minVoteAverage);
                }

                getMinVoteAverageSuccess = true;
            }
            catch (NumberFormatException e) {
                System.out.println("\n| Pleaser enter a value that corresponding to an integer.");
            }
        }

        if(!minVoteAverage.isEmpty()){

            try{
                validateValueInterval(minVoteAverageValue, minAcceptableValue, maxAcceptableValue);
            }
            catch (IntervalException e) {
                System.out.println(e.getMessage());
                return getMinVoteAverage(minAcceptableValue,maxAcceptableValue);
            }
        }
        return minVoteAverage;
    }



    /**
     * Selects a genre from the list based on the provided index.
     * @param genres The list of genres to select from.
     * @return The selected movie.
     */
    private String selectGenreByIndex(List<String> genres) {
        try {
            int index = retrieveAsInt("Enter genre index: ") - 1;
            isValidIndex(index, genres.size());
            return genres.get(index);
        }
        catch (IndexException e){
            System.out.println(new IndexException().getMessage());
            return selectGenreByIndex(genres);
        }
    }

    /**
     * Ask the user to select an interaction with page management system (previous, next, stop)
     * @param title from the precedent search
     * @param minYear from the precedent search
     * @param maxYear from the precedent search
     * @param genres from the precedent search
     * @param minVoteAverage from the precedent search
     * @param message the message to print to the user to interact with page management system
     * @return the user's answer
     */
    private boolean askPreviousOrNextPage(String title, String minYear, String maxYear, List<String> genres, String minVoteAverage , String message){
        String response = askValue(message);

        switch (response) {
            case "3" -> {
                int pageNumber = Integer.parseInt(askValue("Enter page number: "));
                if (pageNumber >= 1 && pageNumber <= SEARCH_READER.numberOfPagesOfMoviesInJson()){
                    apiObject.searchMovies(title, minYear, maxYear, genres, minVoteAverage, String.valueOf(pageNumber));
                    return true;
                }
                System.out.println("\n| Page number unavailable.");
            }
            case "2" -> {
                if(SEARCH_READER.getPageInJson() < SEARCH_READER.numberOfPagesOfMoviesInJson()){
                    apiObject.searchMovies(title, minYear, maxYear, genres, minVoteAverage, String.valueOf(SEARCH_READER.getPageInJson() + 1));
                    return true;
                }
                System.out.println("\n| There is no next page.");
            }
            case "1" -> {
                if(SEARCH_READER.getPageInJson() > 1){
                    apiObject.searchMovies(title, minYear, maxYear, genres, minVoteAverage, String.valueOf(SEARCH_READER.getPageInJson() - 1));
                    return true;
                }
                System.out.println("\n| There is no precedent page.");
            }
            case "0" -> {
                return false;
            }
            default -> System.out.println("\n| Please enter a valid option.");
        }

        return askPreviousOrNextPage(title, minYear, maxYear, genres, minVoteAverage, message);
    }

    /**
     * Return the message corresponding to the page management user interactive
     * @return the message corresponding to the page management user interactive
     */
    private String messageOfAskPreviousOrNextPage(){
        return "Choose your action: [0] Continue/Leave command, [1] Previous Page, [2] Next Page, [3] Specify Page | page ("
                + SEARCH_READER.getPageInJson()
                + "/"
                + SEARCH_READER.numberOfPagesOfMoviesInJson() +")";
    }

    /**
     * Display only the title, the year of release and the average note of every film in the catalog (popular films generated by API)
     */
    public void catalogCommand(){
        apiObject.popularMovies("1");
        do{
            updateSearchReader();
            System.out.println("The most popular movies at the moment are listed below: \n" + SEARCH_READER.findAllMovies());
        } while(askPreviousOrNextPage(messageOfAskPreviousOrNextPage()));
    }

    /**
     * Ask the user to select an interaction with page management system (previous, next, stop)
     * @param message the message to print to the user to interact with page management system
     * @return {@code true} if the user select something else than stopping the page management system
     */
    private boolean askPreviousOrNextPage(String message){
        String response = askValue(message);

        switch (response) {
            case "3" -> {
                int pageNumber = Integer.parseInt(askValue("Enter page number: "));
                if (pageNumber >= 1 && pageNumber <= SEARCH_READER.numberOfPagesOfMoviesInJson()){
                    apiObject.popularMovies(String.valueOf(pageNumber));
                    System.out.println();
                    return true;
                }
                System.out.println("\n| Page number unavailable.");
            }
            case "2" -> {
                if(SEARCH_READER.getPageInJson() < SEARCH_READER.numberOfPagesOfMoviesInJson()){
                    apiObject.popularMovies( String.valueOf(SEARCH_READER.getPageInJson() + 1));
                    System.out.println();
                    return true;
                }
                System.out.println("\n| There is no next page.");
            }
            case "1" -> {
                if(SEARCH_READER.getPageInJson() > 1){
                    apiObject.popularMovies(String.valueOf(SEARCH_READER.getPageInJson() - 1));
                    System.out.println();
                    return true;
                }
                System.out.println("\n| There is no precedent page.");
            }
            case "0" -> {
                return false;
            }
            default -> System.out.println("\n| Please enter a valid option.");
        }

        return askPreviousOrNextPage(message);
    }

    /**
     * Selects a mode based on provided options.
     * @param message        The message prompting the user for input.
     * @param optionNumbers  The list of valid option numbers.
     * @return The selected mode.
     */
    private String selectMode(String message, List<String> optionNumbers) throws IndexException {
        String selectMode = askValue(message);

        if(!optionNumbers.contains(selectMode)){
            throw new IndexException();
        }
        return selectMode;
    }
}