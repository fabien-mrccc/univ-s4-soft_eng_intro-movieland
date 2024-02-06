package moviesapp.model;
import java.util.ArrayList;
import java.util.List;
public class Favorites {

    public static final Favorites instance = new Favorites();
    private final List<Movie> favorites;

    private Favorites(){
        favorites = new ArrayList<>();
    }

    /** Return true if the list of favorites is empty, if not return false
     *  @return boolean
     **/
    public boolean isEmpty(){
        return favorites.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder favoritesString = new StringBuilder();
        if(favorites.isEmpty()){
            favoritesString = new StringBuilder("Your list of favorites is empty.");
            return favoritesString.toString();
        }
        for(Movie movie: favorites){
            favoritesString.append(movie).append("\n");
        }
        return favoritesString.toString();
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
     * Return a list of movies containing there information from the JSON file selected with a name(optional) and year (optional) provided in parameter.
     * @param name the name of the movie
     * @param year the release year of the movie
     * @return return a list of movies
     */
    public Movies findMovies(String name , String year){
        if(name == null || year == null){
            return null;
        }

        boolean nameEmpty = name.isEmpty();
        boolean yearEmpty = year.isEmpty();

        if(nameEmpty && yearEmpty){
            return null;
        }

        Movies movieList = new Movies();

        if(!nameEmpty && yearEmpty){
            findMoviesByName(movieList , name);
        }
        else if(nameEmpty){
            findMoviesByYear(movieList , year );
        }
        else{
            findMovies(movieList , name , year);
        }
        return movieList;
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param name the name of the movie researched
     */
    private void findMoviesByName(Movies movies, String name ) {
        for (Movie movie : favorites) {
            if(movie.originalTitle().toLowerCase().contains(name.toLowerCase())) {
                movies.add(movie);
            }
        }
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     */
    private void findMoviesByYear(Movies movies , String year ) {
        for (Movie movie : favorites) {
            if(movie.releaseDate().toLowerCase().contains(year.toLowerCase())) {
                movies.add(movie);
            }
        }
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    private void findMovies(Movies movies, String name, String year) {
        for (Movie movie : favorites) {
            if(movie.releaseDate().toLowerCase().contains(year.toLowerCase())
                    && movie.originalTitle().toLowerCase().contains(name.toLowerCase())) {
                movies.add(movie);
            }
        }
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
}
