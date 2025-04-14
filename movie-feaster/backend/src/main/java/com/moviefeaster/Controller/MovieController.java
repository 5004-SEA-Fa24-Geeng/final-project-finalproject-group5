package com.moviefeaster.Controller;

import com.moviefeaster.Model.*;
import com.moviefeaster.Service.MovieModel;
import com.moviefeaster.Service.MovieParser;
import com.moviefeaster.Utils.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (parsedTitle == null && parsedDirector == null &&
                parsedCast == null && parsedYear == null && parsedGenre == null) {
            return model.getMovies();
        }

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
     *
     * @param sortType    The sort type that wanted to apply on from the view.
     */
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

    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return Arrays.stream(Genre.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("")
    public List<Movie> getAllMovies() {
        return model.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id) {
        for (Movie movie : model.getMovies()) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        throw new IllegalArgumentException("Movie not found with ID: " + id);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportMovies(@RequestParam(defaultValue = "PRETTY") String format) {
        try {
            Format outputFormat = Format.containsValues(format);
            if (outputFormat == null) {
                outputFormat = Format.PRETTY;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            List<Movie> movies = model.getProcessedMovies() != null ? model.getProcessedMovies() : model.getMovies();

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

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
