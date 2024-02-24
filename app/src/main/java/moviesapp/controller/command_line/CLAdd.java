package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;
import moviesapp.model.json.JsonWriter;
import moviesapp.model.movies.Movies;

import static moviesapp.controller.command_line.CLController.*;

public class CLAdd extends CLFavorites {

    /**
     * Add to the favorites one movie.
     */
    void addCommand(){
        System.out.println("Add command has been started.");
        Movies movies = moviesFromPreviousSearch();
        do{
            if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    addMovieByIndex(movies);
                }
                else{
                    Favorites.instance.add(movies.get(0));
                }
                new JsonWriter(favoritesFilePath).saveFavorites(Favorites.instance.getFavorites());
            }
            else {
                printNoMovieFoundMessage();
                System.out.println("| Make sure to use search command before add command.");
                return;
            }
        }
        while(askToConfirm("Do you want to add another movie?"));
        System.out.println();
        favoritesCommand();
    }

    /**
     * Add a specific movie chosen by the user with a number to the favorite list
     * @param movies chosen to browse
     */
    private void addMovieByIndex(Movies movies){
        Favorites.instance.add(selectMovieByIndex(movies, "Index of the movie to add to your favorites: "));
    }
}
