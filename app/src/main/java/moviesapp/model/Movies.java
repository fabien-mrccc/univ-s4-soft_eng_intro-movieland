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
}
