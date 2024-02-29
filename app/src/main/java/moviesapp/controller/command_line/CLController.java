package moviesapp.controller.command_line;

import moviesapp.controller.command_line.exceptions.ExitException;
import moviesapp.model.exceptions.*;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.*;

import static moviesapp.model.json.JsonWriter.SEARCH_WRITER;
import static moviesapp.model.movies.Movies.moviesFromPreviousSearch;
import static moviesapp.model.movies.Movies.selectMovieByIndex;


public class CLController {

    private final List<String> commands = new ArrayList<>();
    private final CLFavorites favoritesCommands;
    private final CLSearch searchCommands;
    private final Scanner scanner = new Scanner(System.in);

    public CLController() {
        favoritesCommands = new CLFavorites();
        searchCommands = new CLSearch();
        setupHelpCommandsDescription();
    }

    /**
     * Add elements with description to the command list
     */
    private void setupHelpCommandsDescription() {
        commands.add("[1] catalog: see popular movies at the moment");
        commands.add("[2] search: show specific movies based on your criteria");
        commands.add("[3] details: view more information about a movie from a search or favorites");
        commands.add("search -> [4] add: add one movie to your favorite list");
        commands.add("[5] remove: remove one movie from your favorite list");
        commands.add("[6] favorites: see movies in your favorite list");
        commands.add("[7] clear: remove all the movies from your favorite list");
        commands.add("[8] exit: leave the application");
    }

    /**
     * Select and execute a command based on user input
     */
    public void select() {
        SEARCH_WRITER.clean();
        boolean exitRequested = false;

        do {
            helpCommand();

            String commandValue = askValue("\nInput your command: ").toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch (commandValue) {
                case "1":
                    searchCommands.catalog();
                    break;

                case "2":
                    searchCommands.search();
                    break;

                case "3":
                    detailsCommand();
                    break;

                case "4":
                    favoritesCommands.add();
                    break;

                case "5":
                    favoritesCommands.remove();
                    break;

                case "6":
                    favoritesCommands.display();
                    break;

                case "7":
                    favoritesCommands.clear();
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
     * Print the list of commands available
     */
    private void helpCommand() {
        System.out.println("\n\nCommands available: ");
        for (String command : commands){
            System.out.println("  • " + command);
        }
    }

    /**
     * Executes the details command based on the selected mode.
     */
    private void detailsCommand(){

        String detailsMode = selectModeTry("Choose details command mode: [1] From Search, [2] From Favorites", Arrays.asList("1","2"));

        Movies movies;

        switch(detailsMode){
            case "1" -> movies = moviesFromPreviousSearch();
            case "2" -> movies = favoritesCommands.display();
            default -> {
                System.out.println(new SelectModeException().getMessage());
                return;
            }
        }

        System.out.print(selectMovieByIndexTry(movies).details());
    }

    /**
     * Checks if the user wants to exit the application.
     * If confirmed, throws an ExitException.
     *
     * @throws ExitException if the user confirms the exit action
     */
    private void exitCommand() throws ExitException {
        if(askToConfirm("Are you sure that you want to leave the application?")){
            throw new ExitException();
        }
    }

    /**
     * Asks the user to confirm an action.
     *
     * @param string the prompt to display asking for confirmation.
     * @return {@code true} if the user confirms (enters 'Y' or 'y'), {@code false} otherwise.
     */
    protected boolean askToConfirm(String string) {
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
    protected String askValue(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Prompts the user with a message to enter an integer value.
     * Continuously prompts until a valid integer value is entered.
     *
     * @param message the message to display when prompting the user for input
     * @return the integer value entered by the user
     */
    protected int retrieveAsInt(String message) {

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
     * Selects a mode based on provided options.
     *
     * @param message The message prompting the user for input.
     * @param optionNumbers The list of valid option numbers.
     * @return The selected mode.
     */
    protected String selectMode(String message, List<String> optionNumbers) throws IndexException {
        String selectMode = askValue(message);

        if(!optionNumbers.contains(selectMode)){
            throw new IndexException();
        }
        return selectMode;
    }

    /**
     * Tries to select a mode based on the provided message and list of option numbers.
     * Continuously attempts mode selection until successful.
     *
     * @param message        The message related to selecting a mode.
     * @param optionNumbers  The list of option numbers representing available modes.
     * @return               The selected mode.
     */
    protected String selectModeTry(String message, List<String> optionNumbers) {
        String modeSelected = "";
        boolean selectModeSuccess = false;

        while(!selectModeSuccess){
            try{
                modeSelected = selectMode(message, optionNumbers);
                selectModeSuccess = true;
            }
            catch(IndexException e){
                System.out.println(e.getMessage());
            }
        }

        return modeSelected;
    } //TODO: find a solution to refactor all ...Try type methods

    /**
     * Tries to select a movie by its index from the provided movies' collection.
     * Continuously attempts movie selection until successful.
     *
     * @param movies The collection of movies to select from.
     * @return The selected movie.
     */
    private Movie selectMovieByIndexTry(Movies movies) {

        Movie movieToSelect = null;
        boolean selectMovieSuccess = false;

        while(!selectMovieSuccess) {
            try {
                movieToSelect = selectMovieByIndex(movies, retrieveAsInt("Enter the index of a movie to see its details: ") - 1);
                selectMovieSuccess = true;
            }
            catch (IndexException e) {
                System.out.println(e.getMessage());
            }
        }

        return movieToSelect;
    }
}