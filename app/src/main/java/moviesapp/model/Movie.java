package moviesapp.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Movie(boolean adult, String backdropPath, List<String> genres, String id, String originalLanguage,
                    String originalTitle, String overview, double popularity, String posterPath, String releaseDate,
                    String title, boolean video, double voteAverage, int voteCount) {
    @Override
    public String toString(){
        String print =
                " |__________________" +
                        "\n  title: " +
                        title +
                        "\n  vote average: " +
                        voteAverage;

        if(releaseDate != null && releaseDate.length() >= 4){
            print += "\n  release year: " + releaseDate.substring(0,4);
        }
        else{
            print += "\n  release year: empty";
        }
        print += "\n__________________________\n";

        return print;
    }

    /**
     * Return the details of the movie
     * @return the details of the movie
     */
    public String details(){
        return "__________________________  \n"+"movie n°"+ id +":\n  title: "+title+"\n  original title: "+originalTitle+
                "\n  release date: "+releaseDate+ "\n  original language: "+originalLanguage+
                "\n  genreIds: "+genres+"\n  popularity: " +popularity+"\n  adult: "+adult+
                "\n  vote average: "+voteAverage+"\n  vote count: " +voteCount+"\n  overview: "
                +overview+ "\n__________________________  ";
    }

    /**
     * Return a string that represents json code corresponding to the movie.
     * @return a string that represents json code corresponding to the movie.
     */
    public String toJsonFormat() {
        return "    {\n" +
                "      \"adult\" : " + adult() + ",\n" +
                "      \"backdrop_path\" : \"" + backdropPath() + "\",\n" +
                "      \"genre_ids\" : " + genres() + ",\n" +
                "      \"id\" : " + id() + ",\n" +
                "      \"original_language\" : \"" + originalLanguage() + "\",\n" +
                "      \"original_title\" : \"" + originalTitle() + "\",\n" +
                "      \"overview\" : \"" + overview() + "\",\n" +
                "      \"popularity\" : " + popularity() + ",\n" +
                "      \"poster_path\" : \"" + posterPath() + "\",\n" +
                "      \"release_date\" : \"" + releaseDate() + "\",\n" +
                "      \"title\" : \"" + title() + "\",\n" +
                "      \"video\" : " + video() + ",\n" +
                "      \"vote_average\" : " + voteAverage() + ",\n" +
                "      \"vote_count\" : " + voteCount() + "\n" +
                "    }";
    }

    /**
     * Determines whether two movies have the same ID.
     * @param movie1 The first movie to compare.
     * @param movie2 The second movie to compare.
     * @return True if the movies have the same ID, false otherwise.
     */
    public static boolean sameMovies(Movie movie1, Movie movie2) {
        return movie1.id().equals(movie2.id());
    }
}