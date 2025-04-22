package com.moviefeaster.controller;

import com.moviefeaster.model.*;
import com.moviefeaster.service.MovieModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for MovieController.
 * Tests the functionality of MovieController using JUnit without Mockito.
 * The class tests various controller methods including filtering, sorting,
 * comment and rating submissions, and data export functionality.
 */
public class MovieControllerTest {

    /** The movie controller instance being tested. */
    private MovieController movieController;

    /** Test implementation of MovieModel for verifying controller behavior. */
    private TestMovieModel movieModel;

    /** Test implementation of InputProcessor for providing controlled input. */
    private TestInputProcessor inputProcessor;

    /** Collection of test movie data used for testing controller methods. */
    private List<Movie> testMovies;

    /**
     * Sets up the test environment before each test.
     * Creates test movies, initializes test implementations of dependencies,
     * and creates the controller with these test dependencies.
     */
    @BeforeEach
    public void setUp() {
        // Create test movies
        testMovies = createTestMovies();

        // Create test implementations for dependencies
        movieModel = new TestMovieModel(testMovies);
        inputProcessor = new TestInputProcessor();

        // Create controller with test dependencies
        movieController = new MovieController(movieModel, inputProcessor);
    }

    /**
     * Tests the handleMultiFilterSearch method when all parameters are null.
     * Verifies that the method returns all movies and calls the model's
     * searchByFilter method with an empty filter strategy.
     */
    @Test
    public void testHandleMultiFilterSearchAllParamsNull() {
        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, null, null, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(0, movieModel.filterStrategy.size());
    }

    /**
     * Tests the handleMultiFilterSearch method with only a title parameter.
     * Verifies that the method correctly handles a single filter parameter,
     * passes it to the model, and returns the expected result.
     */
    @Test
    public void testHandleMultiFilterSearchWithTitle() {
        // Setup
        String title = "Inception";
        inputProcessor.titleToReturn = title;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(title, null, null, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(1, movieModel.filterStrategy.size());
        assertEquals(title, movieModel.filterStrategy.get(MovieFilterType.TITLE_KEYWORD));
    }

    /**
     * Tests the handleMultiFilterSearch method with multiple parameters.
     * Verifies that the method correctly processes multiple filter parameters,
     * passes them to the model with the appropriate filter types, and returns
     * the expected result.
     */
    @Test
    public void testHandleMultiFilterSearchWithMultipleParams() {
        // Setup
        String director = "Nolan";
        Integer year = 2010;
        inputProcessor.directorToReturn = director;
        inputProcessor.yearToReturn = year;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, director, null, "2010", null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(2, movieModel.filterStrategy.size());
        assertEquals(director, movieModel.filterStrategy.get(MovieFilterType.DIRECTOR));
        assertEquals(year, movieModel.filterStrategy.get(MovieFilterType.YEAR));
    }

    /**
     * Tests the handleMultiFilterSearch method with a genre parameter.
     * Verifies that the method correctly processes the genre parameter,
     * passes it to the model with the appropriate filter type, and returns
     * the expected result.
     */
    @Test
    public void testHandleMultiFilterSearchWithGenre() {
        // Setup
        Genre genre = Genre.ACTION;
        inputProcessor.genreToReturn = genre;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, null, null, null, "ACTION");

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(1, movieModel.filterStrategy.size());
        assertEquals(genre.toString(), movieModel.filterStrategy.get(MovieFilterType.GENRE));
    }

    /**
     * Tests the handleMultiFilterSearch method with a cast parameter.
     * Verifies that the method correctly processes the cast parameter,
     * passes it to the model with the appropriate filter type, and returns
     * the expected result.
     */
    @Test
    public void testHandleMultiFilterSearchWithCast() {
        // Setup
        String cast = "Leonardo DiCaprio";
        inputProcessor.castToReturn = cast;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, null,
                cast, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(1, movieModel.filterStrategy.size());
        assertEquals(cast, movieModel.filterStrategy.get(MovieFilterType.ACTOR));
    }

