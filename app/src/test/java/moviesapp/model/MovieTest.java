package moviesapp.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
public class MovieTest {
    @Test
    void testDetails(){
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(0);
        Movie movie1 = new Movie(true,"backdropPath",genreIds,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDate",
                "title",false,3,4);
        assertThat(movie1.details()).isEqualTo("__________________________  \nmovie n°1:\n  title: title\n  original title: originalTitle"+
                "\n  release date: releaseDate\n  original language: originalLanguage\n  genreIds: "+genreIds+
                "\n  popularity: 2.0\n  adult: true\n  vote average: 3.0\n  vote count: 4\n  overview: overview\n__________________________  ");
    }

    @Test
    void testToString(){
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(0);
        Movie movie1 = new Movie(true,"backdropPath",genreIds,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDateTest",
                "title",false,3,4);

        String expectedString =   " |__________________" +
                "\n  title: title" +
                "\n  vote average: 3.0" +
                "\n  release year: rele\n" +
                "__________________________\n";

        assertThat(movie1.toString()).isEqualTo(expectedString);

    }

    @Test
    void testToStringWithID(){
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(0);
        Movie movie1 = new Movie(true,"backdropPath",genreIds,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDate",
                "title",false,3,4);
        assertThat(movie1.toStringWithID())
                .isEqualTo("__________________________  \n  title: title\n  vote average: 3.0\n  release year: rele\n  id: 1\n__________________________  ");
    }
}
