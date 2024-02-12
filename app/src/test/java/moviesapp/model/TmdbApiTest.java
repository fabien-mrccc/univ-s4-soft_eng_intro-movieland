package moviesapp.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TmdbApiTest {
    private static TmdbAPI api;
    private static OkHttpClient client;

    @BeforeAll
    static void setupBeforeAll() {
        api = new TmdbAPI();
        client = new OkHttpClient();
    }

    @Test
    void searchMovieTest() {
        String urlTest = "https://api.themoviedb.org/3/discover/movie?language=en-US&vote_average.gte=6&primary_release_year=2012&with_genres=28,12&api_key=5e40bf6f22600832c99dbb5d52115269";
        Request testRequest = new Request.Builder().url(urlTest).build();
        List<String> genre = new ArrayList<>();
        genre.add("action");
        genre.add("adventure");
        String title = "";

        api.searchMovie(title, "2012", genre, "6");

        String searchResult = null;
        try {
            Response testResponse = client.newCall(testRequest).execute();
            if (testResponse.isSuccessful()) {
                searchResult = testResponse.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileContent = null;
        try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/src/main/java/moviesapp/model/api-results.json", StandardCharsets.UTF_8)) {
            StringBuilder stringBuilder = new StringBuilder();
            int character;
            while ((character = reader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            fileContent = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assertions
        assertThat(searchResult).isNotNull();
        assertThat(fileContent).isNotNull();
        assertThat(searchResult).isEqualTo(fileContent);
    }
}
