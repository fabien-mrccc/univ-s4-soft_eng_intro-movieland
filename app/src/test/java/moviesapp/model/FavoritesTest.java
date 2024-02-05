package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FavoritesTest {
    private static Favorites favorites;
    private final Movie movie1 = new Movie(true,null,null,"1",null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie2 = new Movie(true,null,null,"2",null,
            null, null,0,null,null,null,true,
            0, 0);
    private final Movie movie3 = new Movie(true,null,null,"3",null,
            null, null,0,null,null,null,true,
            0, 0);

    private final Movies movies = new Movies();

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
        Movies moviesToAdd = new Movies();
        moviesToAdd.add(movie1);
        favorites.add(moviesToAdd);
        assertThat(favorites.toString().equals(movie1 + "\n")).isTrue();
        moviesToAdd.add(movie2);
        moviesToAdd.add(movie3);
        favorites.add(moviesToAdd);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
        Movies moviesToRemove = new Movies();
        moviesToRemove.add(movie2);
        favorites.remove(moviesToRemove);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie3 + "\n")).isTrue();
        moviesToRemove.remove(movie2);
        moviesToRemove.add(movie1);
        moviesToRemove.add(movie3);
        favorites.remove(moviesToRemove);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
    }

    @Test
    void testClear(){
        assertThat(favorites.isEmpty()).isTrue();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
        Movies moviesToAdd = new Movies();
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
        Movies moviesToAdd = new Movies();
        moviesToAdd.add(movie2);
        favorites.add(moviesToAdd);
        assertThat(favorites.isEmpty()).isFalse();
        favorites.clear();
        assertThat(favorites.isEmpty()).isTrue();
    }

    @Test
    void testAdd(){
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
        Movies emptyList = new Movies();
        favorites.add(emptyList);
        assertThat(favorites.toString().equals(
                movie1 + "\n" + movie2 + "\n" + movie3 + "\n")).isTrue();
    }
    @Test
    void testRemove(){
        movies.add(movie1);
        favorites.add(movies);
        favorites.remove(movies);
        assertThat(favorites.toString().equals("Your list of favorites is empty.")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies); // favorites contains movie1 and movie2
        movies.remove(movie2);
        favorites.remove(movies);// we want to remove movie2 from favorites
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies); // favorites contains movie1 and movie2
        movies.remove(movie2);
        movies.add(movie3);
        favorites.remove(movies);// we want to remove movie1 and movie3 from favorites
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        favorites.remove(null);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
        Movies emptyList = new Movies();
        favorites.remove(emptyList);
        assertThat(favorites.toString().equals(movie2 + "\n")).isTrue();
    }
}