package moviesapp.controller;

import moviesapp.model.Favorites;
import moviesapp.model.JSONReader;
import moviesapp.model.Movies;
import moviesapp.model.TmdbAPI;

import java.io.IOException;
import java.util.*;

public final class CLController {
    private final List<String> commands;
    private final Scanner scanner;
    private JSONReader jsonReader;

    public CLController() {
        commands = new ArrayList<>();
        setupCommands();
        scanner = new Scanner(System.in);
        jsonReaderUpdate();
    }

    /**
     * Update the file read by the jsonReader
     */
    private void jsonReaderUpdate(){
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/main/java/moviesapp/model/api-results.json");
    }

    /**
     * Add elements to the command list
     */
    private void setupCommands(){
        commands.add("(1)catalog: see all movies available on the application");
        commands.add("(2)search: show specific movies based on your criteria");
        commands.add("(3)details: see detailed information about one movie");
        commands.add("(4)add: add one or several movies to your favorite list");
        commands.add("(5)remove: remove one or several movies to your favorite list");
        commands.add("(6)favorites: see movies in your favorite list");
        commands.add("(7)clear: remove all the movies in your favorite list");
        commands.add("(8)exit: leave the application");
    }

    /**
     * Print the list of commands available
     */
    private void help(){
        System.out.println("Commands available (lower/uppercase doesn't matter): ");
        for (String command : commands){
            System.out.println("•" + command);
        }
    }

    /**
     * Display only the title, the year of release and the average note of every film in the catalog
     */
    private void displayCatalog(){
        TmdbAPI api = new TmdbAPI();
        String page = "";
        int i = 1;
        do {
            if(i != 1) {
                page = askValue("which page do you want :");
            }
            api.displayCatalog(page);
            jsonReaderUpdate();
            System.out.println(jsonReader.findAllMovies());
            i=0;
        }while(askToConfirm("do you want to change the page?(y/n)"));
    }

    /**
     * Search a specific group of movies and print their detailed information
     */
    private void details(){
        jsonReaderUpdate();
        Movies movieList= jsonReader.findAllMovies();
        if(!movieList.isEmpty()) {
            System.out.println("Give the number of the movie");
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println(movieList.get(index).details());
        }
        System.out.println("There was no movie");
    }

    /**
     * Ask title, release year, vote average and genres information to the user to select a specific group of movies to print
     */
    private void searchMoviesToPrint(){
        searchMovies();
        System.out.println("\nYour list of movies found in your search: \n" + jsonReader.findAllMovies());
    }

    /**
     * Ask title, release year, vote average and genres information to the user to select a specific group of movies
     * @return the group of movies found
     */
    private Movies searchMoviesToReturn(){
        searchMovies();
        return jsonReader.findAllMovies();
    }

    /**
     * Ask title, release year, vote average and genres information to the user to select a specific group of movies
     */
    private void searchMovies(){
        TmdbAPI api = new TmdbAPI();
        String title = askValue("Title of the movie: ");
        String releaseYear = askValue("Year of release: ");
        String voteAverage = askValue("Movie's minimum rate: ");
        String page = askValue("Select your page (total pages = " + jsonReader.numberOfPagesOfMoviesInJson() + "): ");
        List<String> genres = specifiedGenres(api);
        api.searchMovie(title, releaseYear, genres, voteAverage , page);
        jsonReaderUpdate();
    }

    /**
     * Return the user's specified genres
     * @param api the api that contains all the genres available
     * @return the user's specified genres
     */
    private List<String> specifiedGenres(TmdbAPI api){
        List<String> genres = new ArrayList<>();

        if(askToConfirm("Do you want to specify one or more genres?")){
            System.out.println("\nList of genres: \n" + api.genreList());

            do{
                String genreName = askValue("Enter genre name: ").trim().toLowerCase();
                genreName = genreName.substring(0,1).toUpperCase() + genreName.substring(1);
                if (TmdbAPI.GENRE_ID_MAP.containsKey(genreName)) {
                    genres.add(genreName);
                }
                else {
                    System.out.println("Genre not found. Please enter a valid genre.");
                }
            } while(askToConfirm("Do you want to add more genres?"));
        }
        return genres;
    }

