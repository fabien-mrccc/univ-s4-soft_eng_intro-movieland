package moviesapp.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class TmdbAPITest {
    private static TmdbAPI api;

    @BeforeAll
    static void setupBeforeAll() {
        api = new TmdbAPI();
    }

    @Test
    void searchMovieTest() {
        api.searchMovie("", "1890",null , null);

        String apiResultsJSONContent = readContentFromAPIResultsJSON();
        String dataExampleJSONContent = readContentFromDataExampleJSON();

        assertThat(apiResultsJSONContent).isNotNull();
        assertThat(dataExampleJSONContent).isNotNull();
        assertThat(apiResultsJSONContent).isEqualTo(dataExampleJSONContent);
    }

    private String readContentFromAPIResultsJSON(){
        return readContentFrom("/src/main/java/moviesapp/model/api-results.json");
    }

    private String readContentFromDataExampleJSON(){
        return readContentFrom("/src/test/java/moviesapp/model/data-example.json");
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
}
