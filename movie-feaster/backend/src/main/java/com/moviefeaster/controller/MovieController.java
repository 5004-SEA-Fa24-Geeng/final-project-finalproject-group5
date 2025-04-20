package com.moviefeaster.controller;

import com.moviefeaster.model.*;
import com.moviefeaster.service.MovieModel;
import com.moviefeaster.utils.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Logger instance for logging events and errors within the MovieController.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    /**
     * The main model responsible for managing and processing movie data.
     */
    private final MovieModel model;

    /**
     * Utility for parsing and validating user inputs from the view layer.
     */
    private final InputProcessor inputProcessor;

    /**
     * Constructs a new MovieController with the given model and view.
     * @param model The movie model to use
     * @param inputProcessor The input processor to use
     */
    @Autowired
    public MovieController(final MovieModel model, final InputProcessor inputProcessor) {
        this.model = model;
        this.inputProcessor = inputProcessor;
    }

    /**
     * Handles a multi-filter search request. Each field is optional.
     * Input is parsed and validated using InputProcessor.
     */
    @Override
    @GetMapping("/search")
    public List<Movie> handleMultiFilterSearch(
            @RequestParam(required = false) final String title,
            @RequestParam(required = false) final String director,
            @RequestParam(required = false) final String cast,
            @RequestParam(required = false) final String year,
            @RequestParam(required = false) final String genre
    ) {
        final String parsedTitle = inputProcessor.optionalParseTitle(title);
        final String parsedDirector = inputProcessor.optionalParseDirector(director);
        final String parsedCast = inputProcessor.optionalParseCast(cast);
        final Integer parsedYear = inputProcessor.optionalParseYear(year);
        final Genre parsedGenre = inputProcessor.optionalParseGenre(genre);

        final Map<MovieFilterType, Object> filterStrategy = new HashMap<>();
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

        model.searchByFilter(filterStrategy);
        return model.getProcessedMovies();
    }

    /**
     * Handles sort request. Only one sort type can be applied on.
     */
    @Override
    @GetMapping("/sort")
    public List<Movie> handleSort(@RequestParam(required = false) final String sortType) {
        final MovieSorterType toSortOn = MovieSorterType.fromValue(sortType);
        model.sortMovieList(toSortOn);
        return model.getProcessedMovies();
    }

    /**
     * Handles user-submitted comment for a selected movie.
     */
    @PostMapping("/{movieId}/comment")
    @Override
    public String handleCommentSubmission(
            @PathVariable final int movieId,
            @RequestBody final String comment
    ) {
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Invalid comment.");
        }
        model.updateComments(movieId, comment);
        return comment;
    }

    /**
     * Handles user-submitted rating for a selected movie.
     */
    @PostMapping("/{movieId}/rating")
    @Override
    public void handleRatingSubmission(
            @PathVariable final int movieId,
            @RequestBody final double rating
    ) {
        try {
            if (rating < 0.0 || rating > 5.0) {
                throw new IllegalArgumentException("Invalid rating value.");
            }
            model.updateRating(movieId, rating);
        } catch (Exception e) {
            LOGGER.warn("Failed to update rating: {}", e.getMessage());
        }
    }

    /**
     * Retrieves all available movie genres.
     *
     * @return List of all available genre names
     */
    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return Arrays.stream(Genre.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all movies in the system.
     *
     * @return List of all movies
     */
    @GetMapping("")
    public List<Movie> getAllMovies() {
        return model.getMovies();
    }

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param movieId The ID of the movie to retrieve
     * @return The movie with the specified ID
     */
    @GetMapping("/{movieId}")
    public Movie getMovieById(@PathVariable final int movieId) {
        return model.getMovieById(movieId);
    }

    /**
     * Exports movies in the specified format.
     *
     * @param format The format to export the movies in (PRETTY, JSON, XML, CSV)
     * @return ResponseEntity containing the exported movie data
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportMovies(
            @RequestParam(defaultValue = "PRETTY") final String format) {
        final HttpHeaders headers = new HttpHeaders();
        try {
            Format outputFormat = Format.containsValues(format);
            if (outputFormat == null) {
                outputFormat = Format.PRETTY;
            }

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final List<Movie> movies =
                    model.getProcessedMovies() != null ? model.getProcessedMovies() : model.getMovies();
            DataFormatter.write(movies, outputFormat, outputStream);

            String contentType;
            String filename;
            switch (outputFormat) {
                case JSON:
                    contentType = "application/json";
                    filename = "movies.json";
                    break;
                case XML:
                    contentType = "application/xml";
                    filename = "movies.xml";
                    break;
                case CSV:
                    contentType = "text/csv";
                    filename = "movies.csv";
                    break;
                default:
                    contentType = "text/plain";
                    filename = "movies.txt";
                    break;
            }

            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to export movies: {}", e.getMessage());
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
