package moviesapp.model.api;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static moviesapp.model.api.TheMovieDbAPI.client;
import static moviesapp.model.api.RequestBuilder.apiKey;
import static moviesapp.model.api.RequestBuilder.baseUrl;
import static moviesapp.model.json.JsonReader.GENRES_FILE_PATH;
import static moviesapp.model.json.JsonReader.GENRES_READER;
import static moviesapp.model.json.JsonWriter.convertJsonToFile;

public class Genres {

    public static final Genres instance = new Genres();

    public static final TreeMap<String, String> GENRE_NAME_ID_MAP = new TreeMap<>();

    /**
     * Update the genres.json then fill the static GENRE_ID_MAP with the genres located in genres.json
     */
    public static void fillGENRE_NAME_ID_MAP(){
        updateGenresFile();
        for(JsonNode genre : GENRES_READER.getJsonGenres()){
            GENRE_NAME_ID_MAP.put(genre.get("name").asText(),genre.get("id").asText());
        }
    }

    /**
     * Update the genres.json with all genres from Tmdb
     */
    private static void updateGenresFile(){
        String url = baseUrl + "language=en" + apiKey;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                assert response.body() != null;
                String genresResult = response.body().string();
                convertJsonToFile(genresResult, GENRES_FILE_PATH);
            }
            else{
                System.out.println("error :" + response.code());
            }
        }catch(IOException e){
            System.err.println("IOException e from 'Response response = client.newCall(request).execute();' ");
        }
    }

    /**
     * Return a list of every registered genres
     * @return list of every genre
     */
    public static StringBuilder getGenres(){
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

    /**
     * return a list of genres in String format
     * @return a list of genres
     */
    public List<String> genreList(){
        return new ArrayList<>(GENRE_NAME_ID_MAP.keySet());
    }
}
