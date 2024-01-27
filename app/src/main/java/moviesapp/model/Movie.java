package moviesapp.model;

public record Movie(boolean adult, String backdropPath, String genreIds, int id, String originalLanguage,
                    String originalTittle, String overview, double popularity, String posterPath, String releaseDate,
                    String title, boolean video, double voteAverage, int voteCount) {
}