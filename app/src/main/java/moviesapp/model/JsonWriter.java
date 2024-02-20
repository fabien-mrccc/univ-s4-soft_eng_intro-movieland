package moviesapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
    private final File jsonFile;
    private final ObjectMapper objectMapper;

    public JsonWriter(String path){
        jsonFile = new File(path);
        objectMapper = new ObjectMapper();
    }

    /**
     * Clean the json file by emptying it.
     */
    public void clean(){
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error truncating file: " + e.getMessage());
        }
    }
}
