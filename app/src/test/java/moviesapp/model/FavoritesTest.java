package moviesapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;

public class FavoritesTest {
    private static Favorites favorites;

    @BeforeAll
    static void setupBeforeAll(){
        favorites = Favorites.instance;
    }

    @Test
    void testAdd() {
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
    }
    @Test
    void testRemove(){
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        favorites.add(movie1);
        favorites.add(movie2);
        favorites.remove(movie1);
        assertThat(favorites.toString()).isEqualTo(movie2.toString());
        favorites.remove(movie2);
        assertThat(favorites.toString()).isEqualTo("Your list of favorites is empty.");
    }
    @Test
    void testContains() {
        Movie movie = new Movie();

    }
}