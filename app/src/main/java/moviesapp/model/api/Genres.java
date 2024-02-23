package moviesapp.model.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import moviesapp.controller.command_line.CLController;
import moviesapp.model.json.JsonReader;
import moviesapp.model.movies.Favorites;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static moviesapp.model.api.TheMovieDbAPI.client;
import static moviesapp.model.api.UrlRequestBuilder.apiKey;
import static moviesapp.model.api.UrlRequestBuilder.baseUrl;

public class Genres {

    public static final Genres instance = new Genres();

    public static final TreeMap<String, String> GENRE_NAME_ID_MAP = new TreeMap<>();

    /**
     * Update the genres.json then fill the static GENRE_ID_MAP with the genres located in genres.json
     */
    public static void fillGENRE_NAME_ID_MAP(){
        updateGenresFile();
        JsonReader jsonGenresReader = new JsonReader(CLController.genresFilePath);
        for(JsonNode genre : jsonGenresReader.getJsonGenres()){
            GENRE_NAME_ID_MAP.put(genre.get("name").asText(),genre.get("id").asText());
        }
    }

    /**
     * Update the genres.json with all genres from Tmdb
     */
    private static void updateGenresFile(){
        String url = baseUrl + "/genre/movie/list?language=en" + apiKey;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                assert response.body() != null;
                String genresResult = response.body().string();
                requestToGenresFile(genresResult);
            }
            else{
                System.out.println("error :" + response.code());
            }
        }catch(IOException e){
            System.err.println("IOException e from 'Response response = client.newCall(request).execute();' ");
        }
    }

    /**
     * Turns the response of an api request into a json file filled with genres
     * @param result response of the api request
     */
    private static void requestToGenresFile(String result){
        try {
            ObjectMapper mapper = JsonMapper.builder().build();
            ObjectNode node = mapper.readValue(result, ObjectNode.class);
            try (FileWriter fileWriter = new FileWriter(CLController.genresFilePath, StandardCharsets.UTF_8)) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileWriter, node);
            }
        }catch (IOException e){
            System.err.println("IOException from 'new FileWriter(...)' or 'mapper.writeValue(fileWriter, node)'");
        }
    }

    /**
     * Return a list of every registered genres
     * @return list of every genre
     */
    public StringBuilder getGenres(){
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (String genre : GENRE_NAME_ID_MAP.keySet()) {
            list.append("  • [").append(i).append("] ").append(genre).append("\n");
            i++;
        }
        return list;
    }

    /**
     * Return a list of genre ids from a list of genre
     * @param genres a list of genres
     * @return a list of ids
     */
    public static List<String> genresToGenreIds(List<String> genres){
        if(genres == null){
            return null;
        }

        List<String> genreIds = new ArrayList<>();

        for(String genre : genres){
            genre = genre.substring(0,1).toUpperCase() + genre.substring(1);
            genreIds.add(GENRE_NAME_ID_MAP.get(genre));
        }
        return genreIds;
    }
}
