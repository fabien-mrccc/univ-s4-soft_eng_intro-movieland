package moviesapp;

import moviesapp.controller.CLController;


public class AppCLI {
    public static void main(String[] args) {
        System.out.println("\nWelcome to the movies app!\n");
        CLController controller = new CLController();
        controller.select();
    }
}