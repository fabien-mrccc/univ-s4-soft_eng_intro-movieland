package moviesapp.controller;
import moviesapp.model.Movie;
import moviesapp.model.JSONReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import moviesapp.model.Favorites;

import static org.assertj.core.api.Assertions.assertThat;

public class CLControllerTest {
    private static JSONReader jsonReader;

    @BeforeAll
    static void setupBeforeAll(){
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");
    }
    @Test
    public void clearTest(){
        CLController controlle = new CLController() ;
        Movie movie = jsonReader.findMovie(32571) ;
        Movie movie2 = jsonReader.findMovie(32571) ;
        Favorites.instance.add(movie);
        Favorites.instance.add(movie2);
        controlle.clear("yes");
        assertThat(Favorites.instance.isEmpty()).isTrue();




    }

}
