package Model;

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

    }

    @Override
    public void writeFile(Format format) {

    }

    @Override
    public List<Movie> searchByMultipleFilters(String title, String director, int year, Genre genre) {
        return List.of();
    }

    @Override
    public void UpdateComments(String movieID, String comment) {

    }

    @Override
    public void UpdateRating(String movieID, Float rating) {

    }

    /**
     * Gets the singleton instance of the MovieModel.
     * It prevents model being instantiated more than once.
     * @return MovieModel
     */
    public static MovieModel getInstance() {
        if (instance == null) {
            instance = new MovieModel();
        }
        return instance;
    }
}
