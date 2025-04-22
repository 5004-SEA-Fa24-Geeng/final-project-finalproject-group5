package com.moviefeaster.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MovieParser}.
 * Contains unit tests for the MovieSummary inner class.
 */
class MovieParserTest {

    @Test
    void testMovieSummarySettersAndGetters() {
        MovieParser.MovieSummary summary = new MovieParser.MovieSummary();
        
        summary.setMovieID(1);
        summary.setTitle("Test Movie");
        summary.setOverview("Test Overview");
        summary.setReleaseDate("2023-01-01");
        summary.setRating(8.5);
        summary.setGenreID(new HashSet<>(Arrays.asList(28, 12)));
        summary.setPosterPath("/test.jpg");

        assertEquals(1, summary.getMovieID());
        assertEquals("Test Movie", summary.getTitle());
        assertEquals("Test Overview", summary.getOverview());
        assertEquals("2023-01-01", summary.getReleaseDate());
        assertEquals(8.5, summary.getRating());
        assertEquals(2, summary.getGenreID().size());
        assertEquals("/test.jpg", summary.getPosterPath());
    }

    @Test
    void testMovieSummaryToString() {
        MovieParser.MovieSummary summary = new MovieParser.MovieSummary();
        summary.setMovieID(1);
        summary.setTitle("Test Movie");
        summary.setOverview("Test Overview");
        summary.setReleaseDate("2023-01-01");
        summary.setGenreID(new HashSet<>(Arrays.asList(28, 12)));
        summary.setPosterPath("/test.jpg");

        String expected = "MovieSummary{"
                + "movieID=1"
                + ", title='Test Movie'"
                + ", overview='Test Overview'"
                + ", releaseDate='2023-01-01'"
                + ", genreID=[28, 12]"
                + ", posterPath='/test.jpg'"
                + "}";
        
        assertEquals(expected, summary.toString());
    }
}
