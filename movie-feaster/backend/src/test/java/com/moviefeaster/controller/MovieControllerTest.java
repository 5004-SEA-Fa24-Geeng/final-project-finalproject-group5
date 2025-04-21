package com.moviefeaster.controller;

import com.moviefeaster.model.Format;
import com.moviefeaster.model.Genre;
import com.moviefeaster.model.Movie;
import com.moviefeaster.model.MovieFilterType;
import com.moviefeaster.service.MovieModelInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieController.
 * This class tests the functionality of the MockMovieController by using stub implementations
 * of the required interfaces.
 *
 * @author MovieFeaster Team
 * @version 1.0
 * @since 2025-04-21
 */
public class MovieControllerTest {

    /**
     * The movie model stub used for testing.
     */
    private StubMovieModel movieModel;

    /**
     * The input processor stub used for testing.
     */
    private StubInputProcessor inputProcessor;

    /**
     * The movie controller mock being tested.
     */
    private MockMovieController movieController;

    /**
     * Sets up the test environment before each test.
     * Creates test movie data, initializes stubs, and sets up the controller.
     */
    @BeforeEach
    public void setUp() {
        // Create test movie data
        List<Movie> testMovies = new ArrayList<>();

        // Create movies using proper constructor and methods based on Movie.java
        List<String> directors1 = new ArrayList<>();
        directors1.add("Christopher Nolan");
        List<Genre> genres1 = new ArrayList<>();
        genres1.add(Genre.ACTION);
        genres1.add(Genre.SCIENCE_FICTION);
        List<String> cast1 = new ArrayList<>();
        cast1.add("Leonardo DiCaprio");
        cast1.add("Joseph Gordon-Levitt");

        Movie movie1 = new Movie.Builder()
                .movieId(1)
                .title("Inception")
                .directors(directors1)
                .year(2010)
                .rating(8.8)
                .genres(genres1)
                .overview("A thief who steals corporate secrets")
                .castings(cast1)
                .imgUrl("inception.jpg")
                .build();
//        Movie movie1 = new Movie(1, "Inception", directors1, 2010, 8.8,
//                genres1, "A thief who steals corporate secrets", cast1, "inception.jpg");

        List<String> directors2 = new ArrayList<>();
        directors2.add("Christopher Nolan");
        List<Genre> genres2 = new ArrayList<>();
        genres2.add(Genre.ACTION);
        genres2.add(Genre.CRIME);
        List<String> cast2 = new ArrayList<>();
        cast2.add("Christian Bale");
        cast2.add("Heath Ledger");

        Movie movie2 = new Movie.Builder()
                .movieId(2)
                .title("The Dark Knight")
                .directors(directors2)
                .year(2008)
                .rating(9.0)
                .genres(genres2)
                .overview("Batman fights the Joker")
                .castings(cast2)
                .imgUrl("dark_knight.jpg")
                .build();

//        Movie movie2 = new Movie(2, "The Dark Knight", directors2, 2008, 9.0,
//                genres2, "Batman fights the Joker", cast2, "dark_knight.jpg");

        testMovies.add(movie1);
        testMovies.add(movie2);

        // Initialize stubs
        movieModel = new StubMovieModel(testMovies);
        inputProcessor = new StubInputProcessor();

        // Initialize controller with stubs
        movieController = new MockMovieController(movieModel, inputProcessor);
    }

    /**
     * Tests the handleMultiFilterSearch method with only a title filter.
     * Verifies that the correct filter is applied and the search is performed.
     */
    @Test
    public void testHandleMultiFilterSearchWithTitle() {
        // Setup input processor to return valid data
        inputProcessor.setTitleToReturn("Inception");

        // Execute search
        List<Movie> result = movieController.handleMultiFilterSearch(
                "Inception", null, null, null, null
        );

        // Verify that the correct filter was applied
        Map<MovieFilterType, Object> expectedFilter = new HashMap<>();
        expectedFilter.put(MovieFilterType.TITLE_KEYWORD, "Inception");

        assertEquals(expectedFilter, movieController.getLastAppliedFilter());
        assertTrue(movieModel.isSearchByFilterCalled());
    }

