package moviesapp.controller;
import moviesapp.model.JSONReader;
import org.junit.jupiter.api.BeforeAll;

public class CLControllerTest {
    private static JSONReader jsonReader;

    private static CLController controller;

    @BeforeAll
    static void setupBeforeAll(){
        jsonReader = new JSONReader(System.getProperty("user.dir")+"/src/test/java/moviesapp/model/data_example.json");
        controller = new CLController();
    }
}
