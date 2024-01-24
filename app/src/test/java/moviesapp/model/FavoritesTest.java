package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FavoritesTest {

    @Test
    void testAdd(){
        Favorites favorites = new Favorites();
        Movie movie1 = new Movie();

    }

    @Test
    void testContains(){
        Favorites favorites = new Favorites();
        Movie movie = new Movie();

        assertThat(favorites.contains(movie)).isEqualTo(false);
        favorites.add(movie);
        assertThat(favorites.contains(movie)).isEqualTo(true);
    }
}
