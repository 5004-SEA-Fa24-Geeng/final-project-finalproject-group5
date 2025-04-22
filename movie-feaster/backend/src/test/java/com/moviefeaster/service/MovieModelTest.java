package com.moviefeaster.service;

import com.moviefeaster.model.Format;
import com.moviefeaster.model.Movie;
import com.moviefeaster.model.MovieSorterType;
import com.moviefeaster.model.MovieFilterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieModel service.
 */
class MovieModelTest {

    /** MovieModel instance for testing. */
    private MovieModel model;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        model = new MovieModel();
    }

    /**
     * Tests fetching movies from the model.
     */
    @Test
    void fetchMovies() {
        model.fetchMovies();
        List<Movie> movies = model.getMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty(), "Movies list should not be empty after fetching");
    }

    /**
     * Tests getting movies from the model.
     */
    @Test
    void getMovies() {
        List<Movie> movies = model.getMovies();
        assertNotNull(movies);
    }

    /**
     * Tests getting a movie by ID.
     */
    @Test
    void getMovieById() {
        model.fetchMovies();
        Movie movie = model.getMovies().get(0);
        Movie found = model.getMovieById(movie.getMovieId());
        assertEquals(movie.getMovieId(), found.getMovieId());
    }

    /**
     * Tests getting a movie by non-existent ID.
     */
    @Test
    void getMovieByNonExistentId() {
        model.fetchMovies();
        Movie found = model.getMovieById(-1);
        assertNull(found, "Should return null for non-existent movie ID");
    }

    /**
     * Tests getting processed movies from the model.
     */
    @Test
    void getProcessedMovies() {
        List<Movie> processed = model.getProcessedMovies();
        assertNotNull(processed);
    }

    /**
     * Tests writing movies to a file in JSON format.
     */
    @Test
    void writeFileJson() {
        model.fetchMovies();
        model.writeFile(false, Format.JSON);
        File outputFile = new File("output.json");
        assertTrue(outputFile.exists(), "Output file should exist after writing");
        outputFile.delete(); // Clean up
    }

    /**
     * Tests writing movies to a file in XML format.
     */
    @Test
    void writeFileXml() {
        model.fetchMovies();
        model.writeFile(false, Format.XML);
        File outputFile = new File("output.xml");
        assertTrue(outputFile.exists(), "Output file should exist after writing");
        outputFile.delete(); // Clean up
    }

    /**
     * Tests writing movies to a file in CSV format.
     */
    @Test
    void writeFileCsv() {
        model.fetchMovies();
        model.writeFile(false, Format.CSV);
        File outputFile = new File("output.csv");
        assertTrue(outputFile.exists(), "Output file should exist after writing");
        outputFile.delete(); // Clean up
    }

    /**
     * Tests searching movies by filter with title keyword.
     */
    @Test
    void searchByFilterTitleKeyword() {
        model.fetchMovies();
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "The");
        model.searchByFilter(filters);
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Should find movies with 'The' in title");
    }

    /**
     * Tests searching movies by filter with non-existent title keyword.
     */
    @Test
    void searchByFilterNonExistentTitleKeyword() {
        model.fetchMovies();
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "NonExistentMovieTitle123");
        model.searchByFilter(filters);
        List<Movie> results = model.getProcessedMovies();
        assertTrue(results.isEmpty(), "Should return empty list for non-existent title");
    }

    /**
     * Tests searching movies by filter with null filters.
     */
    @Test
    void searchByFilterNullFilters() {
        model.fetchMovies();
        model.searchByFilter(null);
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
        assertEquals(model.getMovies().size(), results.size(), "Should return all movies when filters are null");
    }

    /**
     * Tests searching movies by filter with empty filters.
     */
    @Test
    void searchByFilterEmptyFilters() {
        model.fetchMovies();
        model.searchByFilter(new HashMap<>());
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
        assertEquals(model.getMovies().size(), results.size(), "Should return all movies when filters are empty");
    }

    /**
     * Tests sorting the movie list by title ascending.
     */
    @Test
    void sortMovieListTitleAsc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.TITLE_ASC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByTitleAsc(sorted), "Movies should be sorted by title ascending");
    }

    /**
     * Tests sorting the movie list by title descending.
     */
    @Test
    void sortMovieListTitleDesc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.TITLE_DESC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByTitleDesc(sorted), "Movies should be sorted by title descending");
    }

    /**
     * Tests sorting the movie list by year ascending.
     */
    @Test
    void sortMovieListYearAsc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.YEAR_ASC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByYearAsc(sorted), "Movies should be sorted by year ascending");
    }

    /**
     * Tests sorting the movie list by year descending.
     */
    @Test
    void sortMovieListYearDesc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.YEAR_DESC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByYearDesc(sorted), "Movies should be sorted by year descending");
    }

    /**
     * Tests sorting the movie list by rating ascending.
     */
    @Test
    void sortMovieListRatingAsc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.RATING_ASC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByRatingAsc(sorted), "Movies should be sorted by rating ascending");
    }

    /**
     * Tests sorting the movie list by rating descending.
     */
    @Test
    void sortMovieListRatingDesc() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.RATING_DESC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
        assertTrue(isSortedByRatingDesc(sorted), "Movies should be sorted by rating descending");
    }

    /**
     * Tests setting the default movie sorter type.
     */
    @Test
    void setDefaultMovieSorterType() {
        model.setDefaultSortType(MovieSorterType.RATING_DESC);
        model.sortMovieList(MovieSorterType.RATING_DESC);
        assertNotNull(model.getProcessedMovies());
    }

    /**
     * Tests updating movie comments.
     */
    @Test
    void updateComments() {
        model.fetchMovies();
        Movie movie = model.getMovies().get(0);
        String comment = "Great movie!";
        model.updateComments(movie.getMovieId(), comment);
        assertTrue(movie.getComments().contains(comment));
    }

    /**
     * Tests updating comments for non-existent movie.
     */
    @Test
    void updateCommentsNonExistentMovie() {
        model.fetchMovies();
        model.updateComments(-1, "Test comment");
        // Should not throw exception
    }

    /**
     * Tests updating rating for non-existent movie.
     */
    @Test
    void updateRatingNonExistentMovie() {
        model.fetchMovies();
        model.updateRating(-1, 4.5);
        // Should not throw exception
    }

    // Helper methods for checking sort order
    private boolean isSortedByTitleAsc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getTitle().compareToIgnoreCase(movies.get(i + 1).getTitle()) > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByTitleDesc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getTitle().compareToIgnoreCase(movies.get(i + 1).getTitle()) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByYearAsc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getYear() > movies.get(i + 1).getYear()) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByYearDesc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getYear() < movies.get(i + 1).getYear()) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByRatingAsc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getRating() > movies.get(i + 1).getRating()) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedByRatingDesc(List<Movie> movies) {
        for (int i = 0; i < movies.size() - 1; i++) {
            if (movies.get(i).getRating() < movies.get(i + 1).getRating()) {
                return false;
            }
        }
        return true;
    }
}
