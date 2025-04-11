package Model;

import Model.Net.MovieParser;

import java.util.List;

public class MovieModel implements MovieModelInterface{
    /** Singleton instance */
    private static MovieModel instance;
    /** Movie's list */
    private List<Movie> movies;

    /**
     * Private constructor to prevent direct instantiation
     */
    private MovieModel() {
    }

    @Override
    public void fetchMovies() {
        this.movies = MovieParser.getMoviesFromApi();
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    @Override
    public void writeFile(Format format) {
        return;
    }

    @Override
    public List<Movie> searchByMultipleFilters(String title, String director, int year, Genre genre) {
        return null;
    }

    @Override
    public void UpdateComments(int movieID, String comment) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieID) {
                movie.addComment(comment);
                return;
            }
        }
    }

    @Override
    public void UpdateRating(int movieID, double rating) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieID) {
                movie.addInAppRating(rating);
                return;
            }
        }
    }

    /**
     * Gets the singleton instance of the MovieModel.
     * It prevents model being instantiated more than once.
     * @return MovieModel
     */
    public static MovieModel getInstance() {
        if (instance == null) {
            instance = new MovieModel();
            instance.fetchMovies();
        }
        return instance;
    }
}
