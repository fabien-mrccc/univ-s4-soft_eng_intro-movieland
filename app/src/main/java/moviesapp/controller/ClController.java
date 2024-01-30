package moviesapp.controller;

import moviesapp.model.Favorites;

import java.util.Scanner;

public final class ClController {

    public ClController() {
    }

    public void clear(String input) {
        if (input.equals("yes")){
            Favorites.instance.clear();
        }
    }
}