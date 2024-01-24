package moviesapp.model;
import java.util.ArrayList;
import java.util.List;
public class Favorites {

    public static final Favorites instance = new Favorites();
    private final List<Movie> favorites;

    private Favorites(){
        favorites = new ArrayList<>();
    }

    /** return true if the list of favorites is empty, if not return false
     @return boolean
     **/
    public boolean isEmpty(){
        return favorites.isEmpty();
    }

    /** Add a film to the favorites of the user
     @param movie the movie to add to the list
     **/
    public void add(Movie movie){
        if(!contains(movie)){
            favorites.add(movie);
        }
    }

    /**
     * Check if the favorites list contains a specific movie, returns false if the list is empty
     * @param movie the movie that we check if it is in our list
     * @return boolean
     */
    private boolean contains(Movie movie){
        if(isEmpty()){
            return false;
        }
        if(movie == null){
            return true;
        }
        return favorites.contains(movie);
    }

    /** Remove a film from the list of favorites
     @param movie the movie to remove from the list
     **/
    public void remove(Movie movie){
        try {favorites.remove(movie);}
        catch (UnsupportedOperationException e){
            System.out.println("This movie does not belong to your list of favorites.");
        }
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
     * Add a group of movies to the user favorite list
     * @param movies: the movies that we want to add to the user favorite list
     */
    public void addAll(List<Movie> movies){
        favorites.addAll(movies);
    }
}
