package moviesapp.controller.command_line;

import moviesapp.model.json.JsonWriter;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;

import static moviesapp.model.json.JsonReader.favoritesFilePath;

public class CLRemove extends CLFavorites {

    /**
     * Remove to the favorites the movie chosen by the user
     */
    void removeCommand(){
        System.out.println("Remove command has been started.\n");
        do{
            favoritesCommand() ;
            System.out.println();
            Movies movies = Favorites.instance.getFavorites() ;

            if(movies.isEmpty()){
                break;
            }
            else if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    removeMovieByIndex(movies);
                }
                else{
                    Favorites.instance.remove(movies.get(0));
                }
                new JsonWriter(favoritesFilePath).saveFavorites(Favorites.instance.getFavorites());
            }
            else{
                printNoMovieFoundMessage();
                return;
            }
        }
        while(askToConfirm("Do you want to remove another movie?"));
        System.out.println();
        favoritesCommand();
    }

    /**
     * Remove a specific movie chosen by the user with the index of the movie to the favorite list
     * @param movies chosen to browse
     */
    private void removeMovieByIndex(Movies movies){
        Favorites.instance.remove(selectMovieByIndex(movies, "Index of the movie to remove from your favorites: "));
    }
}
