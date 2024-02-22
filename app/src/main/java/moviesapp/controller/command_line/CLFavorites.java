package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;

public class CLFavorites extends CLMethods {

    /**
     * Command that print all movies stored in user favorite list
     */
    protected void favoritesCommand(){
        System.out.print("Your favorite list:\n" + Favorites.instance);
    }

    /**
     * Print the favorites list modified
     */
    protected void printFavoritesUpdate(){
        System.out.println("\nYour favorites list updated: ");
        favoritesCommand();
    }
}
