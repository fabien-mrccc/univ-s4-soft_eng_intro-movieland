package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
public class MovieTest {

    static List<String> genres;

    @BeforeAll
    static void setup(){
        genres = new ArrayList<>();
        genres.add("action");
    }

    @Test
    void testDetails(){
        Movie movie1 = new Movie(true,"backdropPath",genres,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDate",
                "title",false,3,4);
        assertThat(movie1.details()).isEqualTo("__________________________  \nmovie n°1:\n  title: title\n  original title: originalTitle"+
                "\n  release date: releaseDate\n  original language: originalLanguage\n  genreIds: "+genres+
                "\n  popularity: 2.0\n  adult: true\n  vote average: 3.0\n  vote count: 4\n  overview: overview\n__________________________  ");
    }

    @Test
    void testToString(){
        Movie movie1 = new Movie(true,"backdropPath",genres,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDateTest",
                "title",false,3,4);

        String expectedString = """
                 |__________________
                  title: title
                  vote average: 3.0
                  release year: rele
                __________________________
                """;

        assertThat(movie1.toString()).isEqualTo(expectedString);

    }

    @Test
    void testToStringWithID(){
        Movie movie1 = new Movie(true,"backdropPath",genres,"1","originalLanguage",
                "originalTitle","overview",2,"posterPath","releaseDate",
                "title",false,3,4);
        assertThat(movie1.toStringWithID())
                .isEqualTo("__________________________  \n  title: title\n  vote average: 3.0\n  release year: rele\n  id: 1\n__________________________  ");
    }
}
