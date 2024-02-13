package moviesapp.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Movies implements Iterable<Movie> {
    private final List<Movie> movies;

    public Movies(List<Movie> movies){
        this.movies = movies;
    }

    public Movies(){
        this.movies = new ArrayList<>();
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
     * Return the same default toString() but add id value
     * @return the same default toString() but add id value
     */
    public String toStringWithID(){
        StringBuilder moviesString = new StringBuilder();
        if(movies.isEmpty()){
            moviesString = new StringBuilder("Your list of movies is empty.");
            return moviesString.toString();
        }
        for(Movie movie: movies){
            moviesString.append(movie.toStringWithID()).append("\n");
        }
        return moviesString.toString();
    }

    /**
     * Add a movie to the list
     * @param movie the movie that we want to add
     */
    public void add(Movie movie){
        if(movie != null) {
            movies.add(movie);
        }
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
        if(noMovieFound(this)){
            return;
        }
        System.out.println("\nYour list of movies with detailed information: ");
        for(Movie movie : movies){
            System.out.println(movie.details());
        }
    }

    /**
     * Check if a list is empty or null and deduct that no movie where found
     * @return true if the list is without movies inside, otherwise false
     */
    public static boolean noMovieFound(Movies movies){
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

    @NotNull
    @Override
    public Iterator<Movie> iterator() {
        return new MoviesIterator();
    }

    private class MoviesIterator implements Iterator<Movie> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Movie next() {
            if (hasNext()) {
                return movies.get(index++);
            } else {
                throw new IllegalStateException("No more elements in the iterator.");
            }
        }
    }

    /**
     * Return of a movie selected with a list of movies and an id provided in parameter.
     * @param id of movie to find
     * @return the movie found with selection or null
     */
    public Movie findMovieByID(String id){
        for(Movie movie : movies){
            if(movie.id().equals(id)){
                return movie;
            }
        }
        return null ;
    }

    /**
     * Remove a movie from the movies list
     * @param movie the movie that we want to remove in our list
     */
    public void remove(Movie movie){
        movies.remove(movie);
    }

    /**
     * Return a movie according to an index provided in parameter
     * @param index to find our movie in movies list
     * @return the Movie found with the index
     */
    public Movie get(int index){
        return movies.get(index);
    }
}
