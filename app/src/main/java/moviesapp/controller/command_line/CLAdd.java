package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;
import moviesapp.model.json.JsonWriter;
import moviesapp.model.movies.Movies;

import static moviesapp.controller.command_line.CLController.*;

public class CLAdd extends CLFavorites {

    /**
     * Add to the favorites one or several movies.
     */
    void addCommand(){
        do{
            Movies movies = jsonReader.findAllMovies();
            if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    addMovieByIndex(movies);
                }
                else{
                    Favorites.instance.add(movies);
                }
                new JsonWriter(favoritesFilePath).saveFavorites(Favorites.instance.getFavorites());
            }
            else {
                printNoMovieFoundMessage();
            }
        }
        while(askToConfirm("Do you want to add another movie?"));
        printFavoritesUpdate() ;
    }

    /**
     * Add a specific movie chosen by the user with a number to the favorite list
     * @param movies chosen to browse
     */
    private void addMovieByIndex(Movies movies){
        if(!Movies.noMovieFound(movies)) {
            Favorites.instance.add(selectMovieByIndex(movies, "Index of the movie to add to your favorites: "));
        }
    }
}
