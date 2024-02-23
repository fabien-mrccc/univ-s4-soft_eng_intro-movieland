package moviesapp.controller.command_line;

import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;

import java.util.Arrays;

public class CLDetails extends CLMethods {

    /**
     * Search a specific group of movies and print their detailed information
     */
    void detailsCommand(){
        String detailsMode = selectMode("Choose details command mode: [1] From Search, [2] From Favorites", Arrays.asList("1","2"));

        Movies movieList;

        switch(detailsMode){
            case "1" -> movieList = moviesFromPreviousSearch();
            case "2" -> {
                movieList = Favorites.instance.getFavorites();
                System.out.println("\nBelow the movies from your favorites: \n" + movieList);
            }
            default -> {
                printSelectModeError();
                return;
            }
        }

        try{
            System.out.print(selectMovieByIndex(movieList, "Enter the index of the movie to see its details: ").details());
        } catch(IllegalArgumentException e){
            System.out.println("| Impossible to access movie details.");
        }
    }
}
