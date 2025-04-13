package com.moviefeaster.Service;

import com.moviefeaster.Model.Format;
import com.moviefeaster.Model.Movie;
import com.moviefeaster.Model.MovieSorterType;
import com.moviefeaster.Model.MovieFilterType;
import com.moviefeaster.Utils.MovieFilterFacilitator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MovieModelTest {

    private MovieModel model;

    @BeforeEach
    void setUp() {
        model = new MovieModel();
    }

    @Test
    void fetchMovies() {
        model.fetchMovies();
        List<Movie> movies = model.getMovies();
        assertNotNull(movies);
        assertFalse(movies.isEmpty(), "Movies list should not be empty after fetching");
    }

    @Test
    void getMovies() {
        List<Movie> movies = model.getMovies();
        assertNotNull(movies);
    }

    @Test
    void getMovieById() {
        Movie movie = model.getMovies().get(0);
        Movie found = model.getMovieById(movie.getId());
        assertEquals(movie.getId(), found.getId());
    }

    @Test
    void getProcessedMovies() {
        List<Movie> processed = model.getProcessedMovies();
        assertNotNull(processed);
    }

    @Test
    void writeFile() {
        model.fetchMovies();
        model.writeFile(false, Format.JSON);
        File outputFile = new File("output.json");
        assertTrue(outputFile.exists(), "Output file should exist after writing");
        outputFile.delete(); // Clean up
    }

    @Test
    void searchByFilter() {
        model.fetchMovies();
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "The");
        model.searchByFilter(filters);
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
    }

    @Test
    void testSearchByFilter_withNewFilterFlag() {
        model.fetchMovies();
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "The");
        model.searchByFilter(true, filters);
        List<Movie> results = model.getProcessedMovies();
        assertNotNull(results);
    }

    @Test
    void sortMovieList() {
        model.fetchMovies();
        model.sortMovieList(MovieSorterType.YEAR_DESC);
        List<Movie> sorted = model.getProcessedMovies();
        assertNotNull(sorted);
    }

    @Test
    void setDefaultMovieSorterType() {
        model.setDefaultMovieSorterType(MovieSorterType.RATING_DESC);
        model.sortMovieList(MovieSorterType.RATING_DESC);
        assertNotNull(model.getProcessedMovies());
    }

    @Test
    void updateComments() {
        model.fetchMovies();
        Movie movie = model.getMovies().get(0);
        model.UpdateComments(movie.getId(), "Great movie!");
        assertTrue(movie.getComments().contains("Great movie!"));
    }

    @Test
    void updateRating() {
        model.fetchMovies();
        Movie movie = model.getMovies().get(0);
        double initialAverage = movie.getInAppRating();
        model.UpdateRating(movie.getId(), 4.5);
        assertTrue(movie.getInAppRating() > initialAverage || movie.getInAppRating() == 4.5);
    }
}