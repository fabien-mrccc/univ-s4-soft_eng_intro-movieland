package moviesapp.controller.command_line;

import moviesapp.model.exceptions.IndexException;
import moviesapp.model.exceptions.NoMovieFoundException;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;

import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.movies.Favorites.addByIndex;
import static moviesapp.model.movies.Movies.moviesFromPreviousSearch;
import static moviesapp.model.movies.Movies.selectMovieByIndex;

public class CLFavorites extends CLController {

    /**
     * Executes the add command, allowing the user to add movies to their favorites.
     */
    void add(){
        System.out.println("Add command has been started.");
        Movies movies = moviesFromPreviousSearch();

        try {
            Movies.searchableMovie(movies);
        }
        catch (NoMovieFoundException e) {
            System.out.println("\n| Make sure to use search command before add command, otherwise your search result was null.");
            return;
        }

        boolean addSuccess = false;

        while (!addSuccess) {
            try {
                addCommandTry(movies);
                addSuccess = true;
            } catch (IndexException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Attempts to add movies to favorites by index from the given list of movies.
     *
     * @param movies the list of movies from which to select movies to add.
     * @throws IndexException if an invalid index is provided during the addition process.
     */
    private void addCommandTry(Movies movies) throws IndexException {

        do{
            addByIndex(movies, retrieveAsInt("Index of the movie to add to your favorites: "));
        }
        while(askToConfirm("Do you want to add another movie?"));

        System.out.println();
        display();
    }

    /**
     * Displays the user's favorite movie list and returns it as Movies object.
     */
    Movies display(){
        System.out.print("Your favorite list:\n" + Favorites.instance);
        return Favorites.asMovies();
    }

    /**
     * Remove to the favorites the movie chosen by the user
     */
    void remove() {
        System.out.println("Remove command has been started.\n");

        try {
            do{
                Movies movies = display();
                System.out.println();

                Movies.searchableMovie(movies);

                if (movies.size() > 1){
                    removeMovieByIndex(movies);
                }
                else{
                    Favorites.remove(movies.get(0));
                }
                FAVORITES_WRITER.saveFavorites(Favorites.asMovies());

            }
            while(askToConfirm("Do you want to remove another movie?"));
            System.out.println();
            display();
        }
        catch (NoMovieFoundException e){
            System.out.println(e.getMessage() + "\n| Be sure to have movies in your favorites before trying to use remove command.");
        }
        catch (IndexException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Remove a specific movie chosen by the user with the index of the movie to the favorite list
     * @param movies chosen to browse
     */
    private void removeMovieByIndex(Movies movies) throws NoMovieFoundException, IndexException {
        Favorites.remove(selectMovieByIndex(movies, retrieveAsInt("Index of the movie to remove from your favorites: ")));
    }

    /**
     * Test if askToConfirm is true ,and if it is, clear the favorite list
     */
    void clear() {
        if (askToConfirm("Are you sure that you want to delete your favorites?")){
            Favorites.clear();
            FAVORITES_WRITER.clean();
            System.out.println("Your favorite list has been cleared.");
        }
    }
}
