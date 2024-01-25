package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FavoritesTest {
    private static Favorites favorites;
    private final Movie movie1 = new Movie();
    private final Movie movie2 = new Movie();
    private final Movie movie3 = new Movie();

    private final List<Movie> movies = new ArrayList<>();

    @BeforeAll
    static void setupBeforeAll(){
        favorites = Favorites.instance;
    }

    @BeforeEach
    void setupBeforeEach(){
        if(!favorites.isEmpty()){
            favorites.clear();
        }
    }

    @Test
    void testAdd() {
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        favorites.add(movie1);
        assertThat(favorites.toString().equals(movie1 + "\n")).isTrue();
        favorites.add(movie2);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n")).isTrue();
        favorites.add(movie3);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.add(null);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.add(movie3);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
    }

    @Test
    void testToString(){
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        favorites.add(movie1);
        assertThat(favorites.toString().equals(movie1 + "\n")).isTrue();
        favorites.add(movie2);
        favorites.add(movie3);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.remove(movie2);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie3 + "\n")).isTrue();
        favorites.remove(movie1);
        favorites.remove(movie3);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
    }
    @Test
    void testRemove(){
        favorites.add(movie1);
        favorites.add(movie2);
        favorites.remove(movie1);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        favorites.remove(null);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        favorites.remove(movie2);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        assertThatCode(() -> favorites.remove(movie2)).doesNotThrowAnyException();
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
    }

    @Test
    void testClear(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
        favorites.add(movie1);
        favorites.add(movie2);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testIsEmpty(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.add(movie2);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testAddAll(){
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        favorites.addAll(movies);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.addAll(movies);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.addAll(null);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        List<Movie> emptyList = new ArrayList<>();
        favorites.addAll(emptyList);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
    }
    @Test
    void testRemoveAll(){
        movies.add(movie1);
        favorites.addAll(movies);
        favorites.removeAll(movies);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.addAll(movies); // favorites contains movie1 and movie2
        movies.remove(movie2);
        favorites.removeAll(movies);// we want to remove movie2 from favorites
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.addAll(movies); // favorites contains movie1 and movie2
        movies.remove(movie2);
        movies.add(movie3);
        favorites.removeAll(movies);// we want to remove movie1 and movie3 from favorites
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        favorites.removeAll(null);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        List<Movie> emptyList = new ArrayList<>();
        favorites.removeAll(emptyList);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
    }
}