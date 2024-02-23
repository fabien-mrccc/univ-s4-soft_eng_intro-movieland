package moviesapp.model.api;

import okhttp3.Request;

import java.util.List;

public class UrlRequestBuilder {
    final static String baseUrl = "https://api.themoviedb.org/3";
    final static String apiKey = "&api_key=5e40bf6f22600832c99dbb5d52115269";
    final static String language = "language=en-US";
    private final String title;
    private final String singleYearOrMinYear;
    private final String maxYear;
    private final List<String> genres;
    private final String voteAverage;
    private final String page;

    public UrlRequestBuilder(String title, String singleYearOrMinYear, String maxYear, List<String> genres, String voteAverage, String page){
        this.title = title;
        this.singleYearOrMinYear = singleYearOrMinYear;
        this.maxYear = maxYear;
        this.genres = genres;
        this.voteAverage = voteAverage;
        this.page = page;
    }

    /**
     * According to title value (null or not), choose to build API request from url with API search command or API discover command
     * @return the request built
     */
    Request build(){
        String urlString;

        if(!title.isEmpty() && maxYear.isEmpty()){
            urlString = searchBuilder(title, singleYearOrMinYear, page);
        }
        else{
            urlString = discoverBuilder(singleYearOrMinYear, maxYear, genres, voteAverage , page);

        }
        return new Request.Builder().url(urlString).build();
    }

    /**
     * Return an url using API search command from the given parameters
     * @param title title or part of a title of a movie
     * @param releaseYear release year of a movie
     * @return the desired url based on given parameters
     */
    private String searchBuilder(String title, String releaseYear , String page){
        StringBuilder urlBuilder = new StringBuilder(baseUrl + "/search/movie?" + language + "&query=" + title);
        buildUrlWithYears(urlBuilder, releaseYear, null);
        buildUrlWithPage(urlBuilder, page);
        return urlBuilder + apiKey;
    }

    /**
     * Return an url using API discover command from the given parameters
     * @param singleYearOrMinYear min release year of a film
     * @param maxYear max release year of a film
     * @param genreIds list of genres of a film
     * @param voteAverage minimum vote average of a film
     * @return the desired url based on given parameters
     */
    private String discoverBuilder(String singleYearOrMinYear, String maxYear, List<String> genreIds, String voteAverage, String page){
        StringBuilder urlBuilder = new StringBuilder(baseUrl + "/discover/movie?" + language);

        buildUrlWithYears(urlBuilder, singleYearOrMinYear, maxYear);
        buildUrlWithGenres(urlBuilder, genreIds, genreIds == null || genreIds.isEmpty());
        buildUrlWithVoteAverage(urlBuilder, voteAverage, voteAverage == null || voteAverage.isEmpty());
        buildUrlWithPage(urlBuilder, page);
        return urlBuilder + apiKey;
    }

    /**
     * Append to urlBuilder string corresponding to the given year span argument if it is not empty
     * @param urlBuilder StringBuilder to modify
     * @param singleYearOrMinYear of the movies to search with API with discover command
     * @param maxYear of the movies to search with API with discover command
     */
    private void buildUrlWithYears(StringBuilder urlBuilder, String singleYearOrMinYear, String maxYear){
        if(maxYear == null){
            maxYear = "single_mode";
        }
        boolean isSingleMode = maxYear.equals("single_mode");
        boolean isMinYearEmpty = singleYearOrMinYear == null || singleYearOrMinYear.isEmpty();
        boolean isMaxYearEmpty = maxYear.isEmpty();

        if(!isMinYearEmpty && isSingleMode){
            urlBuilder.append("&primary_release_year=").append(singleYearOrMinYear);
        }
        else if (!isMinYearEmpty && !isMaxYearEmpty){
            urlBuilder.append("&primary_release_date.gte=").append(singleYearOrMinYear).append("-01-01");
            urlBuilder.append("&primary_release_date.lte=").append(maxYear).append("-12-31");
        }
        else if (!isMinYearEmpty){
            urlBuilder.append("&primary_release_date.gte=").append(singleYearOrMinYear).append("-01-01");
        }
        else if (!isMaxYearEmpty && !isSingleMode){
            urlBuilder.append("&primary_release_date.lte=").append(maxYear).append("-12-31");
        }
    }

    /**
     * Append to UrlBuilder string corresponding to page
     * @param urlBuilder StringBuilder to modify
     * @param page the number of the page
     */
    private void buildUrlWithPage(StringBuilder urlBuilder, String page ) {
        urlBuilder.append("&page=").append(page);
    }

    /**
     * Append to urlBuilder string corresponding to genreIds argument if it is not empty
     * @param urlBuilder StringBuilder to modify
     * @param genreIds of the movies to search with API with discover command
     * @param isGenreEmpty flag to append to urlBuilder
     */
    private void buildUrlWithGenres(StringBuilder urlBuilder, List<String> genreIds, boolean isGenreEmpty){
        if(!isGenreEmpty){
            urlBuilder.append("&with_genres=");
            for(String genre : genreIds){
                urlBuilder.append(genre).append(",");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
    }

    /**
     * Append to urlBuilder string corresponding to voteAverage argument if it is not empty
     * @param urlBuilder StringBuilder to modify
     * @param voteAverage of the movies to search with API with discover command
     * @param isVoteAverageEmpty flag to append to urlBuilder
     */
    private void buildUrlWithVoteAverage(StringBuilder urlBuilder, String voteAverage, boolean isVoteAverageEmpty){
        if(!isVoteAverageEmpty){
            urlBuilder.append("&vote_average.gte=").append(voteAverage);
        }
    }

    String popularMoviesBuilder(int page){
        return baseUrl + "/movie/popular?" + language + "&page=" + page + apiKey;
    }
}
