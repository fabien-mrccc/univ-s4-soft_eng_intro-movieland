package moviesapp.model;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
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
     * Return a list of movies containing there information from the JSON file selected with a name(optional) and year (optional) provided in parameter.
     * @param name the name of the movie
     * @param year the release year of the movie
     * @return return a list of movies
     */
    public List<Movie> findMovies(String name , String year){
        if(name == null && year == null){
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        if(name != null && year == null){
            findMoviesByName(movieList , name );
        }
        else if(name == null){
            findMoviesByYear(movieList , year );
        }
        else{
            findMovies(movieList , name , year);
        }
        return movieList;
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param name the name of the movie researched
     */
    private void findMoviesByName(List<Movie> movies , String name ) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("original_title").asText().contains(name)) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     */
    private void findMoviesByYear(List<Movie> movies , String year ) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("release_date").asText().contains(year)) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    private void findMovies(List<Movie> movies, String name, String year) {
        for (JsonNode movie : jsonMovies) {
            if(movie.get("release_date").asText().contains(year) && movie.get("original_title").asText().contains(name)) {
                movies.add(jsonNodeToMovie(movie));
            }
        }
    }

    /**
     * Return a list of Movie containing there information from the JSON file
     * @return the list of Movie contained in the JSON File
     */
    public List<Movie> findAllMovies(){
        List<Movie> movieList = new ArrayList<>();
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