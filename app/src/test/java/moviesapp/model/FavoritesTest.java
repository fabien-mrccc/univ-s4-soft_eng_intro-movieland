package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
    void testContains() {
        Movie movie = new Movie();

    }
}