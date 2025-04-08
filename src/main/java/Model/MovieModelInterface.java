package Model;

import java.util.List;

/**
 * Interface for MovieModel. Responsible for handling data fetching from api sources,
 * writing and reading movies data from local database, filtering and sorting movies data,
 * and output format.
 */
public interface MovieModelInterface {

    /**
     * Fetching movie data from api.
     */
    void fetchMovies();

    /**
     * Write the movie data stored in the model to the file.
     */
    void writeFile(Format format);

    /**
     * Filter the movie that conform with user's input
     * @param title movie title
     * @param director directors of the movie
     * @param year movie's publish year
     * @param genere movie's genere
     * @return List of move that filtered by user's input.
     */
    List<Movie> searchByMultipleFilters(String title, String director, int year, Genere genere);

    /**
     * Update comments with user-given comment.
     *
     * @param movieID ID of movie that the comments is given.
     * @param comment comments.
     */
    void UpdateComments(String movieID, String comment);

    /**
     * Update in-App rating with given rating.
     * @param movieID ID of movie that the rating is given.
     * @param rating rating from user.
     */
    void UpdateRating(String movieID, Float rating);

    /**
     * Gets the singleton instance of the model.
     * Delegates to the implementation class {@link MovieModel}.
     *
     * @return the singleton instance of MovieModelInterface
     */
    static MovieModel getInstance() {
        return MovieModel.getInstance();
    }

}
