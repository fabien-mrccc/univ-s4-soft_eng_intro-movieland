package moviesapp.model;

public abstract class SearchMovies {

    /**
     * Return a list of movies containing there information from the JSON file selected with a title(optional) and release year (optional) provided in parameter.
     * @param title the title of the movie
     * @param releaseYear the releaseYear of the movie
     * @return return a list of movies
     */
    public Movies findMovies(String title , String releaseYear){
        if(title == null || releaseYear == null){
            return null;
        }

        boolean nameEmpty = title.isEmpty();
        boolean yearEmpty = releaseYear.isEmpty();

        if(nameEmpty && yearEmpty){
            return null;
        }

        Movies movieList = new Movies();

        if(!nameEmpty && yearEmpty){
            findMoviesByName(movieList , title);
        }
        else if(nameEmpty){
            findMoviesByYear(movieList , releaseYear );
        }
        else{
            findMoviesByNameAndYear(movieList , title , releaseYear);
        }
        return movieList;
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the title provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param title the title of the movie researched
     */
    public void findMoviesByName(Movies movies, String title ){
        findMoviesByCriteria(movies, title, null);
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the release releaseYear provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param releaseYear the releaseYear of the movie researched
     */
    public void findMoviesByYear(Movies movies , String releaseYear){
        findMoviesByCriteria(movies, null, releaseYear);
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    public void findMoviesByNameAndYear(Movies movies, String name, String year){
        findMoviesByCriteria(movies, name, year);
    }

    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided or not in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    public abstract void findMoviesByCriteria(Movies movies, String name, String year);
}
