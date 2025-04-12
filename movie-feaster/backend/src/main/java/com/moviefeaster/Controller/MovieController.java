package com.moviefeaster.Controller;

import com.moviefeaster.Model.*;
import com.moviefeaster.Service.MovieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MovieController handles all user interactions coming from the view.
 * It coordinates input validation via InputProcessor and delegates
 * data operations to the MovieModel. It also controls the flow between
 * search results and detailed views.
 */
@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000") // Allow cross-origin requests from frontend
public class MovieController implements MovieControllerInterface {

    private final MovieModel model;
    private final InputProcessor inputProcessor;

    /**
     * Constructs a new MovieController with the given model and view.
     */
    @Autowired
    public MovieController(MovieModel model, InputProcessor inputProcessor) {
        this.model = model;
        this.inputProcessor = inputProcessor;
    }

    /**
     * Handles a multi-filter search request. Each field is optional.
     * Input is parsed and validated using InputProcessor.
     *
     * @param title    The raw name input from the view.
     * @param director The raw director input from the view.
     * @param cast     The casts
     * @param year     The raw year input from the view.
     * @param genre    The raw type input from the view.
     */
    @Override
    @GetMapping("/search")
    public List<Movie> handleMultiFilterSearch(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String cast,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre
    ) {
        try {
            String parsedName = inputProcessor.optionalParseName(title);
            String parsedDirector = inputProcessor.optionalParseDirector(director);
            String parsedCast = inputProcessor.optionalParseDirector(cast);
            Integer parsedYear = inputProcessor.optionalParseYear(year);
            Genre parsedGenre = inputProcessor.optionalParseGenre(genre);

            return model.searchByMultipleFilters(parsedName, parsedDirector, parsedCast, parsedYear, parsedGenre);
        } catch (Exception e) {
            throw new IllegalArgumentException("Search failed: " + e.getMessage());
        }
    }

    /**
     * Handles user-submitted comment for a selected movie.
     *
     * @param movieId The ID of the movie to comment on.
     * @param comment The comment text.
     * @return
     */
    @PostMapping("/{movieId}/comment")
    @Override
    public String handleCommentSubmission(@PathVariable int movieId, @RequestBody String comment) {
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Invalid comment.");
        }
        model.UpdateComments(movieId, comment);
        return comment;
    }

    /**
     * Handles user-submitted rating for a selected movie.
     *
     * @param movieId The ID of the movie to rate.
     * @param rating  The rating value.
     */
    @PostMapping("/{movieId}/rating")
    @Override
    public void handleRatingSubmission(int movieId, double rating) {
        try {
            if (rating < 0.0 || rating > 5.0) {
                throw new IllegalArgumentException("Invalid rating value.");
            }
            model.UpdateRating(movieId, rating);
        } catch (Exception e) {
            System.out.println("Failed to update rating: " + e.getMessage());
        }
    }

}
