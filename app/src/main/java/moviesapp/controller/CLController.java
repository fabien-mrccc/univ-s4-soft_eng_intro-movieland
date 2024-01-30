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
        scanner = new Scanner(System.in);

    }

    public void clear(String input) {
        if (input.equals("yes")){
            Favorites.instance.clear();
        }
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