    /**
     * Print a message to the user and return its input.
     * @param message to print to the user
     * @return its input
     */
    private String askValue(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Command that print all movies stored in user favorite list
     */
    private void displayFavorites(){
        System.out.printf(Favorites.instance+"\n");
    }

    /**
     * Test if askToConfirm is true ,and if it is, clear the favourite list
     */
    private void clear() {
        if (askToConfirm("Are you sure that you want to delete your favourites?")){
            Favorites.instance.clear();
            System.out.println("Your favorite list has been cleared.");
        }
    }

    /**
     * Exit command that asks user if he is sure that he wants to leave the application, exit it if yes
     */
    private void exit(){
        if(askToConfirm("Are you sure that you want to leave the application?")){
            System.exit(0);
        }
    }

    /**
     * Add a specific movie chosen by the user with an id to the favorite list
     * @param movies chosen to browse
     */
    private void addMovieById(Movies movies){
        Favorites.instance.add(selectMovieById(movies));
    }

    /**
     * Remove a specific movie chosen by the user with an id to the favorite list
     * @param movies chosen to browse
     */
    private void removeMovieById(Movies movies){
        Favorites.instance.remove(selectMovieById(movies));
    }

    /**
     * Ask the user the id of movie that he wants to select in a Movies object
     * @param movies to browse
     * @return movie selected in a Movies object
     */
    private Movies selectMovieById(Movies movies){
        System.out.println("\nYour list of movies with identifiers: \n" + movies.toStringWithID() + "\nSelect the ID from the movies that correspond to your search, displayed above.");
        String id = askValue("ID of the movie to add to your favorites: ") ;
        Movies movieSelected = new Movies();
        movieSelected.add(movies.findMovieByID(id));
        return movieSelected ;
    }

    /**
     * add to the favorites one or several movies
     */
    private void add(){
        do{
            Movies movies = searchMoviesToReturn();
            if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    addMovieById(movies);
                }
                else{
                    Favorites.instance.add(movies);
                }
            }
        }
        while(askToConfirm("Do you want to add another movie?"));
        printFavoritesUpdate() ;
    }

    /**
     * Remove to the favorites the movie chosen by the user
     */
    private void remove(){
        do{
            if(Favorites.instance.isEmpty()){
                break;
            }
            System.out.println("Your actual favorites list: ");
            displayFavorites() ;
            Movies movies = searchFavoritesToRemove() ;
            if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    removeMovieById(movies);
                }
                else{
                    Favorites.instance.remove(movies);
                }
            }
        }
        while(askToConfirm("Do you want to remove another movie?"));
        printFavoritesUpdate() ;
    }

    /**
     * Ask title and release year information to the user to select a specific group of favorite movies
     * @return the group of movies found
     */
    private Movies searchFavoritesToRemove(){
        String title = askValue("Title of the movie to remove: ");
        String releaseYear = askValue("Year of release: ");
        return Favorites.instance.findMovies(title, releaseYear);
    }

    /**
     * Print the favorites list modified
     */
    private void printFavoritesUpdate(){
        System.out.println("\nYour favorites list updated: ");
        displayFavorites();
        System.out.println("End of your favorite list.");
    }

    /**
     * Print a terminal message with choice (yes or no) and return true if yes, false if no
     * @param string the message to print
     * @return true if yes, false if no
     */
    private boolean askToConfirm(String string){
        String answer;
        do{
            answer = askValue(string + " [Y/n]: ").trim().toLowerCase();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Select a method to execute based on user input and execute it
     */
    public void select(){
        for (;;) {
            help();
            System.out.println("\nInput your command: ");
            String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch(command){
                case "7":
                    clear();
                    break;

                case "8":
                    exit();
                    break;

                case "1":
                    displayCatalog();
                    break;

                case "3":
                    details();
                    break;

                case "2":
                    searchMoviesToPrint();
                    break;

                case "6":
                    displayFavorites();
                    break;

                case "4":
                    add();
                    break;

                case "5":
                    remove();
                    break;

                default :
                    System.out.println("*** Command '" + command +  "' doesn't exist. ***");
                    break;
            }
        }
    }
}