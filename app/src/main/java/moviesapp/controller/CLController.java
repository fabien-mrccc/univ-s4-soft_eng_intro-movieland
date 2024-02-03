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

    /**
     * test if askToReturn is true and if it is clear the favourite list
     */

    public void clear() {
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
        String answer;
        do{
            System.out.println(string + " (y/n): ");
            answer = scanner.nextLine();
        }while(!answer.equals("y") && !answer.equals("n"));

        return answer.equals("y");
    }

    /**
     * Select a method to execute based on a scanner and execute it
     */

    public void select(){
        Scanner scanner = new Scanner(System.in);
        String command;
        for(;;) {
            System.out.println("Input your command: ");
            command = scanner.nextLine();
            if(command.equals("clear")){
                clear();
            }
            else if(command.equals("exit")){
                exit();
            }
            else{
                System.out.println("command '" + command +  "' doesn't exist.");
            }
        }
    }
}