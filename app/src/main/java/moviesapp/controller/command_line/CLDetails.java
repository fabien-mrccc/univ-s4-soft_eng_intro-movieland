package moviesapp.controller.command_line;

import moviesapp.model.movies.Movies;

import static moviesapp.controller.command_line.CLController.*;

public class CLDetails extends CLMethods {

    /**
     * Search a specific group of movies and print their detailed information
     */
    void detailsCommand(){
        jsonReaderUpdate();
        Movies movieList= jsonReader.findAllMovies();
        System.out.println("Below the movies from your precedent search: \n" + movieList);
        if(!movieList.isEmpty()) {
            System.out.println(selectMovieByIndex(movieList, "Enter the index of the movie to see its details: "));
        }
    }
}
