package moviesapp.model;

public record Movie(boolean adult, String backdropPath, int genreIds, int id, String originalLanguage,
                    String originalTittle, String Overview, String popularity, String posterPath, String releaseDate,
                    String title, boolean video, int voteAverage, int voteCount) {
}