    /**
     * Tests the handleMultiFilterSearch method with multiple filters.
     * Verifies that all filters are properly applied in the search.
     */
    @Test
    public void testHandleMultiFilterSearchWithMultipleFilters() {
        // Setup input processor
        inputProcessor.setTitleToReturn("Dark");
        inputProcessor.setDirectorToReturn("Nolan");
        inputProcessor.setYearToReturn(2008);

        // Execute search
        List<Movie> result = movieController.handleMultiFilterSearch(
                "Dark", "Nolan", null, "2008", null
        );

        // Verify that all filters were properly applied
        Map<MovieFilterType, Object> expectedFilter = new HashMap<>();
        expectedFilter.put(MovieFilterType.TITLE_KEYWORD, "Dark");
        expectedFilter.put(MovieFilterType.DIRECTOR, "Nolan");
        expectedFilter.put(MovieFilterType.YEAR, 2008);

        assertEquals(expectedFilter, movieController.getLastAppliedFilter());
    }

    /**
     * Tests the handleMultiFilterSearch method with an invalid year.
     * Verifies that an IllegalArgumentException is thrown when an invalid year is provided.
     */
    @Test
    public void testHandleMultiFilterSearchWithInvalidYear() {
        // Setup input processor to throw exception for invalid year
        inputProcessor.setThrowExceptionForYear(true);

        // Assert that the exception is propagated
        assertThrows(IllegalArgumentException.class, () -> {
            movieController.handleMultiFilterSearch(
                    null, null, null, "invalid year", null
            );
        });
    }

    /**
     * Tests the handleSort method.
     * Verifies that the sort type is correctly recorded.
     */
    @Test
    public void testHandleSort() {
        // Execute sort
        List<Movie> result = movieController.handleSort("YEAR");

        // Verify sort type was recorded
        assertEquals("YEAR", movieController.getLastSortType());
    }

    /**
     * Tests the handleCommentSubmission method with a valid comment.
     * Verifies that the comment is correctly recorded and returned.
     */
    @Test
    public void testHandleCommentSubmissionValidComment() {
        // Execute comment submission
        String comment = "Great movie!";
        String result = movieController.handleCommentSubmission(1, comment);

        // Verify comment was recorded and returned
        assertEquals(1, movieController.getLastCommentMovieId());
        assertEquals(comment, movieController.getLastSubmittedComment());
        assertEquals(comment, result);
        assertTrue(movieModel.isUpdateCommentsCalled());
    }

    /**
     * Tests the handleCommentSubmission method with an empty comment.
     * Verifies that an IllegalArgumentException is thrown when an empty comment is provided.
     */
    @Test
    public void testHandleCommentSubmissionEmptyComment() {
        // Assert that empty comments are rejected
        assertThrows(IllegalArgumentException.class, () -> {
            movieController.handleCommentSubmission(1, "");
        });
    }

    /**
     * Tests the handleRatingSubmission method with a valid rating.
     * Verifies that the rating is correctly recorded and the model is updated.
     */
    @Test
    public void testHandleRatingSubmissionValidRating() {
        // Execute rating submission
        double rating = 4.5;
        movieController.handleRatingSubmission(1, rating);

        // Verify rating was recorded
        assertEquals(1, movieController.getLastRatedMovieId());
        assertEquals(rating, movieController.getLastSubmittedRating());
        assertTrue(movieModel.isUpdateRatingCalled());
    }

    /**
     * Tests the handleRatingSubmission method with an invalid rating.
     * Verifies that the model is not updated when an invalid rating is provided.
     */
    @Test
    public void testHandleRatingSubmissionInvalidRating() {
        // Execute rating submission with invalid rating
        movieController.handleRatingSubmission(1, 6.0);  // Out of range

        // Verify model was not updated
        assertFalse(movieModel.isUpdateRatingCalled());
    }

    /**
     * Mock implementation of MovieControllerInterface for testing.
     * Uses the MovieModelInterface and InputProcessorInterface dependencies.
     */
    public static class MockMovieController implements MovieControllerInterface {

        /**
         * The movie model interface implementation.
         */
        private final MovieModelInterface movieModel;

        /**
         * The input processor interface implementation.
         */
        private final InputProcessorInterface inputProcessor;

        // Tracking variables for test verification
        /**
         * Tracks the last filter map applied in a search operation.
         */
        private Map<MovieFilterType, Object> lastAppliedFilter;

        /**
         * Tracks the ID of the last movie that was rated.
         */
        private int lastRatedMovieId;

        /**
         * Tracks the last rating value that was submitted.
         */
        private double lastSubmittedRating;

