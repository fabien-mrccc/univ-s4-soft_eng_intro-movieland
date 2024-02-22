package moviesapp;

import moviesapp.controller.command_line.CLController;
import static moviesapp.model.TheMovieDbAPI.*;


public class AppCLI {
    public static void main(String[] args) {
        fillGENRE_NAME_ID_MAP();
        System.out.println("\nWelcome to the movies app!");
        CLController controller = new CLController();
        controller.select();
    }
}