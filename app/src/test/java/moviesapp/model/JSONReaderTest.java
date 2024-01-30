package moviesapp.model;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class JSONReaderTest {

    private static JSONReader jsonReader;

    @BeforeAll
    static void setupBeforeAll(){
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");
    }

    @Test
    void testFindMovie(){
        Movie movie = jsonReader.findMovie(32571);

        assertThat(movie.adult()).isFalse();
        assertThat(movie.backdropPath().equals("/3KiwdVe0XqLNrBVYrpVMlvZaPdm.jpg")).isTrue();
        assertThat(movie.genreIds().get(0)).isEqualTo(99);
        assertThat(movie.id()).isEqualTo(32571);
        assertThat(movie.originalLanguage().equals("xx")).isTrue();
        assertThat(movie.originalTitle().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movie.overview().equals("Experimental film made to test the original cylinder format of the Kinet")).isTrue();
        assertThat(movie.popularity()).isEqualTo(6.209);
        assertThat(movie.posterPath().equals("/a41Z3Owp1TsXcUlBCRhbb9eJjWW.jpg")).isTrue();
        assertThat(movie.releaseDate().equals("1890-11-21")).isTrue();
        assertThat(movie.title().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movie.video()).isFalse();
        assertThat(movie.voteAverage()).isEqualTo(4.9);
        assertThat(movie.voteCount()).isEqualTo(90);
    }

    @Test
    void testFindMovies(){
        List<Integer> idList = new ArrayList<>();
        idList.add(32571);
        idList.add(33315);
        List<Movie> movieList = jsonReader.findMovies(idList);
        assertThat(movieList.get(0).title().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movieList.get(0).voteCount() == 90).isTrue();
        assertThat(movieList.get(1).title().equals("Monkeyshines, No. 2")).isTrue();
        assertThat(movieList.get(1).voteCount() == 47).isTrue();
    }

    @Test
    void testFindAllMovies(){
        List<Movie> movieList = jsonReader.findAllMovies();
        assertThat(movieList.get(0).title().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movieList.get(0).voteCount() == 90).isTrue();
        assertThat(movieList.get(1).title().equals("Monkeyshines, No. 2")).isTrue();
        assertThat(movieList.get(1).voteCount() == 47).isTrue();
        assertThat(movieList.get(9).title().equals("Escrime")).isTrue();
        assertThat(movieList.get(9).voteCount() == 4).isTrue();
        assertThat(movieList.size() == 10).isTrue();
    }
}
