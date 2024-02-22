package moviesapp.controller.command_line;

import moviesapp.model.Movies;

import java.util.Scanner;

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
    protected Movies selectMovieByIndex(Movies movies, String message) {
        for (;;) {
            try {
                int index = Integer.parseInt(askValue(message));
                if (isValidIndex(index, movies.size())) {
                    return getSelectedMovie(movies, index);
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
        return index >= 1 && index <= size;
    }

    /**
     * Retrieves the selected movie from the list based on the index.
     * @param movies The list of movies.
     * @param index The index of the selected movie.
     * @return A Movies object containing the selected movie.
     */
    private Movies getSelectedMovie(Movies movies, int index) {
        Movies movieSelected = new Movies();
        movieSelected.add(movies.findMovieByIndex(index - 1));
        return movieSelected;
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
        System.out.println("No movie found.");
    }
}
