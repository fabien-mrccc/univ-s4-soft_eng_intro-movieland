package moviesapp.model;

import java.util.List;

public class Movies {
    private final List<Movie> movies;

    public Movies(List<Movie> movies){
        this.movies = movies;
    }

    @Override
    public String toString(){
        StringBuilder moviesString = new StringBuilder();
        if(movies.isEmpty()){
            moviesString = new StringBuilder("Your list of movies is empty.");
            return moviesString.toString();
        }
        for(Movie movie: movies){
            moviesString.append(movie).append("\n");
        }
        return moviesString.toString();
    }

    /**
     * Add a movie to the list
     * @param movie the movie that we want to add
     */
    public void add(Movie movie){
        movies.add(movie);
    }

    /**
     * Return true if the list of movies is empty, otherwise false
     * @return true if the list of movies is empty, otherwise false
     */
    public boolean isEmpty(){
        return movies.isEmpty();
    }

    /**
     * Print all movies information details according to a specific group
     */
    public void printMoviesDetails(){
        if(noMovieFound(movies)){
            return;
        }
        for(Movie movie : movies){
            System.out.println(movie);
        }
    }

    /**
     * Check if a list is empty or null and deduct that no movie where found
     * @param movies the list of movies to check
     * @return true if the list is without movies inside, otherwise false
     */
    private boolean noMovieFound(List<Movie> movies){
        if(movies == null || movies.isEmpty()){
            System.out.println("No movie found.");
            return true;
        }
        return false;
    }

    /**
     * Return the size of the movies list
     * @return the size of the movies list
     */
    public int size(){
        return movies.size();
    }
}
