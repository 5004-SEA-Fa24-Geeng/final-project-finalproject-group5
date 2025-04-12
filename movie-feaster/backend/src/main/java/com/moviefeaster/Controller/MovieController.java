package com.moviefeaster.Controller;

import com.moviefeaster.Model.*;
import com.moviefeaster.Service.MovieModel;
import com.moviefeaster.Service.MovieParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String parsedTitle = inputProcessor.optionalParseTitle(title);
        String parsedDirector = inputProcessor.optionalParseDirector(director);
        String parsedCast = inputProcessor.optionalParseCast(cast);
        Integer parsedYear = inputProcessor.optionalParseYear(year);
        Genre parsedGenre = inputProcessor.optionalParseGenre(genre);

        Map<MovieFilterType, Object> filterStrategy = new HashMap<>();

        if (parsedTitle != null && !parsedTitle.isEmpty()) {
            filterStrategy.put(MovieFilterType.TITLE_KEYWORD, parsedTitle);
        }

        if (parsedDirector != null && !parsedDirector.isEmpty()) {
            filterStrategy.put(MovieFilterType.DIRECTOR, parsedDirector);
        }

        if (parsedCast != null && !parsedCast.isEmpty()) {
            filterStrategy.put(MovieFilterType.ACTOR, parsedCast);
        }

        if (parsedYear != null) {
            filterStrategy.put(MovieFilterType.YEAR, parsedYear);
        }

        if (parsedGenre != null) {
            filterStrategy.put(MovieFilterType.GENRE, parsedGenre.toString());
        }

        // Use a clean search if we have filters, otherwise return all movies
        boolean useNewFilter = !filterStrategy.isEmpty();
        model.searchByFilter(useNewFilter, filterStrategy);

        return model.getProcessedMovies();
    }

    @Override
    @GetMapping("/sort")
    public List<Movie> handleSort(
            @RequestParam(required = false) String sortType
    ) {
        MovieSorterType toSortOn = MovieSorterType.fromValue(sortType);
        model.sortMovieList(toSortOn);
        return model.getProcessedMovies();
    }

    /**
     * Handles user-submitted comment for a selected movie.
     *
     * @param movieId The ID of the movie to comment on.
     * @param comment The comment text.
     * @return  comment
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
    public void handleRatingSubmission(@PathVariable int movieId, @RequestBody double rating) {
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
