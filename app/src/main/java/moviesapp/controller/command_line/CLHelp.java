package moviesapp.controller.command_line;

import java.util.ArrayList;
import java.util.List;

public class CLHelp {

    private final List<String> commands = new ArrayList<>();

    /**
     * Add elements to the command list
     */
    void setupHelpCommandsDescription(){
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
    void helpCommand(){
        System.out.println("\n\nCommands available: ");
        for (String command : commands){
            System.out.println("  • " + command);
        }
    }
}
