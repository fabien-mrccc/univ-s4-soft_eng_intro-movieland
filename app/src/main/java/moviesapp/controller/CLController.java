package moviesapp.controller;

import moviesapp.model.Favorites;

public final class CLController {

    public CLController() {
    }

    public void clear(String input) {
        if (input.equals("yes")){
            Favorites.instance.clear();
        }
    }
}