        /**
         * Tracks the ID of the last movie that received a comment.
         */
        private int lastCommentMovieId;

        /**
         * Tracks the last comment that was submitted.
         */
        private String lastSubmittedComment;

        /**
         * Tracks the last sort type that was used.
         */
        private String lastSortType;

        /**
         * Constructor for MockMovieController.
         *
         * @param movieModel The movie model to use
         * @param inputProcessor The input processor to use for validation
         */
        public MockMovieController(MovieModelInterface movieModel, InputProcessorInterface inputProcessor) {
            this.movieModel = movieModel;
            this.inputProcessor = inputProcessor;
            this.lastAppliedFilter = new HashMap<>();
            this.lastRatedMovieId = 0;
            this.lastSubmittedRating = 0.0;
            this.lastCommentMovieId = 0;
            this.lastSubmittedComment = "";
            this.lastSortType = "";
        }

        /**
         * Handles multi-filter search for movies.
         * Processes and validates inputs, creates a filter map, and searches for movies.
         *
         * @param title The title keyword to search for (may be null)
         * @param director The director name to search for (may be null)
         * @param cast The actor name to search for (may be null)
         * @param year The year to search for (may be null)
         * @param genre The genre to search for (may be null)
         * @return A list of movies matching the provided filters
         * @throws IllegalArgumentException If any input values are invalid
         */
        @Override
        public List<Movie> handleMultiFilterSearch(
                String title,
                String director,
                String cast,
                String year,
                String genre) {

            // Process and validate inputs using input processor
            String parsedTitle = inputProcessor.optionalParseTitle(title);
            String parsedDirector = inputProcessor.optionalParseDirector(director);
            String parsedCast = inputProcessor.optionalParseCast(cast);
            Integer parsedYear = inputProcessor.optionalParseYear(year);
            Genre parsedGenre = inputProcessor.optionalParseGenre(genre);

            // Create filter map with non-null values
            Map<MovieFilterType, Object> filterMap = new HashMap<>();

            if (parsedTitle != null) {
                filterMap.put(MovieFilterType.TITLE_KEYWORD, parsedTitle);
            }

            if (parsedDirector != null) {
                filterMap.put(MovieFilterType.DIRECTOR, parsedDirector);
            }

            if (parsedCast != null) {
                filterMap.put(MovieFilterType.ACTOR, parsedCast);
            }

            if (parsedYear != null) {
                filterMap.put(MovieFilterType.YEAR, parsedYear);
            }

            if (parsedGenre != null) {
                filterMap.put(MovieFilterType.GENRE, parsedGenre.name());
            }

            // Store last applied filter for test verification
            this.lastAppliedFilter = filterMap;

            // Apply filters via model and return results
            movieModel.searchByFilter(filterMap);
            return ((StubMovieModel) movieModel).getProcessedMovies();
        }

        /**
         * Handles sorting of movies.
         * Records the sort type for verification and returns the sorted movies.
         *
         * @param sortType The type of sort to perform (e.g., "YEAR", "RATING")
         * @return A list of sorted movies
         */
        @Override
        public List<Movie> handleSort(String sortType) {
            // Store for verification
            this.lastSortType = sortType;

            // In a real implementation, would call model's sort method
            // For mock purposes, just return the current processed movies
            return ((StubMovieModel) movieModel).getProcessedMovies();
        }

        /**
         * Handles comment submission for a movie.
         * Validates the comment, records it for verification, and updates the model.
         *
         * @param movieID The ID of the movie to comment on
         * @param comment The comment text
         * @return The submitted comment
         * @throws IllegalArgumentException If the comment is empty or null
         */
        @Override
        public String handleCommentSubmission(int movieID, String comment) {
            // Validate comment
            if (comment == null || comment.trim().isEmpty()) {
                throw new IllegalArgumentException("Comment cannot be empty");
            }

            // Store for verification
            this.lastCommentMovieId = movieID;
            this.lastSubmittedComment = comment;

            // Update model
            movieModel.updateComments(movieID, comment);

            // Return submitted comment
            return comment;
        }

        /**
         * Handles rating submission for a movie.
         * Validates the rating, records it for verification, and updates the model if valid.
         *
         * @param movieID The ID of the movie to rate
         * @param rating The rating value (expected to be between 0 and 5)
         */
        @Override
        public void handleRatingSubmission(int movieID, double rating) {
            // Validate rating (assuming 0-5 scale)
            if (rating < 0 || rating > 5) {
                // Invalid rating, do not update
                return;
            }

            // Store for verification
            this.lastRatedMovieId = movieID;
            this.lastSubmittedRating = rating;

            // Update model
            movieModel.updateRating(movieID, rating);
        }

