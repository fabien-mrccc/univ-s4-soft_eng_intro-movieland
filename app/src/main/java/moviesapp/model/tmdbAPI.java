package moviesapp.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class tmdbAPI {

    String fileName = System.getProperty("user.dir") + "/src/main/java/moviesapp/model/api-results.json";
    OkHttpClient client = new OkHttpClient();
    private final static String baseUrl = "https://api.themoviedb.org/3/search/movie?query=";
    private final static String apiKey = "5e40bf6f22600832c99dbb5d52115269";
    private final static String language = "&language=en-US";

    public void searchMovie(String title){
        String url = urlBuilder(title);

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String searchResult = response.body().string();
                requestToFile(searchResult);
            }
            else{
                System.out.println("error :" + response.code());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * turns the response of an api request into a json file
     * @param result response of the api request
     */
    private void requestToFile(String result){
        try(FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8)){
            fileWriter.write(result);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * build an url from a query as a String
     * @param query part of a title you want to search
     * @return the url as a String
     */
    private String urlBuilder(String query){
        return baseUrl + query + language + "&page=3" + "&api_key=" + apiKey;
    }
}
