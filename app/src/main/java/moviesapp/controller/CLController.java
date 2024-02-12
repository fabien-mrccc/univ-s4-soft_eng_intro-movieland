package moviesapp.controller;

import moviesapp.model.Favorites;
import moviesapp.model.JSONReader;
import moviesapp.model.Movies;
import moviesapp.model.tmdbAPI;

import java.util.*;

public final class CLController {
    private final List<String> commands;
    private final Scanner scanner;
    private final JSONReader jsonReader;

    public CLController() {
        commands = new ArrayList<>();
        setupCommands();
        scanner = new Scanner(System.in);
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/main/java/moviesapp/model/api-results.json");
    }

    /**
     * Add elements to the command list
     */
    private void setupCommands(){
        commands.add("help: get a list of commands available");
        commands.add("catalog: see all movies available on the application");
        commands.add("details: see detailed information about one or several movies");
        commands.add("add: add one or several movies to your favorite list");
        commands.add("remove: remove one or several movies to your favorite list");
        commands.add("favorites: see movies in your favorite list");
        commands.add("clear: remove all the movies in your favorite list");
        commands.add("exit: leave the application");
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
     * Display only the name, the year of release and the average note of every film in the catalog
     */
    private void displayCatalog(){
        System.out.println(jsonReader.findAllMovies());
    }

    /**
     * Search a specific group of movies and print their detailed information
     */
    private void details(){
        searchMovies().printMoviesDetails();
    }

    /**
     * Ask name and year information to the user to select a specific group of movies
     * @return the group of movies found
     */
    private Movies searchMovies(){
        String title = "";
        String year;
        List<String> genre = new ArrayList<>();
        String voteAverage = "";
        tmdbAPI api = new tmdbAPI();
        String temp;
        if(askToConfirm("do you have the name of the movie?")){
            System.out.println("only the name is mandatory");
            title = askValue("Name of the movie: ");
            year = askValue("Year of release: ");
            api.searchMovie(title, year, genre, voteAverage);
            return jsonReader.findAllMovies();
        }
        else {
            year = askValue("Year of release: ");
            voteAverage = askValue("minimum mark :");
            if(askToConfirm("do you want to specify or more genres?")){
                System.out.println("List of genres :");
                System.out.println(api.genreList());
                do{
                    temp = askValue("enter genre name :");
                    if (tmdbAPI.GENRE_ID_MAP.containsKey(temp)) {
                        genre.add(temp);
                    } else {
                        System.out.println("Genre not found. Please enter a valid genre.");
                    }
                }while(askToConfirm("do you want to add more genres"));
            }
            api.searchMovie(title, year, genre, voteAverage);
        }

        return jsonReader.findAllMovies();
    }

    /*old searchMovie :
    String name = askValue("Name of the movie: ");
        String year = askValue("Year of release: ");
        return jsonReader.findMovies(name, year);*/

    /**
     * Ask name and year information to the user to select a specific group of favorites movies
     * @return the group of movies found
     */
    private Movies searchFavorites(){
        String name = askValue("Name of the movie to remove: ");
        String year = askValue("Year of release: ");
        return Favorites.instance.findMovies(name, year);
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
     * Test if askToConfirm is true and if it is clear the favourite list
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
        System.out.println(movies.toStringWithID() + "\nSelect the ID from the movies that correspond to your search, displayed below.");
        String id = askValue("ID of the movie to add to your favorites: ") ;
        Movies movieSelected= new Movies();
        movieSelected.add(movies.findMovie(id , movies));
        return movieSelected ;
    }

    /**
     * add to the favorites one or several movies
     */
    private void add(){
        do{
            Movies movies = searchMovies();
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
            Movies movies = searchFavorites() ;
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
     * print the favorites list modified
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
            answer = askValue(string + " [Y/n]: ").trim();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Select a method to execute based on user input and execute it
     */
    public void select(){
        for (;;) {
            System.out.println("\nInput your command: ");
            String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch(command){
                case "clear":
                    clear();
                    break;

                case "exit":
                    exit();
                    break;

                case "catalog":
                    displayCatalog();
                    break;

                case "help":
                    help();
                    break;

                case "details":
                    details();
                    break;

                case "search":
                    searchMovies();
                    break;

                case "favorites":
                    displayFavorites();
                    break;

                case "add":
                    add();
                    break;

                case "remove":
                    remove();
                    break;

                default :
                    System.out.println("*** Command '" + command +  "' doesn't exist. ***");
                    break;
            }
        }
    }
}