package moviesapp.model;
import java.util.List;
public class Favorites {
    private List<Movie> favorites;

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
        if(!favorites.contains(movie)){
            favorites.add(movie);
        }
    }

    /** Remove a film from the list of favorites
     @param movie the movie to remove from the list
     **/
    public void remove(Movie movie){
        try {favorites.remove(movie);}
        catch (UnsupportedOperationException e){
            System.out.println("This movie does not belong to your list of favorites");
        }
    }
}