        /**
         * Gets the last filter applied in handleMultiFilterSearch.
         * This method is used for test verification.
         *
         * @return The last applied filter map
         */
        public Map<MovieFilterType, Object> getLastAppliedFilter() {
            return lastAppliedFilter;
        }

        /**
         * Gets the last movie ID for which a rating was submitted.
         * This method is used for test verification.
         *
         * @return The last rated movie ID
         */
        public int getLastRatedMovieId() {
            return lastRatedMovieId;
        }

        /**
         * Gets the last rating submitted.
         * This method is used for test verification.
         *
         * @return The last submitted rating
         */
        public double getLastSubmittedRating() {
            return lastSubmittedRating;
        }

        /**
         * Gets the last movie ID for which a comment was submitted.
         * This method is used for test verification.
         *
         * @return The last comment movie ID
         */
        public int getLastCommentMovieId() {
            return lastCommentMovieId;
        }

        /**
         * Gets the last comment submitted.
         * This method is used for test verification.
         *
         * @return The last submitted comment
         */
        public String getLastSubmittedComment() {
            return lastSubmittedComment;
        }

        /**
         * Gets the last sort type used.
         * This method is used for test verification.
         *
         * @return The last sort type
         */
        public String getLastSortType() {
            return lastSortType;
        }
    }

    /**
     * Stub implementation of MovieModelInterface for testing.
     */
    private static class StubMovieModel implements MovieModelInterface {
        /** List of movies. */
        private final List<Movie> movies;
        /** List of processed movies. */
        private final List<Movie> processedMovies;

        /**
         * Flag indicating whether searchByFilter method was called.
         */
        private boolean searchByFilterCalled = false;

        /**
         * Flag indicating whether updateComments method was called.
         */
        private boolean updateCommentsCalled = false;

        /**
         * Flag indicating whether updateRating method was called.
         */
        private boolean updateRatingCalled = false;

        StubMovieModel(List<Movie> movies) {
            this.movies = new ArrayList<>(movies);
            this.processedMovies = new ArrayList<>(movies);
        }

        /**
         * Fetches movies from a data source.
         * This is a stub implementation that does nothing for testing purposes.
         */
        @Override
        public void fetchMovies() {
            // Do nothing for tests
        }

        /**
         * Writes movie data to a file.
         * This is a stub implementation that does nothing for testing purposes.
         *
         * @param useProcessedMovie Whether to use processed movies or all movies
         * @param format The format to write the file in
         */
        @Override
        public void writeFile(boolean useProcessedMovie, Format format) {
            // Do nothing for tests
        }

        /**
         * Searches for movies using the specified filters.
         * This is a stub implementation that sets a flag for testing purposes.
         *
         * @param filtersStrategy The map of filters to apply
         */
        @Override
        public void searchByFilter(Map<MovieFilterType, Object> filtersStrategy) {
            searchByFilterCalled = true;

            // In a real implementation, this would filter the movies
        }

        /**
         * Updates the comments for a movie.
         * This is a stub implementation that sets a flag for testing purposes.
         *
         * @param movieID The ID of the movie to update
         * @param comment The comment to add
         */
        @Override
        public void updateComments(int movieID, String comment) {
            updateCommentsCalled = true;
        }

        /**
         * Updates the rating for a movie.
         * This is a stub implementation that sets a flag for testing purposes.
         *
         * @param movieID The ID of the movie to update
         * @param rating The rating to add
         */
        @Override
        public void updateRating(int movieID, double rating) {
            updateRatingCalled = true;
        }

        /**
         * Gets the list of all movies.
         *
         * @return The list of all movies
         */
        public List<Movie> getMovies() {
            return movies;
        }

        /**
         * Gets the list of processed movies (after filtering/sorting).
         *
         * @return The list of processed movies
         */
        public List<Movie> getProcessedMovies() {
            return processedMovies;
        }

        /**
         * Gets a movie by its ID.
         *
         * @param id The ID of the movie to retrieve
         * @return The movie with the specified ID, or null if not found
         */
        public Movie getMovieById(int id) {
            return movies.stream()
                    .filter(movie -> movie.getMovieId() == id)
                    .findFirst()
                    .orElse(null);
        }

