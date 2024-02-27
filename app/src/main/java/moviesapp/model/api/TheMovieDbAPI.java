package moviesapp.model.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import moviesapp.controller.command_line.CLController;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static moviesapp.model.json.JsonReader.apiFilePath;

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
                searchResultFromRequestToFile(searchResult);
            }
            else{
                System.err.println("Error API request: " + response.code());
            }
        } catch (IOException e){
            System.err.println("IOException e from 'String searchResult = response.body().string();'");
        }
    }

    /**
     * Turns the response to an API request into a JSON file
     * @param searchResult response of the api request
     */
    public void searchResultFromRequestToFile(String searchResult){
        ObjectMapper mapper = JsonMapper.builder().build();

        try {
            ObjectNode node = mapper.readValue(searchResult, ObjectNode.class);

            try (FileWriter fileWriter = new FileWriter(apiFilePath, StandardCharsets.UTF_8)) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileWriter, node);
            } catch (IOException e) {
                System.err.println("IOException from 'new FileWriter(...)' or 'mapper.writeValue(fileWriter, node)'");
            }

        } catch (JsonProcessingException e){
            System.err.println("JsonProcessingException from 'ObjectNode node = mapper.readValue(searchResult, ObjectNode.class);'");
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
