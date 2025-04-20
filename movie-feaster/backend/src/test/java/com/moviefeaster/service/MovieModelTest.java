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
        Movie movie = model.getMovies().get(0);
        Movie found = model.getMovieById(movie.getMovieId());
        System.out.println(movie);
        System.out.println(found);
        assertEquals(movie.getMovieId(), found.getMovieId());
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
     * Tests writing movies to a file.
     */
    @Test
    void writeFile() {
        model.fetchMovies();
        model.writeFile(false, Format.JSON);
        File outputFile = new File("output.json");
        assertTrue(outputFile.exists(), "Output file should exist after writing");
        outputFile.delete(); // Clean up
    }

    /**
     * Tests searching movies by filter.
     */
    @Test
    void searchByFilter() {
        model.fetchMovies();
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "The");
        model.searchByFilter(filters);
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
    }

    /**
     * Tests searching movies by filter with new filter flag.
     */
    @Test
    void searchByFilterWithNewFilterFlag() {
        model.fetchMovies();

        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "3");

        model.searchByFilter(filters);
        List<Movie> results = model.getProcessedMovies();

        // --------
        System.out.println("new");

        Map<MovieFilterType, Object> filters1 = new HashMap<>();
        model.searchByFilter(filters);
        List<Movie> results1 = model.getProcessedMovies();

        assertNotNull(results);
    }

    /**
     * Tests sorting the movie list.
     */
    @Test
    void sortMovieList() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.YEAR_DESC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
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
        model.updateComments(movie.getMovieId(), "Great movie!");
        assertTrue(movie.getComments().contains("Great movie!"));
    }

    /**
     * Tests updating movie ratings.
     */
    @Test
    void updateRating() {
        model.fetchMovies();
        Movie movie = model.getMovies().get(0);
        double initialAverage = movie.getInAppRating();
        model.updateRating(movie.getMovieId(), 4.5);
        assertTrue(movie.getInAppRating() > initialAverage || movie.getInAppRating() == 4.5);
    }
}