    /**
     * Tests the handleMultiFilterSearch method with an empty title parameter.
     * Verifies that the method correctly handles empty parameters by not
     * including them in the filter strategy.
     */
    @Test
    public void testHandleMultiFilterSearchWithEmptyTitle() {
        // Setup
        String title = "";
        inputProcessor.titleToReturn = title;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(title, null, null, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(0, movieModel.filterStrategy.size());
        assertFalse(movieModel.filterStrategy.containsKey(MovieFilterType.TITLE_KEYWORD));
    }

    /**
     * Tests the handleMultiFilterSearch method with an empty director parameter.
     * Verifies that the method correctly handles empty parameters by not
     * including them in the filter strategy.
     */
    @Test
    public void testHandleMultiFilterSearchWithEmptyDirector() {
        // Setup
        String director = "";
        inputProcessor.directorToReturn = director;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, director, null, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(0, movieModel.filterStrategy.size());
        assertFalse(movieModel.filterStrategy.containsKey(MovieFilterType.DIRECTOR));
    }

    /**
     * Tests the handleMultiFilterSearch method with an empty cast parameter.
     * Verifies that the method correctly handles empty parameters by not
     * including them in the filter strategy.
     */
    @Test
    public void testHandleMultiFilterSearchWithEmptyCast() {
        // Setup
        String cast = "";
        inputProcessor.castToReturn = cast;

        // Test
        List<Movie> result = movieController.handleMultiFilterSearch(null, null, cast, null, null);

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.searchByFilterCalled);
        assertEquals(0, movieModel.filterStrategy.size());
        assertFalse(movieModel.filterStrategy.containsKey(MovieFilterType.ACTOR));
    }

