package moviesapp.model;

import moviesapp.controller.command_line.CLController;
import moviesapp.model.api.RequestBuilder;
import moviesapp.model.api.SearchCriteria;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.SelectModeException;
import moviesapp.model.json.JsonReader;
import moviesapp.model.movies.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.api.Genres.GENRE_NAME_ID_MAP;
import static moviesapp.model.api.Genres.fillGENRE_NAME_ID_MAP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TheMovieDbAPITest {
    private static TheMovieDbAPI api;

    @BeforeAll
    static void setupBeforeAll() {
        api = new TheMovieDbAPI();
    }

    @Test
    void testSearchMoviesWithCriteria() throws SelectModeException {
        TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",new ArrayList<>(),"",""));
        assertThrows(SelectModeException.class, () -> {TheMovieDbAPI.searchMoviesWithCriteria(new SearchCriteria("","","",new ArrayList<>(),"",""));
        });
        /*String title = "One Piece";
        String releaseYear = "2000";
        List<String> genres = new ArrayList<>();
        genres.add("action");
        genres.add("adventure");
        String minVoteAverage = "3";
        String page = "1";*/

        //api.searchMovies(title, releaseYear, genres, minVoteAverage , page);

        /*
        JsonReader apiResultJsonReader = new JsonReader(apiJsonPath);

        assertThat(JsonReader.isFileEmpty(apiJsonPath)).isFalse();
        System.out.println(apiResultJsonReader.getPageInJson());
        System.out.println(Integer.parseInt(page));
        assertThat(apiResultJsonReader.getPageInJson() == Integer.parseInt(page)).isTrue();

        for(Movie movie : apiResultJsonReader.findAllMovies()){
            assertThat(movie.title().equals(title)).isTrue();
            assertThat(movie.releaseDate().contains(releaseYear)).isTrue();
            assertThat(movie.genres().equals(genres)).isTrue();
            assertThat(movie.minVoteAverage() == Double.parseDouble(minVoteAverage)).isTrue();
        }

         */
    }

    @Test
    void testFillGENRE_ID_MAP(){
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isTrue();
        fillGENRE_NAME_ID_MAP();
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isFalse();
        assertEquals(GENRE_NAME_ID_MAP.toString(),(  "{Action=28, Adventure=12, Animation=16, Comedy=35, Crime=80, Documentary=99, " +
                "Drama=18, Family=10751, Fantasy=14, History=36, Horror=27, Music=10402, Mystery=9648, Romance=10749, Science Fiction=878, " +
                "TV Movie=10770, Thriller=53, War=10752, Western=37}"));

    }
}
