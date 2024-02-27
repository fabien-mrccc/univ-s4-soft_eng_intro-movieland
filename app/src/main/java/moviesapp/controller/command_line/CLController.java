package moviesapp.controller.command_line;

import moviesapp.model.json.JsonReader;
import moviesapp.model.json.JsonWriter;

import java.util.*;

import static moviesapp.model.json.JsonReader.apiFilePath;

public final class CLController {

    static JsonReader jsonReader;
    private CLMethods commandMethods;
    private CLHelp help;
    private CLCatalog catalog;
    private CLSearch search;
    private CLDetails details;
    private CLAdd add;
    private CLRemove remove;
    private CLFavorites favorites;
    private CLClear clear;
    private CLExit exit;

    public CLController() {
        initCommands();
        jsonReaderUpdate();
    }

    /**
     * Initializes commands.
     * This method is responsible for initializing command-related objects.
     */
    private void initCommands(){
        commandMethods = new CLMethods();
        help = new CLHelp();
        help.setupHelpCommandsDescription();
        catalog = new CLCatalog();
        search = new CLSearch();
        details = new CLDetails();
        add = new CLAdd();
        remove = new CLRemove();
        favorites = new CLFavorites();
        clear = new CLClear();
        exit = new CLExit();
    }

    /**
     * Update the file read by the jsonReader
     */
    public static void jsonReaderUpdate(){
        jsonReader = new JsonReader(apiFilePath);
    }

    /**
     * Select a method to execute based on user input and execute it
     */
    public void select(){
        new JsonWriter(apiFilePath).clean();

        for (;;) {
            help.helpCommand();

            String commandValue = commandMethods.askValue("\nInput your command: ").toLowerCase(Locale.ROOT).trim();
            System.out.println();

            switch(commandValue){
                case "1":
                    catalog.catalogCommand();
                    break;

                case "2":
                    search.searchCommand();
                    break;

                case "3":
                    details.detailsCommand();
                    break;

                case "4":
                    add.addCommand();
                    break;

                case "5":
                    remove.removeCommand();
                    break;

                case "6":
                    favorites.favoritesCommand();
                    break;

                case "7":
                    clear.clearCommand();
                    break;

                case "8":
                    exit.exitCommand();
                    break;

                default :
                    System.out.println("*** Command '" + commandValue +  "' doesn't exist. ***");
                    break;
            }
        }
    }
}