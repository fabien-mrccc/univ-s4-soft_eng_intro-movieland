package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONReader extends SearchMovies {
    private final File jsonFile;
    private final ObjectMapper objectMapper;
    private final JsonNode jsonMovies ;
    public JSONReader(String path){
        jsonFile = new File(path);
        objectMapper = new ObjectMapper();
        jsonMovies = getJsonMoviesNode() ;
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
                jsonNode.get("id").asText(),
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
    public void findMoviesByName(Movies movies, String name ) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("original_title").asText().toLowerCase().contains(name.toLowerCase())) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }
    public void findMoviesByYear(Movies movies , String year ) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("release_date").asText().toLowerCase().contains(year.toLowerCase())) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }
    public void findMoviesByNameAndYear(Movies movies, String name, String year) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("release_date").asText().toLowerCase().contains(year.toLowerCase())
                    && movie.get("original_title").asText().toLowerCase().contains(name.toLowerCase())) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }

    /**
     * Return a list of Movie containing there information from the JSON file
     * @return the list of Movie contained in the JSON File
     */
    public Movies findAllMovies(){
        Movies movieList = new Movies();
        if(jsonMovies != null){
            for(JsonNode jsonMovie : jsonMovies ){
                movieList.add(jsonNodeToMovie(jsonMovie));
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