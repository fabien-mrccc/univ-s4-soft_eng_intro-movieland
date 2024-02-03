package moviesapp;

import moviesapp.controller.CLController;


public class AppCLI {
    public static void main(String[] args) {
        System.out.println("\nWelcome to the movies app!");
        System.out.println("Type 'help' to learn application commands.");
        CLController controller = new CLController();
        controller.select();
    }
}