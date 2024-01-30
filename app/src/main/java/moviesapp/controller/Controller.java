package moviesapp.controller;

import moviesapp.model.Favorites;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Controller {

    public Controller() {
    }

    public void clear() {
        System.out.println("voulez-vous vraiment effectuer cette action (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.equals("yes")) {
            Favorites.instance.clear();
        }
    }
}
