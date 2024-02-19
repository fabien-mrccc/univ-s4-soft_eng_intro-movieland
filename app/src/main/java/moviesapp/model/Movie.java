package moviesapp.model;

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
     * return the details of the movie
     * @return the details of the movie
     */
    public String details(){
        return "__________________________  \n"+"movie n°"+ id +":\n  title: "+title+"\n  original title: "+originalTitle+
                "\n  release date: "+releaseDate+ "\n  original language: "+originalLanguage+
                "\n  genreIds: "+genres+"\n  popularity: " +popularity+"\n  adult: "+adult+
                "\n  vote average: "+voteAverage+"\n  vote count: " +voteCount+"\n  overview: "
                +overview+ "\n__________________________  ";
    }

    public String toStringWithID(){
        return "__________________________  \n"+"  title: "+title+"\n  vote average: " +voteAverage+"\n  release year: "
                +releaseDate.substring(0,4)+"\n  id: "+id+"\n__________________________  ";
    }
}