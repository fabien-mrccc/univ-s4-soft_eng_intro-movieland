package moviesapp.model;

import moviesapp.model.favorites.Favorites;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(favorites.toString(), "Your list of movies is empty.");
        Movies moviesToAdd = new Movies();
        moviesToAdd.add(movie1);
        favorites.add(moviesToAdd);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie1);
        moviesToAdd.add(movie2);
        moviesToAdd.add(movie3);
        favorites.add(moviesToAdd);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        Movies moviesToRemove = new Movies();
        moviesToRemove.add(movie2);
        favorites.remove(moviesToRemove);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie3);
        moviesToRemove.remove(movie2);
        moviesToRemove.add(movie1);
        moviesToRemove.add(movie3);
        favorites.remove(moviesToRemove);
        assertEquals(favorites.toString(), "Your list of movies is empty.");
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
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1);
        movies.add(movie2);
        movies.add(movie3);
        favorites.add(movies);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        favorites.add(movies);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        favorites.add(null);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1  + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
        Movies emptyList = new Movies();
        favorites.add(emptyList);
        assertEquals(favorites.toString(),
                indexOfMovie(1) + movie1 + indexOfMovie(2) + movie2 + indexOfMovie(3) + movie3);
    }
    @Test
    void testRemove(){
        movies.add(movie1);
        favorites.add(movies);
        favorites.remove(movies);
        assertEquals(favorites.toString(), "Your list of movies is empty.");
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies);
        movies.remove(movie2);
        favorites.remove(movies);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
        movies.add(movie1);
        movies.add(movie2);
        favorites.add(movies);
        movies.remove(movie2);
        movies.add(movie3);
        favorites.remove(movies);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
        favorites.remove(null);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
        Movies emptyList = new Movies();
        favorites.remove(emptyList);
        assertEquals(favorites.toString(), indexOfMovie(1) + movie2);
    }

    private String indexOfMovie(int index){
        return "| n°0" + index;
    }
}