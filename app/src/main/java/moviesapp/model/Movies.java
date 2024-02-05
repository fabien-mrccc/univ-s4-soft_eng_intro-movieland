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
}
