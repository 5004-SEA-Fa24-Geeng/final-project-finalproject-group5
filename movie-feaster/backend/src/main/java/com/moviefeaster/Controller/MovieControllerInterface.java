package com.moviefeaster.Controller;

import com.moviefeaster.Model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Interface for the MovieController, responsible for handling
 * user input events from the view and coordinating responses
 * using the model.
 */
public interface MovieControllerInterface {

    /**
     * Handles a search request based on multiple optional filters.
     *
     * @param title     Optional movie title input (can be null or blank)
     * @param director Optional director name input (can be null or blank)
     * @param cast      The casts
     * @param year     Optional year input as a string (can be null or blank)
     * @param genre     Optional type/genre input (can be null or blank)
     */
    @GetMapping("/search")
    List<Movie> handleMultiFilterSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String cast,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre
    );

//    /**
//     * Handles the selection of a specific movie from the result list.
//     *
//     * @param selectedMovie The movie selected by the user; must not be null
//     */
//    void handleMovieSelected(Movie selectedMovie);

//    /**
//     * Handles the "Back" button event, returning the user to the movie selection view.
//     */
//    void handleBackPressed();

    /**
     * Updates the comment for a given movie.
     *
     * @param movieID The ID of the movie to comment on.
     * @param comment The user-provided comment.
     * @return
     */
    String handleCommentSubmission(int movieID, String comment);

    /**
     * Updates the rating for a given movie.
     *
     * @param movieID The ID of the movie to rate.
     * @param rating  The user-provided rating.
     */
    void handleRatingSubmission(int movieID, double rating);
}