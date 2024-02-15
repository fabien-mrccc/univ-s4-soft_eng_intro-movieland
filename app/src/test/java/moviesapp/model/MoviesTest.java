package moviesapp.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MoviesTest {
    private final Movie movie1 = new Movie(true,null,null,"1",null,
            "movie1", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie2 = new Movie(true,null,null,"2",null,
            "movie2", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie3 = new Movie(true,null,null,"3",null,
            "movie3", null,0,null,"2023",null,true,
            0, 0);
    private final Movie movie4 = new Movie(true,null,null,"4",null,
            "movie4", null,0,null,"2023",null,true,
            0, 0);
    List<Movie> moviesToAddToMovies = new ArrayList<>();
    Movies emptyMovies = new Movies();
    @BeforeEach
    void setUpBeforeEach(){
        moviesToAddToMovies.add(movie1);
        moviesToAddToMovies.add(movie2);
        moviesToAddToMovies.add(movie3);
    }

    @Test
    void testToString(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.toString().equals(
                indexOfMovie(1) + movie1 + "\n" +
                        indexOfMovie(2) + movie2 + "\n" +
                        indexOfMovie(3) + movie3 + "\n")).isTrue();
        assertThat(emptyMovies.toString().equals("Your list of movies is empty.")).isTrue();
    }

    @Test
    void testToStringWithID(){
        Movies movies = new Movies(moviesToAddToMovies);
        System.out.println(movies.toStringWithID());
        assertThat(movies.toStringWithID().equals(
                movie1.toStringWithID() + "\n" + movie2.toStringWithID() + "\n" +
                        movie3.toStringWithID() + "\n")).isTrue();
        assertThat(emptyMovies.toStringWithID().equals("Your list of movies is empty.")).isTrue();
    }

    @Test
    void testAdd(){
        Movies movies = new Movies(moviesToAddToMovies);
        movies.add(movie4);
        assertThat(movies.toString().equals(
                indexOfMovie(1) + movie1 + "\n" +
                        indexOfMovie(2) + movie2 + "\n" +
                        indexOfMovie(3) + movie3 + "\n" +
                        indexOfMovie(4) + movie4 +"\n")).isTrue();
    }

    @Test
    void testIsEmpty(){
        assertThat(emptyMovies.isEmpty()).isTrue();
        moviesToAddToMovies.add(movie3);
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.isEmpty()).isFalse();
    }

    @Test
    void testNoMovieFound(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(Movies.noMovieFound(null)).isTrue();
        assertThat(Movies.noMovieFound(movies)).isFalse();

        assertThat(Movies.noMovieFound(emptyMovies)).isTrue();
    }

    @Test
    void testSize(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.size()).isEqualTo(3);
        movies.add(movie4);
        assertThat(movies.size()).isEqualTo(4);
        assertThat(emptyMovies.size()).isEqualTo(0);
    }

    @Test
    void testHasNext(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.iterator().hasNext()).isTrue();
        assertThat(emptyMovies.iterator().hasNext()).isFalse();
    }

    @Test
    void testNext(){
        Movies movies = new Movies();
        movies.add(movie1);
        assertThat(movies.iterator().next()).isEqualTo(movie1);
        assertThatThrownBy(() -> emptyMovies.iterator().next()).isInstanceOf
                (IllegalStateException.class).hasMessage("No more elements in the iterator.");
    }

    @Test
    void testFindMovie(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.findMovieByIndex(0)).isEqualTo(movie1);
        assertThat(movies.findMovieByIndex(1)).isEqualTo(movie2);
        assertThat(movies.findMovieByIndex(2)).isEqualTo(movie3);
        assertThat(movies.findMovieByIndex(3)).isEqualTo(null);
    }

    @Test
    void testRemove(){
        Movies movies = new Movies(moviesToAddToMovies);
        movies.remove(movie1);
        assertThat(movies.toString().equals(indexOfMovie(1) + movie2 + "\n" + indexOfMovie(2) + movie3 + "\n")).isTrue();
        movies.remove(null);
        assertThat(movies.toString().equals(indexOfMovie(1) + movie2 + "\n" + indexOfMovie(2) + movie3 + "\n")).isTrue();
        movies.remove(movie1);
        assertThat(movies.toString().equals(indexOfMovie(1) + movie2 + "\n" + indexOfMovie(2) + movie3 + "\n")).isTrue();
    }

    @Test
    void testGet(){
        Movies movies = new Movies(moviesToAddToMovies);
        assertThat(movies.get(0)).isEqualTo(movie1);
        assertThat(movies.get(1)).isEqualTo(movie2);
        assertThat(movies.get(2)).isEqualTo(movie3);
    }

    private String indexOfMovie(int index){
        return "| n°0" + index;
    }
}
