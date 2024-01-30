package moviesapp.model;

import java.util.List;

public record Movie(boolean adult, String backdropPath, List<Integer> genreIds, int id, String originalLanguage,
                    String originalTitle, String overview, double popularity, String posterPath, String releaseDate,
                    String title, boolean video, double voteAverage, int voteCount) {
}