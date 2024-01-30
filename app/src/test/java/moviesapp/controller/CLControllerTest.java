package moviesapp.controller;
import moviesapp.model.Movie;
import moviesapp.model.JSONReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import moviesapp.model.Favorites;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CLControllerTest {
    private static JSONReader jsonReader;

    @BeforeAll
    static void setupBeforeAll(){
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /*

    @Test
    public void clearTest(){
        CLController controller = new CLController() ;
        Movie movie = jsonReader.findMovie(32571) ;
        Movie movie2 = jsonReader.findMovie(32571) ;
        Favorites.instance.add(movie);
        Favorites.instance.add(movie2);
        controller.clear("yes");
        assertThat(Favorites.instance.isEmpty()).isTrue();
    }

     */

    @Test
    void testExit(){

    }

}
