package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static moviesapp.model.TmdbAPI.GENRE_NAME_ID_MAP;
import static moviesapp.model.TmdbAPI.fillGENRE_NAME_ID_MAP;
import static org.assertj.core.api.Assertions.assertThat;

public class TmdbAPITest {
    private static TmdbAPI api;
    private static String apiJsonPath;
    private static JsonReader apiJsonReader;

    @BeforeAll
    static void setupBeforeAll() {
        api = new TmdbAPI();
        apiJsonPath = "/src/main/java/moviesapp/model/json/api-results.json";
        apiJsonReader = new JsonReader(apiJsonPath);
    }

    @Test
    void testSearchMovies() {
        String title = "One Piece";
        String releaseYear = "2000";
        List<String> genres = new ArrayList<>();
        genres.add("action");
        genres.add("adventure");
        String voteAverage = "3";
        String page = "1";

        api.searchMovies(title, releaseYear, genres, voteAverage , page);

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
            assertThat(movie.voteAverage() == Double.parseDouble(voteAverage)).isTrue();
        }

         */
    }

    private String readContentFrom(String path){
        String fileContent = null;
        StringBuilder stringBuilder = new StringBuilder();
        int character;

        try (FileReader reader = new FileReader(System.getProperty("user.dir") + path, StandardCharsets.UTF_8)) {

            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            fileContent = stringBuilder.toString();

        } catch (IOException e) {
            System.err.println("IOException e from 'new FileReader(...)'");
        }
        return fileContent;
    }

    @Test
    void testFillGENRE_ID_MAP(){
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isTrue();
        fillGENRE_NAME_ID_MAP();
        assertThat(GENRE_NAME_ID_MAP.isEmpty()).isFalse();
        assertThat(GENRE_NAME_ID_MAP.toString()).isEqualTo(  "{Action=28, Adventure=12, Animation=16, Comedy=35, Crime=80, Documentary=99, " +
                "Drama=18, Family=10751, Fantasy=14, History=36, Horror=27, Music=10402, Mystery=9648, Romance=10749, Science Fiction=878, " +
                "TV Movie=10770, Thriller=53, War=10752, Western=37}");
    }
}
