package moviesapp.model;
import moviesapp.controller.CLController;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends MovieFinder {
    public static final Favorites instance = new Favorites();
    private final JsonReader jsonReader = new JsonReader(CLController.favoritesFilePath);
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
     * Add one or a group of movies to the user's favorite list by selecting only those
     * which are not already in it
     * @param movies: the movies that we want to add to the user favorite list
     */
    public void add(Movies movies){
        if(movies == null){
            return;
        }
        try{favorites.addAll(moviesToAddToFavorites(movies));}
        catch (Exception e){System.out.println("The list you're searching does not respect the basic rules of a list of  movies!");}
    }

    /**
     * Filter the list of movies given on parameters by removing from the list
     * the movies already in the favorites
     * @return the list of movies to add to favorites (those which are not already in the favorites)
     * @param movies: the list of movies to add to favorites (those which are already
     *               in the favorites and those which are not)
     */
    private List<Movie> moviesToAddToFavorites(Movies movies){
        List<Movie> moviesNotInFavoriteList = new ArrayList<>();
        for(Movie movie : movies){
            if(!favorites.contains(movie)){
                moviesNotInFavoriteList.add(movie);
            }
        }
        return moviesNotInFavoriteList;
    }

    /**
     * Remove one or a group of movies from the user's favorite list by selecting only those
     * which are already in it
     * @param movies: the movies that we want to remove from the favorites
     */
    public void remove(Movies movies){
        if(movies == null){
            return;
        }
        favorites.removeAll(moviesToRemoveFromFavorites(movies));
    }

    /**
     * Filter the list of movies given on parameters by removing from the list
     * the movies not in the favorites
     * @return the list of movies to remove from favorites (only those which are in the favorites)
     * @param movies: the list of movies to remove from favorites (those which are in the favorites
     *             and those which are not)
     */
    private List<Movie> moviesToRemoveFromFavorites(Movies movies){
        List<Movie> moviesInFavoriteList = new ArrayList<>();
        for(Movie movie : movies){
            if(favorites.contains(movie)){
                moviesInFavoriteList.add(movie);
            }
        }
        return moviesInFavoriteList;
    }

    @Override
    public void findMoviesByCriteria(Movies movies, String title, String releaseYear, List<String> genres, String voteAverage) {
        for (Movie movie : favorites) {
            boolean titleCondition = (title == null) ||
                    movie.originalTitle().toLowerCase().contains(title.toLowerCase());
            boolean yearCondition = (releaseYear == null) ||
                    movie.releaseDate().toLowerCase().startsWith(releaseYear.toLowerCase());
            boolean genreCondition = (genres == null || genres.isEmpty()) ||
                    movieContainsAnyGenre(movie, genres);
            boolean voteCondition = (voteAverage == null) ||
                    Double.parseDouble(voteAverage) <= movie.voteAverage();

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
