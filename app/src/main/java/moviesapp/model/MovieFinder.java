package moviesapp.model;

import java.util.List;

public abstract class MovieFinder {

    /**
     * Return a list of movies containing there information from the JSON file selected with parameters (same as searchMovies() from TmdbAPI class).
     * @param title the title of the movie
     * @param releaseYear the releaseYear of the movie
     * @param genres the list of genres of the movie
     * @param voteAverage the vote average of the movie
     * @param page the page result of the search
     * @return return a list of movies, null if no information in parameters
     */
    public Movies findMovies(String title , String releaseYear, List<String> genres, String voteAverage, String page){
        if(title == null || releaseYear == null || genres == null || voteAverage == null || page == null){
            return null;
        }

        boolean[] parametersEmptyStatus = {title.isEmpty(), releaseYear.isEmpty(), genres.isEmpty(), voteAverage.isEmpty(), page.isEmpty()};

        if(allParametersAreEmpty(parametersEmptyStatus)){
            return null;
        }

        Movies movies = new Movies();
        findMoviesByCriteria(movies, title, releaseYear, genres, voteAverage, page);

        return movies;
    }

    /**
     * According to an array of boolean that indicates if a parameter is empty or not, return true if all parameters are.
     * @param parametersEmptyStatus the array of boolean that provides data about parameters status
     * @return true if everything is empty, otherwise false
     */
    private boolean allParametersAreEmpty(boolean[] parametersEmptyStatus){
        for (boolean emptyStatus : parametersEmptyStatus) {
            if (!emptyStatus) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the title provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param title the title of the movie researched
     */
    public void findMoviesByName(Movies movies, String title){
        findMoviesByCriteria(movies, title, null, null, null, null);
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the release releaseYear provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param releaseYear the releaseYear of the movie researched
     */
    public void findMoviesByYear(Movies movies , String releaseYear){
        findMoviesByCriteria(movies, null, releaseYear, null, null, null);
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    public void findMoviesByNameAndYear(Movies movies, String name, String year){
        findMoviesByCriteria(movies, name, year, null, null, null);
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided or not in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param title the name of the movie researched
     * @param releaseYear the year of the movie researched
     * @param genres the genres that the movie checked
     * @param voteAverage the minimum vote average that the movie checked
     * @param page the page of the search result where the movie is
     */
    public abstract void findMoviesByCriteria(Movies movies, String title, String releaseYear, List<String> genres, String voteAverage, String page);
}
