package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FavoritesTest {
    private static Favorites favorites;
    private final Movie movie1 = new Movie(true,null,null,1,null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie2 = new Movie(true,null,null,2,null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie3 = new Movie(true,null,null,3,null,
            null, null,0,null,null,null,true,
            0, 0);

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
    void testToString(){
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        List<Movie> moviesToAdd = new ArrayList<>();
        moviesToAdd.add(movie1);
        favorites.add(moviesToAdd);
        assertThat(favorites.toString().equals(movie1 + "\n")).isTrue();
        moviesToAdd.add(movie2);
        moviesToAdd.add(movie3);
        favorites.add(moviesToAdd);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        List<Movie> moviesToRemove = new ArrayList<>();
        moviesToRemove.add(movie2);
        favorites.removeAll(moviesToRemove);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie3 + "\n")).isTrue();
        moviesToRemove.remove(movie2);
        moviesToRemove.add(movie1);
        moviesToRemove.add(movie3);
        favorites.removeAll(moviesToRemove);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
    }
    @Test
    void testRemove(){
        List<Movie> moviesToAdd = new ArrayList<>();
        moviesToAdd.add(movie1);
        moviesToAdd.add(movie2);
        moviesToAdd.remove(movie1);
        favorites.add(moviesToAdd);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        favorites.removeAll(null);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        moviesToAdd.remove(movie2);
        favorites.removeAll(moviesToAdd);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isFalse();
        assertThatCode(() -> favorites.remove(movie2)).doesNotThrowAnyException();
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
    }

    @Test
    void testClear(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
        List<Movie> moviesToAdd = new ArrayList<>();
        moviesToAdd.add(movie1);
        moviesToAdd.add(movie2);
        favorites.add(moviesToAdd);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testIsEmpty(){
        assertThat(favorites.isEmpty()).isTrue();
        List<Movie> moviesToAdd = new ArrayList<>();
        moviesToAdd.add(movie2);
        favorites.add(moviesToAdd);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testAddAll(){
        movies.add(movie1);
        favorites.add(movies);
        assertThat(favorites.toString().equals(
                movie1 + "\n")).isTrue();
        movies.add(movie2);
        movies.add(movie3);
        favorites.add(movies);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.add(movies);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        favorites.add(null);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        List<Movie> emptyList = new ArrayList<>();
        favorites.add(emptyList);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
    }
    @Test
    void testRemoveAll(){
        movies.add(movie1);

        movies.add(movie1);
        favorites.add(movies);
        favorites.removeAll(movies);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies); // favorites contains movie1 and movie2
        movies.remove(movie2);
        favorites.removeAll(movies);// we want to remove movie2 from favorites
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies); // favorites contains movie1 and movie2
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