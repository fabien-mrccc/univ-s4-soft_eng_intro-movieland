package moviesapp.controller;

import moviesapp.model.Favorites;
import moviesapp.model.JSONReader;
import moviesapp.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class CLController {

    private final List<String> commands;
    private final Scanner scanner;

    public CLController() {
        commands = new ArrayList<>();
        setupCommands();
        scanner = new Scanner(System.in);
    }

    /**
     * Add elements to the command list
     */
    private void setupCommands(){
        commands.add("help");
        commands.add("catalog");
        commands.add("details");
        commands.add("add");
        commands.add("remove");
        commands.add("favorites");
        commands.add("clear");
        commands.add("exit");
    }

    /**
     * Print the list of commands available
     */

    private void help(){
        System.out.println("Command List :\n");
        for (String command : commands){
            System.out.println("  " + command + "\n");
        }
        System.out.println("lower/uppercase doesn't matter");
    }

    /**
     * Display only the name the year of release and the average note of every film in the catalog
     */

    private void displayCatalog(){
        JSONReader jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/main/java/moviesapp/model/data_example.json");
        List<Movie> movieList = jsonReader.findAllMovies();
        StringBuilder movies = new StringBuilder();
        for(Movie movie : movieList){
            movies.append(movie.toString()).append("\n");
        }
        System.out.println(movies);
    }

    private void details(){
        JSONReader jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");
        String name = askValue("Name of the movie: ");
        String year = askValue("Year of release: ");
        List<Movie> movies = new ArrayList<>();
        movies = jsonReader.findMovies(name, year);

        for(Movie movie : movies){
            System.out.println(movie);
        }

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
        System.out.printf(Favorites.instance.toString());
    }

    /**
     * Test if askToConfirm is true and if it is clear the favourite list
     */
    private void clear() {
        if (askToConfirm("Are you sure that you want to delete your favourites?")){
            Favorites.instance.clear();
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
     * Print a terminal message with choice (yes or no) and return true if yes, false if no
     * @param string the message to print
     * @return true if yes, false if no
     */
    private boolean askToConfirm(String string){
        String answer;
        do{
            System.out.println(string + " (y/n): ");
            answer = scanner.nextLine();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }





    /**
     * Select a method to execute based on user input and execute it
     */
    public void select(){
        for (;;) {
            System.out.println("Input your command: ");
            String command = scanner.nextLine().toLowerCase(Locale.ROOT).trim();

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

                default :
                    System.out.println("command '" + command +  "' doesn't exist.");
                    break;
            }
        }
    }
}