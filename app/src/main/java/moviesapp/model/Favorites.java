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
}
