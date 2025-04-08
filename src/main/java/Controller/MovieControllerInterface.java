package Controller;

import Model.Movie;

/**
 * Interface for the MovieController, responsible for handling
 * user input events from the view and coordinating responses
 * using the model.
 */
public interface MovieControllerInterface {

    /**
     * Handles a search request based on multiple optional filters.
     *
     * @param name     Optional movie title input (can be null or blank)
     * @param director Optional director name input (can be null or blank)
     * @param year     Optional year input as a string (can be null or blank)
     * @param type     Optional type/genre input (can be null or blank)
     */
    void handleMultiFilterSearch(String name, String director, String year, String type);

    /**
     * Handles the selection of a specific movie from the result list.
     *
     * @param selectedMovie The movie selected by the user; must not be null
     */
    void handleMovieSelected(Movie selectedMovie);

    /**
     * Handles the "Back" button event, returning the user to the movie selection view.
     */
    void handleBackPressed();
}
