package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
    private final File jsonFile;
    private final ObjectMapper objectMapper;

    public JSONReader(String path){
        jsonFile = new File(path);
        objectMapper = new ObjectMapper();
    }

    /**
     * Return a Movie containing its information from the JSON file selected with the movieID provided in parameter
     * @param movieID: the id of the movie that we want recover data from the JSON file
     */
    public Movie findMovie(int movieID) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonFile);
            JsonNode movieSelected = selectJsonNode(jsonNode.get("results"), movieID);

            if(movieSelected != null){
                return jsonNodeToMovie(movieSelected);
            }
        }
        catch (IOException e) {
            System.err.println("IOException: objectMapper.readTree(jsonFile) exception");
        }
        return null;
    }

    /**
     * Select a JsonNode in a bunch of JsonNode according to an id
     * @param jsonNodes: a bunch of JsonNode to browse
     * @param id: the id of the JsonNode searched
     * @return the JsonNode searched, or null
     */
    private JsonNode selectJsonNode(JsonNode jsonNodes, int id){
        for(JsonNode jsonNode : jsonNodes ){
            if(jsonNode.get("id").asInt() == id){
                return jsonNode;
            }
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
        JsonNode jsonMovies = getJsonMoviesNode();

        if(jsonMovies != null){
            for(JsonNode jsonMovie : jsonMovies ){
                movieList.add(findMovie(jsonMovie.get("id").asInt()));
            }
            return movieList;
        }
        return null;
    }

    /**
     * Return the origin jsonNode from our default jsonFile with exception management
     * @return the origin jsonNode from our default jsonFile
     */
    private JsonNode getJsonMoviesNode(){
        try{
            return objectMapper.readTree(jsonFile).get("results");
        }
        catch (IOException e) {
            System.err.println("IOException: objectMapper.readTree(jsonFile) exception");
        }
        catch (NullPointerException e){
            System.err.println("NullPointerException: objectMapper.readTree(jsonFile).get(\"results\") exception");
        }
        return null;
    }
}