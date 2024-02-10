package moviesapp.model;

public abstract class SearchMovies {

    /**
     * Return a list of movies containing there information from the JSON file selected with a name(optional) and year (optional) provided in parameter.
     * @param name the name of the movie
     * @param year the release year of the movie
     * @return return a list of movies
     */
    public Movies findMovies(String name , String year){
        if(name == null || year == null){
            return null;
        }

        boolean nameEmpty = name.isEmpty();
        boolean yearEmpty = year.isEmpty();

        if(nameEmpty && yearEmpty){
            return null;
        }

        Movies movieList = new Movies();

        if(!nameEmpty && yearEmpty){
            findMoviesByName(movieList , name);
        }
        else if(nameEmpty){
            findMoviesByYear(movieList , year );
        }
        else{
            findMoviesByNameAndYear(movieList , name , year);
        }
        return movieList;
    }
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param name the name of the movie researched
     */
    public abstract void findMoviesByName(Movies movies, String name ) ;
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     */
    public abstract void findMoviesByYear(Movies movies , String year );
    /**
     * Add to a list of movies the movie(s) from the JSON file selected with the name and year provided in parameter.
     * @param movies is a list of movies to which we add the new movie(s) to the list
     * @param year the year of the movie researched
     * @param name the name of the movie researched
     */
    public abstract void findMoviesByNameAndYear(Movies movies, String name, String year) ;
}
