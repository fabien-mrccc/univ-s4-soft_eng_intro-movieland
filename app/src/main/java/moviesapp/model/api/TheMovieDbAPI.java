package moviesapp.model.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import moviesapp.model.movies.Movies;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static moviesapp.model.json.JsonReader.*;
import static moviesapp.model.json.JsonWriter.convertJsonToFile;

public class TheMovieDbAPI {

    static final OkHttpClient client = new OkHttpClient();
    private UrlRequestBuilder urlRequestBuilder;

    /**
     * Call every necessary methods to create the appropriate url from the given parameters
     * @param title part of or complete title of a movie
     * @param singleYearOrMinYear min year of release of movie
     * @param maxYear max year of release of movie
     * @param genres a list of genres
     * @param minVoteAverage the min vote average
     */
    public void searchMovies(String title, String singleYearOrMinYear, String maxYear, List<String> genres, String minVoteAverage , String page){
        urlRequestBuilder = new UrlRequestBuilder(title, singleYearOrMinYear, maxYear, genres, minVoteAverage , page);

        Request request = urlRequestBuilder.build();

        try {
            Response response = client.newCall(request).execute();
            reactToRequestResponse(response);

        } catch(IOException e){
            System.err.println("IOException e from 'Response response = client.newCall(request).execute();' ");
        }
    }

    /**
     * Convert the request response to a JSON file if it is successful or print an error.
     * @param response from the API after a specific request
     */
    private void reactToRequestResponse(Response response){
        try{
            if(response.isSuccessful() && response.body() != null){
                String searchResult = response.body().string();
                convertJsonToFile(searchResult, API_FILE_PATH);
            }
            else{
                System.err.println("Error API request: " + response.code());
            }
        } catch (IOException e){
            System.err.println("IOException e from 'String searchResult = response.body().string();'");
        }
    }

    public void popularMovies(String page){
        urlRequestBuilder = new UrlRequestBuilder(null, null,null,null,null, page);
        String url = urlRequestBuilder.popularMoviesBuilder(page);
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            reactToRequestResponse(response);

        } catch(IOException e){
            System.err.println("IOException e from 'Response response = client.newCall(request).execute();' ");
        }
    }
}
