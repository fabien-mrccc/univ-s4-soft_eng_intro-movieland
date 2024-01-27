package moviesapp.model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JSONReaderTest {

    @Test
    void testFindMovie(){

        JSONReader jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");

        Movie movie = jsonReader.findMovie(32571);

        assertThat(movie.adult()).isFalse();
        assertThat(movie.backdropPath().equals("/3KiwdVe0XqLNrBVYrpVMlvZaPdm.jpg")).isTrue();
        assertThat(movie.genreIds().equals("[99]")).isTrue();
        assertThat(movie.id()).isEqualTo(32571);
        assertThat(movie.originalLanguage().equals("xx")).isTrue();
        assertThat(movie.originalTittle().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movie.overview().equals("Experimental film made to test the original cylinder format of the Kinet")).isTrue();
        assertThat(movie.popularity()).isEqualTo(6.209);
        assertThat(movie.posterPath().equals("/a41Z3Owp1TsXcUlBCRhbb9eJjWW.jpg")).isTrue();
        assertThat(movie.releaseDate().equals("1890-11-21")).isTrue();
        assertThat(movie.title().equals("Monkeyshines, No. 1")).isTrue();
        assertThat(movie.video()).isFalse();
        assertThat(movie.voteAverage()).isEqualTo(4.9);
        assertThat(movie.voteCount()).isEqualTo(90);
    }
}
