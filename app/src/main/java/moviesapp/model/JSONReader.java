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
                JsonNode jsonGenreIds = movieSelected.get("genre_ids");
                List<Integer> genreIds = new ArrayList<>();

                for(JsonNode jsonGenreId: jsonGenreIds){
                    genreIds.add(jsonGenreId.asInt());
                }

                return new Movie(
                        movieSelected.get("adult").asBoolean(),
                        movieSelected.get("backdrop_path").asText(),
                        genreIds,
                        movieSelected.get("id").asInt(),
                        movieSelected.get("original_language").asText(),
                        movieSelected.get("original_title").asText(),
                        movieSelected.get("overview").asText(),
                        movieSelected.get("popularity").asDouble(),
                        movieSelected.get("poster_path").asText(),
                        movieSelected.get("release_date").asText(),
                        movieSelected.get("title").asText(),
                        movieSelected.get("video").asBoolean(),
                        movieSelected.get("vote_average").asDouble(),
                        movieSelected.get("vote_count").asInt()
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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