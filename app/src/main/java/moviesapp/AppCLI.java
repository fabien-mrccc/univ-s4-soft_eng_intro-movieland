package moviesapp;

import moviesapp.controller.CLController;


public class AppCLI {
    public static void main(String[] args) {
        System.out.println("Welcome to the movies app");
        CLController controller = new CLController();

        System.out.println("You requested command '" + args[0] + "' with parameter '" + args[1] + "'");
        controller.select();
    }
}