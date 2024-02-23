package moviesapp.controller.command_line;

import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.List;
import java.util.Scanner;

import static moviesapp.controller.command_line.CLController.jsonReader;
import static moviesapp.controller.command_line.CLController.jsonReaderUpdate;

public class CLMethods {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Print a terminal message with choice (yes or no) and return true if yes, false if no
     * @param string the message to print
     * @return true if yes, false if no
     */
    protected boolean askToConfirm(String string){
        String answer;
        do{
            answer = askValue(string + " [Y/n]: ").trim().toLowerCase();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Print a message to the user and return its input.
     * @param message to print to the user
     * @return its input
     */
    protected String askValue(String message){
        System.out.println(message);
        return scanner.nextLine();
    }

    /**
     * Selects a movie from the list based on the provided index.
     * @param movies The list of movies to select from.
     * @param message The message to display prompting the user for input.
     * @return The selected movie.
     */
    protected Movie selectMovieByIndex(Movies movies, String message) {
        if(Movies.noMovieFound(movies)){
            printNoMovieFoundMessage();
            throw new IllegalArgumentException();
        }

        for (;;) {
            try {
                int index = Integer.parseInt(askValue(message)) - 1;
                if (isValidIndex(index, movies.size())) {
                    return movies.findMovieByIndex(index);
                } else {
                    printIndexErrorMessage();
                }
            } catch (NumberFormatException e) {
                printIndexErrorMessage();
            }
        }
    }

    /**
     * Checks if the provided index is valid for the given size.
     * @param index The index to check.
     * @param size The size of the collection.
     * @return True if the index is valid, false otherwise.
     */
    protected boolean isValidIndex(int index, int size) {
        return index >= 0 && index < size;
    }

    /**
     * Prints an error message indicating that the entered index is invalid.
     */
    protected void printIndexErrorMessage() {
        System.out.println("\n| Please enter a valid index to select your element.");
    }

    /**
     * Prints a message indicating that no movie was found.
     */
    protected void printNoMovieFoundMessage() {
        System.out.println("\n| No movie found.");
    }

    /**
     * Prints a message that ask to enter a valid value.
     */
    protected void printValueIntervalError() {
        System.out.println("\n| Please enter a valid value between the interval.");
    }

    /**
     * Selects a mode based on provided options.
     * @param message        The message prompting the user for input.
     * @param optionNumbers  The list of valid option numbers.
     * @return The selected mode.
     */
    protected String selectMode(String message, List<String> optionNumbers){
        String selectMode = askValue(message);

        while(!optionNumbers.contains(selectMode)){
            printIndexErrorMessage();
            selectMode = askValue(message);
        }
        return selectMode;
    }

    /**
     * Prints an error message indicating that the general Select Mode method is no longer working.
     * Signature: protected String selectMode(String message, List<String> optionNumbers)
     */
    protected void printSelectModeError(){
        System.out.println("General Select Mode method is not working anymore.\n -signature: protected String selectMode(String message, List<String> optionNumbers)");
    }

    /**
     * Retrieves the movies from the previous search.
     * @return The list of movies from the previous search.
     */
    protected Movies moviesFromPreviousSearch(){
        jsonReaderUpdate();
        Movies movieList = jsonReader.findAllMovies();
        System.out.println("\nBelow the movies from your precedent search: \n" + movieList);
        return movieList;
    }
}
