package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static moviesapp.model.TmdbAPI.GENRE_ID_MAP;

public class JSONReader extends SearchMovies {
    private final File jsonFile;
    private final ObjectMapper objectMapper;
    private final JsonNode jsonMovies ;
    private final JsonNode jsonGenres;

    public JSONReader(String path){
        jsonFile = new File(path);
        objectMapper = new ObjectMapper();
        jsonMovies = getJsonMoviesNode() ;
        jsonGenres = getJsonGenresNode();
    }

    /**
     * Convert a jsonNode to a Movie
     * @param jsonNode: the jsonNode to convert to a movie
     * @return the jsonNode converted to a movie
     */
    private Movie jsonNodeToMovie(JsonNode jsonNode) {

        return new Movie(
                jsonNode.get("adult").asBoolean(),
                jsonNode.get("backdrop_path").asText(),
                getGenreIdsFromJSON(jsonNode),
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

    /**
     * Browse genre_ids jsonNode to collect values to store in a list
     * @param jsonNode to browse
     * @return the list of genre identifiers
     */
    private List<Integer> getGenreIdsFromJSON(JsonNode jsonNode){
        JsonNode jsonGenreIds = jsonNode.get("genre_ids");
        List<Integer> genreIds = new ArrayList<>();

        for(JsonNode jsonGenreId: jsonGenreIds){
            genreIds.add(jsonGenreId.asInt());
        }

        return genreIds;
    }

    @Override
    public void findMoviesByCriteria(Movies movies, String name, String year) {
        for (JsonNode movie : jsonMovies) {
            boolean nameCondition = (name == null) || movie.get("original_title").asText().toLowerCase().contains(name.toLowerCase());
            boolean yearCondition = (year == null) || movie.get("release_date").asText().toLowerCase().contains(year.toLowerCase());

            if (nameCondition && yearCondition) {
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
        return getSpecificJsonNode("results");
    }

    /**
     * Return the number of total pages of movies available in the json file of the class
     * @return the number of total pages of movies available in the json file of the class
     */
    public int numberOfPagesOfMoviesInJson(){
        JsonNode totalPagesNode = getSpecificJsonNode("total_pages");

        if(totalPagesNode != null){
            return totalPagesNode.asInt();
        }

        return 0;
    }
    /**
     * Return a specific jsonNode from our json file of the class
     * @return a specific jsonNode from our json file of the class
     */
    private JsonNode getSpecificJsonNode(String jsonNodeName){
        try{
            return objectMapper.readTree(jsonFile).get(jsonNodeName);
        }
        catch (IOException e) {
            System.err.println("IOException: objectMapper.readTree(jsonFile) exception");
        }
        catch (NullPointerException e){
            System.err.println("NullPointerException: objectMapper.readTree(jsonFile).get(\"results\") exception");
        }
        return null;
    }

    /**
     * Return the origin jsonNode from our default jsonFile with exception management
     * @return the origin jsonNode from our default jsonFile
     */
    private JsonNode getJsonGenresNode() {
        try {
            return objectMapper.readTree(jsonFile).get("genres");
        } catch (IOException e) {
            System.err.println("IOException: objectMapper.readTree(jsonFile) exception");
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: objectMapper.readTree(jsonFile).get(\"genres\") exception");
        }
        return null;
    }

    public JsonNode getJsonGenres(){
        return jsonGenres;
    }

}