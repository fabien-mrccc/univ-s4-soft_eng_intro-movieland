package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;
import moviesapp.model.json.JsonWriter;

import static moviesapp.model.json.JsonReader.favoritesFilePath;


public class CLClear extends CLMethods {

    /**
     * Test if askToConfirm is true ,and if it is, clear the favorite list
     */
    void clearCommand() {
        if (askToConfirm("Are you sure that you want to delete your favorites?")){
            Favorites.instance.clear();
            new JsonWriter(favoritesFilePath).clean();
            System.out.println("Your favorite list has been cleared.");
        }
    }
}
