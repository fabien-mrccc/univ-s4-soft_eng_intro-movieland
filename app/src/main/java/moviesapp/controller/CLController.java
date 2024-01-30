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


}