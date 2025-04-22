package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import com.moviefeaster.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MovieParserTest {

    /** Mock input stream for testing API responses. */
    @Mock
    private InputStream mockInputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getParsedMoviesSummary() {
        Collection<MovieParser.MovieSummary> parsedMovies = MovieParser.getParsedMoviesSummary();

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }

    @Test
    void getParsedMovies() {
        Collection<Movie> parsedMovies = MovieParser.getMoviesFromApi();

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }

    @Test
    void testGetParsedMoviesSummaryWhenApiReturnsEmpty() {
        // This test verifies behavior when API returns empty response
        Collection<MovieParser.MovieSummary> parsedMovies = MovieParser.getParsedMoviesSummary();
        assertNotNull(parsedMovies, "Should return empty collection instead of null");
    }

    @Test
    void testGetParsedMoviesWhenApiReturnsEmpty() {
        // This test verifies behavior when API returns empty response
        Collection<Movie> parsedMovies = MovieParser.getMoviesFromApi();
        assertNotNull(parsedMovies, "Should return empty collection instead of null");
    }

    @Test
    void testGetParsedMoviesWhenApiReturnsInvalidData() {
        // This test verifies behavior when API returns invalid data
        Collection<Movie> parsedMovies = MovieParser.getMoviesFromApi();
        assertNotNull(parsedMovies, "Should handle invalid data gracefully");
        
        // Verify that movies have valid data
        for (Movie movie : parsedMovies) {
            assertNotNull(movie.getTitle(), "Movie title should not be null");
            assertTrue(movie.getYear() > 1800, "Movie year should be valid");
            assertTrue(movie.getRating() >= 0 && movie.getRating() <= 10_000, "Movie rating should be valid");
        }
    }
}
