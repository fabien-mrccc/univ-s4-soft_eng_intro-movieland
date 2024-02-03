package moviesapp.model;

import java.util.List;

public record Movie(boolean adult, String backdropPath, List<Integer> genreIds, int id, String originalLanguage,
                    String originalTitle, String overview, double popularity, String posterPath, String releaseDate,
                    String title, boolean video, double voteAverage, int voteCount) {
    @Override
    public String toString(){
        return "__________________________  \n"+"  title: "+title+"\n  popularity: " +popularity+"\n  release year: "
                +releaseDate.substring(0,4)+"\n__________________________  ";
    }

    /**
     * return the details of the movie
     * @return the details of the movie
     */
    public String details(){
        return "__________________________  \n"+"movie n°"+ id +":\n  title: "+title+"\n  original title: "+originalTitle+
                "\n  release date: "+releaseDate+ "\n  original language: "+originalLanguage+
                "\n  genreIds: "+genreIds+"\n  popularity: " +popularity+"\n  adult: "+adult+
                "\n  vote average: "+voteAverage+"\n  vote count: " +voteCount+"\n  overview: "
                +overview+ "\n__________________________  ";
    }
}