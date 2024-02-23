package moviesapp.model.movies;

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
            return "Your list of movies is empty.";
        }
        for(int i = 0; i < movies.size(); i++){
            moviesString.append("| n°");
            if (i < 10){
                moviesString.append(0).append(i + 1);
            }
            else{
                moviesString.append(i + 1);
            }
            moviesString.append(movies.get(i));
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
     * Check if a list is empty or null and deduct that no movie where found
     * @return true if the list is without movies inside, otherwise false
     */
    public static boolean noMovieFound(Movies movies){
        return movies == null || movies.isEmpty();
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
     * @param index of movie to find
     * @return the movie found with selection or null
     */
    public Movie findMovieByIndex(int index) {
        if (movies == null || index < 0 || index >= size()) {
            return null;
        }
        return get(index);
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

    /**
     * Converts the list of movies to JSON format by returning the string corresponding.
     * @return a JSON formatted string representing the list of movies
     */
    public String toJsonFormat() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("  \"results\" : [\n");

        for (Movie movie : movies) {
            jsonBuilder.append(movie.toJsonFormat());
            if (movies.indexOf(movie) < movies.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("  ]");
        return jsonBuilder.toString();
    }

    /**
     * Remove all the movies registered in movies list.
     */
    public void clear(){
        movies.clear();
    }

    /**
     * Indicate if movies contains a specific movie
     * @param movie the movie to check in
     * @return {@code true} if the movie is in movies list, otherwise false
     */
    public boolean contains(Movie movie){
        return movies.contains(movie);
    }

    /**
     * Add a group of movies to our list of movies
     * @param movies to add to our list
     */
    public void addAll(List<Movie> movies){
        this.movies.addAll(movies);
    }

    /**
     * Remove a group of movies to our list of movies
     * @param movies to add to our list
     */
    public void removeAll(List<Movie> movies){
        this.movies.removeAll(movies);
    }
}