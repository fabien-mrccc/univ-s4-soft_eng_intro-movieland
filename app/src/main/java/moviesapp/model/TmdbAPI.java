package moviesapp.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TmdbAPI {

    String fileName = System.getProperty("user.dir") + "/src/main/java/moviesapp/model/api-results.json";
    OkHttpClient client = new OkHttpClient();
    private final static String baseUrl = "https://api.themoviedb.org/3";
    private final static String apiKey = "&api_key=5e40bf6f22600832c99dbb5d52115269";
    private final static String language = "language=en-US";
    public static final HashMap<String, String> GENRE_ID_MAP;

    //liste of literally all genres available on TMDB
    static {
        GENRE_ID_MAP = new HashMap<>();
        GENRE_ID_MAP.put("action", "28");
        GENRE_ID_MAP.put("adventure", "12");
        GENRE_ID_MAP.put("animation", "16");
        GENRE_ID_MAP.put("comedy", "35");
        GENRE_ID_MAP.put("crime", "80");
        GENRE_ID_MAP.put("documentary", "99");
        GENRE_ID_MAP.put("drama", "18");
        GENRE_ID_MAP.put("family", "10751");
        GENRE_ID_MAP.put("fantasy", "14");
        GENRE_ID_MAP.put("history", "36");
        GENRE_ID_MAP.put("horror", "27");
        GENRE_ID_MAP.put("music", "10402");
        GENRE_ID_MAP.put("romance", "9648");
        GENRE_ID_MAP.put("mystery", "10749");
        GENRE_ID_MAP.put("science fiction", "878");
        GENRE_ID_MAP.put("tv movie", "10770");
        GENRE_ID_MAP.put("thriller", "53");
        GENRE_ID_MAP.put("war", "10752");
        GENRE_ID_MAP.put("western", "37");
    }

    /**
     * return a list of every registered genres
     * @return list of every genre
     */
    public StringBuilder genreList(){
        StringBuilder list = new StringBuilder();
        for (String genre : GENRE_ID_MAP.keySet()) {
            list.append("  ").append(genre).append("\n");
        }
        return list;
    }

    /**
     * call every necessary methods to create the apropriate url from the given parameters
     * @param title part of or complete title of a movie
     * @param year year of release of movie
     * @param genre a list of genre
     * @param voteAverage the min vote average
     */
    public void searchMovie(String title, String year, List<String> genre, String voteAverage){
        String url;
        if(title.isEmpty()){
            url = urlBuilderDiscover(year, genresToGenreIds(genre), voteAverage);
        }
        else{
            url = urlBuilderMovie(title, year);
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
        try(FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8)){
            fileWriter.write(result);
        } catch (IOException e){
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
}
