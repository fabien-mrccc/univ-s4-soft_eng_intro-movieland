package moviesapp.controller.command_line;

import moviesapp.controller.command_line.exceptions.ExitException;
import moviesapp.model.exceptions.SelectModeException;
import moviesapp.model.movies.Movies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static moviesapp.model.movies.Movies.moviesFromPreviousSearch;
import static moviesapp.model.movies.Movies.selectMovieByIndexTry;

public class CLGeneral extends CLController {

    private final List<String> commands = new ArrayList<>();

    public CLGeneral() {
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
     * Print the list of commands available
     */
    void help() {
        System.out.println("\n\nCommands available: ");
        for (String command : commands){
            System.out.println("  • " + command);
        }
    }

    /**
     * Executes the details command based on the selected mode.
     */
    void details() {

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
    void exit() throws ExitException {
        if(askToConfirm("Are you sure that you want to leave the application?")){
            throw new ExitException();
        }
    }
}
