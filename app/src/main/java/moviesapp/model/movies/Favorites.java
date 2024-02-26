package moviesapp.model.movies;
import moviesapp.controller.command_line.CLController;
import moviesapp.model.json.JsonReader;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.json.JsonReader.favoritesFilePath;

public class Favorites extends MovieFinder {
    public static final Favorites instance = new Favorites();
    private final JsonReader jsonReader = new JsonReader(favoritesFilePath);
    private final Movies favorites = jsonReader.findAllMovies();

    /** Return true if the list of favorites is empty, if not return false
     *  @return boolean
     **/
    public boolean isEmpty(){
        return favorites.isEmpty();
    }

    /**
     * Return the Movies composing user favorites
     * @return the Movies composing user favorites
     */
    public Movies getFavorites(){
        return favorites;
    }

    @Override
    public String toString(){
        return favorites.toString();
    }

    /**
     * Remove all the movies registered in user favorite list.
     */
    public void clear(){
        try{
            favorites.clear();
        }
        catch(UnsupportedOperationException e){
            System.out.println("The operation was unsuccessful.");
        }
    }

    /**
     * Adds a movie to the favorites list if it's not already present and if it's not null.
     * @param movie The movie to add to favorites.
     */
    public void add(Movie movie){
        if(movie != null && !contains(movie)){
            favorites.add(movie);
        }
    }

    /**
     * Removes a movie to the favorites list if it's already present and if it's not null.
     * @param movie The movie to remove to favorites.
     */
    public void remove(Movie movie){
        if(movie != null && contains(movie)){
            favorites.remove(movie);
        }
    }

    /**
     * Checks if a movie is already in the favorites list.
     * @param movie The movie to check.
     * @return True if the movie is already in favorites, false otherwise.
     */
    public boolean contains(Movie movie){
        return favoritesIds().contains(movie.id());
    }

    /**
     * Retrieves the IDs of favorite movies.
     * @return A list of IDs representing favorite movies.
     */
    private List<String> favoritesIds(){
        List<String> favoritesIds = new ArrayList<>();

        for (Movie favorite : favorites){
            favoritesIds.add(favorite.id());
        }
        return favoritesIds;
    }

    @Override
    public void findMoviesByCriteria(Movies movies, String title, String releaseYear, List<String> genres, String minVoteAverage) {
        for (Movie movie : favorites) {
            boolean titleCondition = (title == null) ||
                    movie.originalTitle().toLowerCase().contains(title.toLowerCase());
            boolean yearCondition = (releaseYear == null) ||
                    movie.releaseDate().toLowerCase().startsWith(releaseYear.toLowerCase());
            boolean genreCondition = (genres == null || genres.isEmpty()) ||
                    movieContainsAnyGenre(movie, genres);
            boolean voteCondition = (minVoteAverage == null) ||
                    Double.parseDouble(minVoteAverage) <= movie.minVoteAverage();

            if (titleCondition && yearCondition && genreCondition && voteCondition) {
                movies.add(movie);
            }
        }
    }

    /**
     * Checks if the movie contains at least one genre from the specified list.
     * @param movie The movie to check.
     * @param genres The list of genres to search for in the movie.
     * @return {@code true} if the movie contains at least one of the specified genres, {@code false} otherwise.
     */
    private boolean movieContainsAnyGenre(Movie movie, List<String> genres) {
        for (String genre : genres) {
            if (movie.genres().contains(genre)) {
                return true;
            }
        }
        return false;
    }
}
