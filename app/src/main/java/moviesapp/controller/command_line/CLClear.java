package moviesapp.controller.command_line;

import moviesapp.model.Favorites;
import moviesapp.model.JsonWriter;

import static moviesapp.controller.command_line.CLController.favoritesFilePath;

public class CLClear extends CLMethods {

    /**
     * Test if askToConfirm is true ,and if it is, clear the favourite list
     */
    void clearCommand() {
        if (askToConfirm("Are you sure that you want to delete your favourites?")){
            Favorites.instance.clear();
            new JsonWriter(favoritesFilePath).clean();
            System.out.println("Your favorite list has been cleared.");
        }
    }
}
