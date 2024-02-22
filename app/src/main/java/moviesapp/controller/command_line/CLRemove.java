package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;

public class CLRemove extends CLFavorites {

    /**
     * Remove to the favorites the movie chosen by the user
     */
    void removeCommand(){
        do{
            if(Favorites.instance.isEmpty()){
                break;
            }
            favoritesCommand() ;
            Movies movies = searchFavoritesToRemove() ;
            if (!Movies.noMovieFound(movies)) {
                if (movies.size() > 1){
                    removeMovieByIndex(movies);
                }
                else{
                    Favorites.instance.remove(movies);
                }
            }
            else{
                printNoMovieFoundMessage();
            }
        }
        while(askToConfirm("Do you want to remove another movie?"));
        printFavoritesUpdate() ;
    }

    /**
     * Ask title and release year information to the user to select a specific group of favorite movies
     * @return the group of movies found
     */
    private Movies searchFavoritesToRemove(){
        String title = askValue("Title of the movie to remove: ");
        String releaseYear = askValue("Year of release: ");
        return Favorites.instance.findMovies(title, releaseYear, null, null);
    }

    /**
     * Remove a specific movie chosen by the user with the index of the movie to the favorite list
     * @param movies chosen to browse
     */
    private void removeMovieByIndex(Movies movies){
        if(!Movies.noMovieFound(movies)){
            Favorites.instance.remove(selectMovieByIndex(movies, "Index of the movie to remove from your favorites: "));
        }
    }
}