    /**
     * Tests the handleSort method with a valid sort type.
     * Verifies that the method correctly passes the sort type to the model
     * and returns the expected result.
     */
    @Test
    public void testHandleSort() {
        // Test
        List<Movie> result = movieController.handleSort("TITLE_ASC");

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.sortMovieListCalled);
        assertEquals(MovieSorterType.TITLE_ASC, movieModel.sortType);
    }

    /**
     * Tests the handleSort method with an invalid sort type.
     * Verifies that the method handles invalid sort types by passing null
     * to the model and still returns the expected result.
     */
    @Test
    public void testHandleSortWithInvalidSortType() {
        // Test
        List<Movie> result = movieController.handleSort("INVALID_SORT");

        // Verify
        assertEquals(testMovies, result);
        assertTrue(movieModel.sortMovieListCalled);
        assertNull(movieModel.sortType); // The controller should pass null to the model for invalid sort types
    }

    /**
     * Tests the handleCommentSubmission method with a valid comment.
     * Verifies that the method correctly passes the movie ID and comment
     * to the model and returns the expected result.
     */
    @Test
    public void testHandleCommentSubmissionValidComment() {
        // Setup
        int movieId = 1;
        String comment = "Great movie!";

        // Test
        String result = movieController.handleCommentSubmission(movieId, comment);

        // Verify
        assertEquals(comment, result);
        assertTrue(movieModel.updateCommentsCalled);
        assertEquals(movieId, movieModel.commentMovieId);
        assertEquals(comment, movieModel.commentText);
    }

    /**
     * Tests the handleCommentSubmission method with an empty comment.
     * Verifies that the method throws an IllegalArgumentException with
     * the expected message and does not call the model.
     */
    @Test
    public void testHandleCommentSubmissionInvalidComment() {
        // Setup
        int movieId = 1;
        String comment = "";

        // Test & Verify
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            movieController.handleCommentSubmission(movieId, comment);
        });

        assertEquals("Invalid comment.", exception.getMessage());
        assertFalse(movieModel.updateCommentsCalled);
    }

    /**
     * Tests the handleCommentSubmission method with a null comment.
     * Verifies that the method throws an IllegalArgumentException with
     * the expected message and does not call the model.
     */
    @Test
    public void testHandleCommentSubmissionNullComment() {
        // Setup
        int movieId = 1;
        String comment = null;

        // Test & Verify
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            movieController.handleCommentSubmission(movieId, comment);
        });

        assertEquals("Invalid comment.", exception.getMessage());
        assertFalse(movieModel.updateCommentsCalled);
    }

    /**
     * Tests the handleRatingSubmission method with a valid rating.
     * Verifies that the method correctly passes the movie ID and rating
     * to the model.
     */
    @Test
    public void testHandleRatingSubmissionValidRating() {
        // Setup
        int movieId = 1;
        double rating = 4.5;

        // Test
        movieController.handleRatingSubmission(movieId, rating);

        // Verify
        assertTrue(movieModel.updateRatingCalled);
        assertEquals(movieId, movieModel.ratingMovieId);
        assertEquals(rating, movieModel.ratingValue);
    }

    /**
     * Tests the handleRatingSubmission method with an invalid rating (above 5.0).
     * Verifies that the method does not call the model's updateRating method.
     */
    @Test
    public void testHandleRatingSubmissionInvalidRating() {
        // Setup
        int movieId = 1;
        double rating = 6.0;

        // Test
        movieController.handleRatingSubmission(movieId, rating);

        // Verify
        assertFalse(movieModel.updateRatingCalled);
    }

    /**
     * Tests the handleRatingSubmission method with a zero rating.
     * Verifies that the method correctly passes the movie ID and rating
     * to the model, as zero is a valid rating.
     */
    @Test
    public void testHandleRatingSubmissionZeroRating() {
        // Setup
        int movieId = 1;
        double rating = 0.0;

        // Test
        movieController.handleRatingSubmission(movieId, rating);

        // Verify
        assertTrue(movieModel.updateRatingCalled);
        assertEquals(movieId, movieModel.ratingMovieId);
        assertEquals(rating, movieModel.ratingValue);
    }

    /**
     * Tests the handleRatingSubmission method with a negative rating.
     * Verifies that the method does not call the model's updateRating method.
     */
    @Test
    public void testHandleRatingSubmissionNegativeRating() {
        // Setup
        int movieId = 1;
        double rating = -1.0;

        // Test
        movieController.handleRatingSubmission(movieId, rating);

        // Verify
        assertFalse(movieModel.updateRatingCalled);
    }

    /**
     * Tests the getAllGenres method.
     * Verifies that the method returns a list containing all genre names
     * and that the list has the expected size and contents.
     */
    @Test
    public void testGetAllGenres() {
        // Test
        List<String> genres = movieController.getAllGenres();

        // Verify
        assertNotNull(genres);
        assertEquals(Genre.values().length, genres.size());
        assertTrue(genres.contains(Genre.ACTION.name()));
        assertTrue(genres.contains(Genre.COMEDY.name()));
    }

    /**
     * Tests the getAllMovies method.
     * Verifies that the method returns all test movies.
     */
    @Test
    public void testGetAllMovies() {
        // Test
        List<Movie> result = movieController.getAllMovies();

        // Verify
        assertEquals(testMovies, result);
    }

    /**
     * Tests the getMovieById method with an existing movie ID.
     * Verifies that the method correctly passes the movie ID to the model
     * and returns the expected movie.
     */
    @Test
    public void testGetMovieByIdExistingId() {
        // Setup
        int movieId = 1;
        Movie expectedMovie = testMovies.get(0);
        movieModel.movieById = expectedMovie;

        // Test
        Movie result = movieController.getMovieById(movieId);

        // Verify
        assertEquals(expectedMovie, result);
        assertEquals(movieId, movieModel.movieByIdCalled);
    }

    /**
     * Tests the getMovieById method with a non-existing movie ID.
     * Verifies that the method correctly passes the movie ID to the model
     * and returns null when no movie is found.
     */
    @Test
    public void testGetMovieByIdNonExistingId() {
        // Setup
        int movieId = 999;
        movieModel.movieById = null;

        // Test
        Movie result = movieController.getMovieById(movieId);

        // Verify
        assertNull(result);
        assertEquals(movieId, movieModel.movieByIdCalled);
    }

    /**
     * Tests the exportMovies method with the default format (PRETTY).
     * Verifies that the method returns a ResponseEntity with the expected
     * status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesDefaultFormat() {
        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("PRETTY");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("text/plain", Objects.requireNonNull(response.getHeaders().getContentType()).toString());
    }

    /**
     * Tests the exportMovies method with JSON format.
     * Verifies that the method returns a ResponseEntity with the expected
     * status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesJsonFormat() {
        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("JSON");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("application/json", Objects.requireNonNull(response.getHeaders()
                .getContentType()).toString());
    }

    /**
     * Tests the exportMovies method with XML format.
     * Verifies that the method returns a ResponseEntity with the expected
     * status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesXmlFormat() {
        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("XML");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("application/xml", Objects.requireNonNull(response.getHeaders()
                .getContentType()).toString());
    }

    /**
     * Tests the exportMovies method with CSV format.
     * Verifies that the method returns a ResponseEntity with the expected
     * status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesCsvFormat() {
        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("CSV");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("text/csv", Objects.requireNonNull(response.getHeaders().getContentType()).toString());
    }

    /**
     * Tests the exportMovies method with an invalid format.
     * Verifies that the method defaults to PRETTY format and returns a
     * ResponseEntity with the expected status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesInvalidFormat() {
        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("INVALID_FORMAT");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Should default to PRETTY format
        assertEquals("text/plain", Objects.requireNonNull(response.getHeaders().getContentType()).toString());
    }

    /**
     * Tests the exportMovies method when an exception occurs.
     * Verifies that the method returns a ResponseEntity with an INTERNAL_SERVER_ERROR
     * status code and a null body.
     */
    @Test
    public void testExportMoviesWithException() {
        // Setup a scenario that would cause an exception
        // We'll modify our test model to throw an exception when accessing processed movies
        movieModel.throwExceptionOnGetProcessedMovies = true;

        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("JSON");

        // Verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    /**
     * Tests the exportMovies method when processedMovies returns null.
     * Verifies that the method falls back to using getMovies() and returns
     * a ResponseEntity with the expected status code, content type, and non-null body.
     */
    @Test
    public void testExportMoviesWithNullProcessedMovies() {
        // Setup - make processedMovies return null
        movieModel.returnNullForProcessedMovies = true;

        // Test
        ResponseEntity<byte[]> response = movieController.exportMovies("JSON");

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("application/json", Objects.requireNonNull(response.getHeaders()
                .getContentType()).toString());
        assertTrue(movieModel.getMoviesCalled); // Verify that getMovies() was called as fallback
    }

    /**
     * Helper method to create test movies.
     * Creates and returns a list of test movies with predefined properties.
     *
     * @return A list of test Movie objects
     */
    private List<Movie> createTestMovies() {
        List<Movie> movies = new ArrayList<>();

        // Create first test movie - Inception
        Movie movie1 = new Movie.Builder()
                .movieId(1)
                .title("Inception")
                .directors(List.of("Christopher Nolan"))
                .year(2010)
                .rating(8.8)
                .genres(List.of(Genre.ACTION, Genre.SCIENCE_FICTION))
                .overview("A thief who steals corporate secrets through the use of dream-sharing technology.")
                .castings(List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"))
                .imgUrl("https://example.com/inception.jpg")
                .build();

        // Create second test movie - The Dark Knight
        Movie movie2 = new Movie.Builder()
                .movieId(2)
                .title("The Dark Knight")
                .directors(List.of("Christopher Nolan"))
                .year(2008)
                .rating(9.0)
                .genres(List.of(Genre.ACTION, Genre.CRIME))
                .overview("Batman fights the menace known as The Joker.")
                .castings(List.of("Christian Bale", "Heath Ledger"))
                .imgUrl("https://example.com/dark-knight.jpg")
                .build();

        movies.add(movie1);
        movies.add(movie2);

        return movies;
    }

    /**
     * Test implementation of MovieModel that tracks method calls.
     * This class extends MovieModel to provide test-specific behavior
     * and tracking of method calls and parameters.
     */
    private static class TestMovieModel extends MovieModel {
        /** A list of original movies used for testing. */
        private final List<Movie> movies;

        /** A list of processed movies that can be filtered or sorted. */
        private final List<Movie> processedMovies;

        /** Flag to track if searchByFilter method was called. */
        private boolean searchByFilterCalled = false;

        /** Flag to track if sortMovieList method was called. */
        private boolean sortMovieListCalled = false;

        /** Flag to track if updateComments method was called. */
        private boolean updateCommentsCalled = false;

        /** Flag to track if updateRating method was called. */
        private boolean updateRatingCalled = false;

        /** Map to store filter strategy passed to searchByFilter method. */
        private Map<MovieFilterType, Object> filterStrategy = new HashMap<>();

        /** Sort type passed to sortMovieList method. */
        private MovieSorterType sortType;

        /** Movie ID passed to updateComments method. */
        private int commentMovieId;

        /** Comment text passed to updateComments method. */
        private String commentText;

        /** Movie ID passed to updateRating method. */
        private int ratingMovieId;

        /** Rating value passed to updateRating method. */
        private double ratingValue;

        /** Movie ID passed to getMovieById method. */
        private int movieByIdCalled;

        /** Movie to return from getMovieById method. */
        private Movie movieById;

        /** Flag to control throwing exception in getProcessedMovies method. */
        private boolean throwExceptionOnGetProcessedMovies = false;

        /** Flag to control returning null in getProcessedMovies method. */
        private boolean returnNullForProcessedMovies = false;

        /** Flag to track if getMovies method was called. */
        private boolean getMoviesCalled = false;


        /**
         * Constructs a TestMovieModel with the given test movies.
         *
         * @param testMovies The list of test movies to use
         */
        TestMovieModel(List<Movie> testMovies) {
            this.movies = testMovies;
            this.processedMovies = new ArrayList<>(testMovies);
        }

        @Override
        public void fetchMovies() {
            // Do nothing for tests
        }

        @Override
        public List<Movie> getMovies() {
            getMoviesCalled = true;
            return movies;
        }
        @Override
        public Movie getMovieById(int movieId) {
            this.movieByIdCalled = movieId;
            return movieById;
        }

        @Override
        public List<Movie> getProcessedMovies() {
            if (throwExceptionOnGetProcessedMovies) {
                throw new RuntimeException("Test exception");
            }
            return returnNullForProcessedMovies ? null : processedMovies;
        }
        @Override
        public void writeFile(boolean useProcessedMovie, Format format) {
            // Do nothing for tests
        }

        @Override
        public void searchByFilter(Map<MovieFilterType, Object> filterStrategy) {
            this.searchByFilterCalled = true;
            this.filterStrategy = filterStrategy != null ? filterStrategy : new HashMap<>();
        }

        @Override
        public void sortMovieList(MovieSorterType sortType) {
            this.sortMovieListCalled = true;
            this.sortType = sortType;
        }

        @Override
        public void updateComments(int movieId, String comment) {
            this.updateCommentsCalled = true;
            this.commentMovieId = movieId;
            this.commentText = comment;
        }

        @Override
        public void updateRating(int movieId, double rating) {
            this.updateRatingCalled = true;
            this.ratingMovieId = movieId;
            this.ratingValue = rating;
        }
    }

    /**
     * Test implementation of InputProcessor that returns predefined values.
     * This class extends InputProcessor to provide test-specific behavior
     * by returning predefined values for each parsing method.
     */
    private static class TestInputProcessor extends InputProcessor {
        /** Predefined title value to return from optionalParseTitle method. */
        private String titleToReturn;

        /** Predefined director value to return from optionalParseDirector method. */
        private String directorToReturn;

        /** Predefined cast value to return from optionalParseCast method. */
        private String castToReturn;

        /** Predefined year value to return from optionalParseYear method. */
        private Integer yearToReturn;

        /** Predefined genre value to return from optionalParseGenre method. */
        private Genre genreToReturn;

        @Override
        public String optionalParseTitle(String title) {
            return titleToReturn;
        }

        @Override
        public String optionalParseDirector(String director) {
            return directorToReturn;
        }

        @Override
        public String optionalParseCast(String cast) {
            return castToReturn;
        }

        @Override
        public Integer optionalParseYear(String year) {
            return yearToReturn;
        }

        @Override
        public Genre optionalParseGenre(String genre) {
            return genreToReturn;
        }
    }
}
