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

        assertThat(favorites.contains(movie1)).isEqualTo(false);
        favorites.add(movie1);
        assertThat(favorites.contains(movie1)).isEqualTo(true);
    }

    @Test
    void testContains() {
        Movie movie = new Movie();

        assertThat(favorites.contains(movie)).isEqualTo(false);
        favorites.add(movie);
        assertThat(favorites.contains(movie)).isEqualTo(true);
        assertThat(favorites.contains(null)).isEqualTo(false);
    }
}