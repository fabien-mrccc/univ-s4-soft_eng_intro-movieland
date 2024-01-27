package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
    private final File jsonFile;

    public JSONReader(String path){
        jsonFile = new File(path);
    }

    /**
     * Return a Movie containing its information from the JSON file selected with the movieID provided in parameter
     * @param movieID: the id of the movie that we want recover data from the JSON file
     */
    public Movie findMovie(int movieID) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            JsonNode movieSelected = null;

            JsonNode jsonMovies = jsonNode.get("results");

            for(JsonNode jsonMovie : jsonMovies ){
                if(jsonMovie.get("id").asInt() == movieID){
                    movieSelected = jsonMovie;
                }
            }

            if(movieSelected != null){
                return jsonNodeToMovie(movieSelected);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert a jsonNode to a Movie
     * @param jsonNode: the jsonNode to convert to a movie
     * @return the jsonNode converted to a movie
     */
    private Movie jsonNodeToMovie(JsonNode jsonNode){
        JsonNode jsonGenreIds = jsonNode.get("genre_ids");
        List<Integer> genreIds = new ArrayList<>();

        for(JsonNode jsonGenreId: jsonGenreIds){
            genreIds.add(jsonGenreId.asInt());
        }

        return new Movie(
                jsonNode.get("adult").asBoolean(),
                jsonNode.get("backdrop_path").asText(),
                genreIds,
                jsonNode.get("id").asInt(),
                jsonNode.get("original_language").asText(),
                jsonNode.get("original_title").asText(),
                jsonNode.get("overview").asText(),
                jsonNode.get("popularity").asDouble(),
                jsonNode.get("poster_path").asText(),
                jsonNode.get("release_date").asText(),
                jsonNode.get("title").asText(),
                jsonNode.get("video").asBoolean(),
                jsonNode.get("vote_average").asDouble(),
                jsonNode.get("vote_count").asInt()
        );
    }

    /**
     * Return a list of Movie containing there information from the JSON file selected with a list of movieID provided in parameter
     * @param moviesID: the list of movieID to select movies in JSON File
     * @return the list of Movie selected according to the list of id
     */
    public List<Movie> findMovies(List<Integer> moviesID){
        List<Movie> movieList = new ArrayList<>();
        for (Integer movieID : moviesID){
            movieList.add(findMovie(movieID));
        }
        return movieList;
    }

    /**
     * Return a list of Movie containing there information from the JSON file
     * @return the list of Movie contained in the JSON File
     */
    public List<Movie> findAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            JsonNode jsonMovies = jsonNode.get("results");

            for(JsonNode jsonMovie : jsonMovies ){
                movieList.add(findMovie(jsonMovie.get("id").asInt()));
            }
            return movieList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}