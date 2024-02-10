package moviesapp.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.FileWriter;

public class tmdbAPI {

    String fileName = "jsonRequest.json";
    OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "https://api.themoviedb.org/3/search/movie?query=";
    private final static String apiKey = "5e40bf6f22600832c99dbb5d52115269";

    public void SearchTitle(String title){
        String url = baseUrl + title + "&api_key=" + apiKey;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String searchResult = response.body().string();
                try(FileWriter fileWriter = new FileWriter(fileName)){
                    fileWriter.write(searchResult);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("error :" + response.code());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
