package moviesapp.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TmdbAPI {

    String fileName = System.getProperty("user.dir") + "/src/main/java/moviesapp/model/api-results.json";
    private final static String genreFileName = System.getProperty("user.dir") + "/src/main/java/moviesapp/model/genres.json";
    OkHttpClient client = new OkHttpClient();
    static OkHttpClient clientGenres = new OkHttpClient();

    private final static String baseUrl = "https://api.themoviedb.org/3";
    private final static String apiKey = "&api_key=5e40bf6f22600832c99dbb5d52115269";
    private final static String language = "language=en-US";
    public static final TreeMap<String, String> GENRE_ID_MAP = new TreeMap<>();

    /**
     * Return a list of every registered genres
     * @return list of every genre
     */
    public StringBuilder genreList(){
        StringBuilder list = new StringBuilder();
        for (String genre : GENRE_ID_MAP.keySet()) {
            list.append("  •").append(genre).append("\n");
        }
        return list;
    }

    /**
     * Call every necessary methods to create the appropriate url from the given parameters
     * @param title part of or complete title of a movie
     * @param releaseYear year of release of movie
     * @param genres a list of genres
     * @param voteAverage the min vote average
     */
    public void searchMovie(String title, String releaseYear, List<String> genres, String voteAverage){
        String url;
        if(title.isEmpty()){
            url = urlBuilderDiscover(releaseYear, genresToGenreIds(genres), voteAverage);
        }
        else{
            url = urlBuilderMovie(title, releaseYear);
        }
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
        try {
            ObjectMapper mapper = JsonMapper.builder().build();
            ObjectNode node = mapper.readValue(result, ObjectNode.class);
            try (FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8)) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileWriter, node);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * return an url from the given parameters
     * @param year year of release of a film
     * @param genreIds list of genres of a film
     * @param voteAverage minimum vote average ofa film
     * @return the desired url based on given parameters
     */
    private String urlBuilderDiscover(String year, List<String> genreIds, String voteAverage){
        boolean noYear = year.isEmpty();
        boolean noGenre = genreIds.isEmpty();
        boolean noVoteAverage = voteAverage.isEmpty();
        StringBuilder currentUrl = new StringBuilder(baseUrl + "/discover/movie?" + language);
        if(!noYear){
            currentUrl.append("&primary_release_year=").append(year);
        }
        if(!noVoteAverage){
            currentUrl.append("&vote_average.gte=").append(voteAverage);
        }
        if(!noGenre){
            currentUrl.append("&with_genres=");
            for (int i = 0; i < genreIds.size(); i++) {
                currentUrl.append(genreIds.get(i));
                if (i < genreIds.size() - 1) {
                    currentUrl.append(",");
                }
            }
        }
        return currentUrl + apiKey;
    }

    /**
     * return an url from given parameters
     * @param title title or part of a title of a movie
     * @param year year of release of a movie
     * @return the desired url from given url
     */
    private String urlBuilderMovie(String title, String year){
        boolean noYear = year.isEmpty();
        if(!noYear){
            return baseUrl + "/search/movie?query=" + title + "&primary_release_year=" + year + language + apiKey;
        }
        return baseUrl + "/search/movie?" + language + "&query=" + title + apiKey;
    }

    /**
     * return a list of genre ids from a list of genres
     * @param genreList a list of genres
     * @return a list of ids
     */
    private List<String> genresToGenreIds(List<String> genreList){
        List<String> genreIds = new ArrayList<>();
        for(String genre : genreList){
            genreIds.add(GENRE_ID_MAP.get(genre.toLowerCase(Locale.ROOT).trim()));
        }
        return genreIds;
    }

    /** Update the genres.json then fill the static GENRE_ID_MAP with the genres located in genres.json
     * Update the genres.json then fill the static GENRE_ID_MAP with the genres located in genres.json
     * @return the filled GENRE_ID_MAP
     */
    public static void fillGENRE_ID_MAP(){
        updateGenresFile();
        JSONReader jsonGenresReader = new JSONReader(genreFileName);
        for(JsonNode genre : jsonGenresReader.getJsonGenres()){
            GENRE_ID_MAP.put(genre.get("name").asText(),genre.get("id").asText());
        }
    }

    /**
     * Update the genres.json with all genres from Tmdb
     */
    private static void updateGenresFile(){
        String url = baseUrl + "/genre/movie/list?language=en" + apiKey;

        Request request = new Request.Builder().url(url).build();

        try {
            Response response = clientGenres.newCall(request).execute();

            if(response.isSuccessful()){
                String genresResult = response.body().string();
                requestToGenresFile(genresResult);
            }
            else{
                System.out.println("error :" + response.code());
            }
        }catch(IOException e){
            e.printStackTrace();
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
            try (FileWriter fileWriter = new FileWriter(genreFileName, StandardCharsets.UTF_8)) {
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(fileWriter, node);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
