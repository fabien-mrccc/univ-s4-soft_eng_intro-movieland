package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FavoritesTest {
    private static Favorites favorites;
    private final Movie movie1 = new Movie();
    private final Movie movie2 = new Movie();
    private final Movie movie3 = new Movie();

    @BeforeAll
    static void setupBeforeAll(){
        favorites = Favorites.instance;
    }

    @Test
    void testAdd() {
    }

    @Test
    void testToString(){
        if(!favorites.isEmpty()){
            favorites.clear();
        }
        assertThat(favorites.toString()).isEqualTo("Your list of favorites is empty.");
        favorites.add(movie1);
        assertThat(favorites.toString()).isEqualTo(movie1.toString());
        favorites.add(movie2);
        favorites.add(movie3);
        assertThat(favorites.toString()).isEqualTo(
                movie1
                        + "\n"
                        + movie2
                        + "\n"
                        + movie3);
        favorites.remove(movie2);
        assertThat(favorites.toString()).isEqualTo(
                movie1
                        + "\n"
                        + movie3);
        favorites.remove(movie1);
        favorites.remove(movie3);
        assertThat(favorites.toString()).isEqualTo("Your list of favorites is empty.");
    }
}