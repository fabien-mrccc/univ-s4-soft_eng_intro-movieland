package moviesapp.model.json;

import moviesapp.model.Movies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
    private final File jsonFile;

    public JsonWriter(String path){
        jsonFile = new File(path);
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

    /**
     * Saves the favorite movies to a JSON file.
     * @param favorites the list of favorite movies
     */
    public void saveFavorites(Movies favorites) {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("{\n");
            writer.write(favorites.toJsonFormat());

            writer.write(",\n");
            writer.write("  \"total_pages\" : 1,\n");
            writer.write("  \"total_results\" : " + favorites.size() + "\n");
            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
