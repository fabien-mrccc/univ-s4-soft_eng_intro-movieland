package moviesapp.controller;

import moviesapp.model.Favorites;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class CLController {

    private final List<String> commands;
    private final Scanner scanner;

    public CLController() {
        commands = new ArrayList<>();
        setupCommands();
        scanner = new Scanner(System.in);
    }

    public void clear(String input) {
        if (askToConfirm("Are you sure that you want to delete your favourites?")){
            Favorites.instance.clear();
        }
    }

    /**
     * Add elements to the command list
     */
    private void setupCommands(){
        commands.add("exit");
        commands.add("help");
    }

    /**
     * Exit command that asks user if he is sure that he wants to leave the application, exit it if yes
     */
    public void exit(){
        if(askToConfirm("Are you sure that you want to leave the application?")){
            System.exit(0);
        }
    }
    /**
     * display command that asks user if he is sure that he wants to display his favorites, exit it if yes
     */
    public void favorites(){
        //todo : penser ajouter la nouvelle fonction de Amina
        System.out.printf(Favorites.instance.toString());
    }



    /**
     * Print a terminal message with choice (yes or no) and return true if yes, false if no
     * @param string the message to print
     * @return true if yes, false if no
     */
    private boolean askToConfirm(String string){
        System.out.println(string);
        System.out.print(" (y/n)?: ");
        return scanner.nextLine().equals("y");
    }
}