        /**
         * Checks if searchByFilter method was called.
         *
         * @return true if searchByFilter was called, false otherwise
         */
        public boolean isSearchByFilterCalled() {
            return searchByFilterCalled;
        }

        /**
         * Checks if updateComments method was called.
         *
         * @return true if updateComments was called, false otherwise
         */
        public boolean isUpdateCommentsCalled() {
            return updateCommentsCalled;
        }

        /**
         * Checks if updateRating method was called.
         *
         * @return true if updateRating was called, false otherwise
         */
        public boolean isUpdateRatingCalled() {
            return updateRatingCalled;
        }
    }

    /**
     * Stub implementation of InputProcessorInterface for testing.
     */
    private static class StubInputProcessor implements InputProcessorInterface {
        /**
         * The title value to return when optionalParseTitle is called.
         */
        private String titleToReturn = null;

        /**
         * The director value to return when optionalParseDirector is called.
         */
        private String directorToReturn = null;

        /**
         * The cast value to return when optionalParseCast is called.
         */
        private String castToReturn = null;

        /**
         * The year value to return when optionalParseYear is called.
         */
        private Integer yearToReturn = null;

        /**
         * The genre value to return when optionalParseGenre is called.
         */
        private Genre genreToReturn = null;

        /**
         * Flag to control whether optionalParseYear throws an exception.
         */
        private boolean throwExceptionForYear = false;

        /**
         * Sets the title value to return.
         *
         * @param titleToReturn The title to return
         */
        public void setTitleToReturn(String titleToReturn) {
            this.titleToReturn = titleToReturn;
        }

        /**
         * Sets the director value to return.
         *
         * @param directorToReturn The director to return
         */
        public void setDirectorToReturn(String directorToReturn) {
            this.directorToReturn = directorToReturn;
        }

        /**
         * Sets the cast value to return.
         *
         * @param castToReturn The cast to return
         */
        public void setCastToReturn(String castToReturn) {
            this.castToReturn = castToReturn;
        }

        /**
         * Sets the year value to return.
         *
         * @param yearToReturn The year to return
         */
        public void setYearToReturn(Integer yearToReturn) {
            this.yearToReturn = yearToReturn;
        }

        /**
         * Sets the genre value to return.
         *
         * @param genreToReturn The genre to return
         */
        public void setGenreToReturn(Genre genreToReturn) {
            this.genreToReturn = genreToReturn;
        }

        /**
         * Sets whether optionalParseYear should throw an exception.
         *
         * @param throwExceptionForYear True to throw exception, false otherwise
         */
        public void setThrowExceptionForYear(boolean throwExceptionForYear) {
            this.throwExceptionForYear = throwExceptionForYear;
        }

        /**
         * Parses an optional title input.
         * This is a stub implementation that returns a predefined value.
         *
         * @param input The input to parse
         * @return The parsed title, or null if input is null/empty
         */
        @Override
        public String optionalParseTitle(String input) {
            return titleToReturn;
        }

        /**
         * Parses an optional director input.
         * This is a stub implementation that returns a predefined value.
         *
         * @param input The input to parse
         * @return The parsed director, or null if input is null/empty
         */
        @Override
        public String optionalParseDirector(String input) {
            return directorToReturn;
        }

        /**
         * Parses an optional cast input.
         * This is a stub implementation that returns a predefined value.
         *
         * @param input The input to parse
         * @return The parsed cast member, or null if input is null/empty
         */
        @Override
        public String optionalParseCast(String input) {
            return castToReturn;
        }

        /**
         * Parses an optional year input.
         * This is a stub implementation that returns a predefined value or throws
         * an exception if throwExceptionForYear is true.
         *
         * @param input The input to parse
         * @return The parsed year as an Integer, or null if input is null/empty
         * @throws IllegalArgumentException If the year format is invalid and throwExceptionForYear is true
         */
        @Override
        public Integer optionalParseYear(String input) {
            if (throwExceptionForYear) {
                throw new IllegalArgumentException("Invalid year format");
            }
            return yearToReturn;
        }

        /**
         * Parses an optional genre input.
         * This is a stub implementation that returns a predefined value.
         *
         * @param input The input to parse
         * @return The parsed Genre, or null if input is null/empty
         */
        @Override
        public Genre optionalParseGenre(String input) {
            return genreToReturn;
        }
    }
}
