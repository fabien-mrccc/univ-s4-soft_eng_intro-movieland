package moviesapp.model.api;

import moviesapp.model.exceptions.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

import static moviesapp.model.api.RequestBuilder.*;
import static moviesapp.model.json.JsonReader.*;
import static moviesapp.model.json.JsonWriter.convertJsonToFile;

public class TheMovieDbAPI {

    static final OkHttpClient client = new OkHttpClient();

    public static void launchSearch(SearchCriteria criteria) throws SelectModeException {

        if (criteria.noInformationSent()){
            throw new SelectModeException();
        }
        else if (criteria.title.isEmpty() && !criteria.noDiscoverCriteria()) {
            searchMovies(criteria, 2);
        }
        else if (!criteria.title.isEmpty() && criteria.noDiscoverCriteria()) {
            searchMovies(criteria, 1);
        }
        else {
            //TODO: code merge search

        }
    }

    /**
     * Searches for movies based on the provided parameters.
     *
     * @param searchMode The search mode (1 -> search, 2 -> discover, 3-> movie/popular, 4 -> genre)
     */
    public static void searchMovies(SearchCriteria criteria, int searchMode) {

        RequestBuilder requestBuilder = new RequestBuilder(criteria);

        try {
            searchMovies(requestBuilder.build(searchMode));
        }
        catch (IndexException e) {
            System.err.println(e.getMessage() + e.specifySearchModeError());
        }
    }

    /**
     * Searches for movies using the provided request URL.
     *
     * @param requestUrl The request URL for searching movies.
     */
    public static void searchMovies(String requestUrl) {

        searchMovies(new RequestBuilder().build(requestUrl));
    }

    /**
     * Searches for movies using the provided request.
     *
     * @param request The request for searching movies.
     */
    private static void searchMovies(Request request) {

        try {
            Response response = client.newCall(request).execute();
            reactToRequestResponse(response);
        }
        catch(IOException e){
            System.err.println("IOException e from \"Response response = client.newCall(request).execute();\" ");
        }
    }

    /**
     * Reacts to the response of a request.
     * If the response is successful and contains a body, it saves the body content to a file.
     * If the response is not successful, it prints an error message with the response code.
     *
     * @param response The response of the request.
     */
    private static void reactToRequestResponse(Response response) {

        String filePath = SEARCH_FILE_PATH;

        if (criteriaToUrl.get("searchMode").equals("/genre/movie/list?")) {
            filePath = GENRES_FILE_PATH;
        }

        try{
            if(response.isSuccessful() && response.body() != null){
                convertJsonToFile(response.body().string(), filePath);
                SEARCH_READER = updateSearchReader();
            }
            else{
                System.err.println("Error API request: " + response.code());
            }
        } catch (IOException e){
            System.err.println("IOException e from \"String searchResult = response.body().string();\"");
        }
    }

    /**
     * Retrieves the first page of popular movies in json.
     */
    public static void popularMoviesFirstPage() {
        searchMovies(null, 3);
    }

    /**
     * Retrieves the previous page of popular movies in json.
     *
     * @throws NoPreviousPageException If there is no previous page available.
     */
    public static void switchToPreviousPage() throws NoPreviousPageException {

        try {
            int currentPage = getCurrentPage() ;

            if(currentPage > 1) {
                switchPage(String.valueOf(currentPage - 1));
            }
            else {
                throw new NoPreviousPageException();
            }
        }
        catch (NotAPositiveIntegerException e) {
            throw new NoPreviousPageException();
        }
    }

    /**
     * Retrieves the next page of popular movies in json.
     *
     * @throws NoNextPageException If there is no next page available.
     */
    public static void switchToNextPage() throws NoNextPageException {

        try {
            int currentPage = getCurrentPage() ;

            if(currentPage < SEARCH_READER.numberOfPagesOfMoviesInJson()) {
                switchPage(String.valueOf(currentPage + 1));
            }
            else {
                throw new NoNextPageException();
            }
        }
        catch (NotAPositiveIntegerException e) {
            throw new NoNextPageException();
        }
    }

    public static void switchToSpecificPage(String page) throws NotAPositiveIntegerException, IntervalException {

        int pageNumber = convertAsPositiveInt(page);

        if (pageNumber >= 1 && pageNumber <= SEARCH_READER.numberOfPagesOfMoviesInJson()) {
            switchPage(page);
        }
        else {
            throw new IntervalException();
        }
    }

    private static void switchPage(String pageNumber) {
        searchMovies(updateRequestUrlPage(pageNumber));
    }
}
