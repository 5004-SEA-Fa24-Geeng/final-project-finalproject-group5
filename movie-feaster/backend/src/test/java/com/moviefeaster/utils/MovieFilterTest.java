package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieFilter utility.
 */
class MovieFilterTest {

    /** List of test movies. */
    private List<Movie> testMovies;
    /** First test movie. */
    private Movie movie1;
    /** Second test movie. */
    private Movie movie2;
    /** Third test movie. */
    private Movie movie3;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        testMovies = new ArrayList<>();

        // Movie 1
        movie1 = new Movie(
                1,
                "The Matrix",
                Collections.emptyList(),
                1999,
                8.7,
                Collections.emptyList(),
                "A computer hacker learns about the true nature of reality",
                Collections.emptyList(),
                ""
        );
        movie1.setComments(Arrays.asList("Great prison movie", "Classic film"));
        movie1.setInAppRating(Arrays.asList(5.0, 4.5));

        // Movie 2
        movie2 = new Movie(
                2,
                "Inception",
                Collections.emptyList(),
                2010,
                8.8,
                Collections.emptyList(),
                "A thief who steals corporate secrets through the use of dream-sharing technology",
                Collections.emptyList(),
                ""
        );
        movie2.setComments(Arrays.asList("Masterpiece of cinema", "Excellent performances"));
        movie2.setInAppRating(Arrays.asList(4.8, 4.9));

        // Movie 3
        movie3 = new Movie(
                3,
                "The Dark Knight",
                Collections.emptyList(),
                2008,
                9.0,
                Collections.emptyList(),
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham",
                Collections.emptyList(),
                ""
        );
        movie3.setComments(Arrays.asList("Best superhero movie", "Heath Ledger's Joker is amazing"));
        movie3.setInAppRating(Arrays.asList(4.7, 4.2));

        testMovies.add(movie1);
        testMovies.add(movie2);
        testMovies.add(movie3);
    }

    /**
     * Tests filtering movies by title.
     */
    @Test
    void filterByTitle() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByTitle(testMovies, "godfather");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test case insensitivity
        result = MovieFilter.filterByTitle(testMovies, "DARK");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));
    }

    /**
     * Tests filtering movies by exact title.
     */
    @Test
    void filterByExactTitle() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByExactTitle(testMovies, "The Godfather");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test case insensitivity
        result = MovieFilter.filterByExactTitle(testMovies, "THE DARK KNIGHT");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test no match for partial title
        result = MovieFilter.filterByExactTitle(testMovies, "Godfather");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by director.
     */
    @Test
    void filterByDirector() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByDirector(testMovies, "Nolan");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test case insensitivity
        result = MovieFilter.filterByDirector(testMovies, "COPPOLA");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test partial match
        result = MovieFilter.filterByDirector(testMovies, "Fra");
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
    }

    /**
     * Tests filtering movies by year.
     */
    @Test
    void filterByYear() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByYear(testMovies, 1994);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test no match
        result = MovieFilter.filterByYear(testMovies, 2000);
        assertEquals(0, result.size());

        // Test null input
        result = MovieFilter.filterByYear(null, 1994);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by year range.
     */
    @Test
    void filterByYearRange() {
        // Test range containing multiple movies
        List<Movie> result = MovieFilter.filterByYearRange(testMovies, 1970, 2000);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));

        // Test range containing one movie
        result = MovieFilter.filterByYearRange(testMovies, 2000, 2010);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test range containing no movies
        result = MovieFilter.filterByYearRange(testMovies, 2010, 2020);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by minimum rating.
     */
    @Test
    void filterByMinRating() {
        // Test threshold that includes all movies
        List<Movie> result = MovieFilter.filterByMinRating(testMovies, 9.0f);
        assertEquals(3, result.size());

        // Test threshold that includes some movies
        result = MovieFilter.filterByMinRating(testMovies, 9.1f);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));

        // Test threshold that includes no movies
        result = MovieFilter.filterByMinRating(testMovies, 9.4f);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by maximum rating.
     */
    @Test
    void filterByMaxRating() {
        // Test threshold that includes all movies
        List<Movie> result = MovieFilter.filterByMaxRating(testMovies, 9.5f);
        assertEquals(3, result.size());

        // Test threshold that includes some movies
        result = MovieFilter.filterByMaxRating(testMovies, 9.1f);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test threshold that includes no movies
        result = MovieFilter.filterByMaxRating(testMovies, 8.9f);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by genre.
     */
    @Test
    void filterByGenre() {
        // Test genre present in all movies
        List<Movie> result = MovieFilter.filterByGenre(testMovies, "drama");
        assertEquals(3, result.size());

        // Test genre present in some movies
        result = MovieFilter.filterByGenre(testMovies, "crime");
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
        assertTrue(result.contains(movie3));

        // Test genre present in one movie
        result = MovieFilter.filterByGenre(testMovies, "action");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test genre not present
        result = MovieFilter.filterByGenre(testMovies, "comedy");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by actor.
     */
    @Test
    void filterByActor() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByActor(testMovies, "Freeman");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test case insensitivity
        result = MovieFilter.filterByActor(testMovies, "PACINO");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test partial match
        result = MovieFilter.filterByActor(testMovies, "Heath");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test no match
        result = MovieFilter.filterByActor(testMovies, "DiCaprio");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by comment keyword.
     */
    @Test
    void filterByCommentKeyword() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByCommentKeyword(testMovies, "prison");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test case insensitivity
        result = MovieFilter.filterByCommentKeyword(testMovies, "MASTERPIECE");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test no match
        result = MovieFilter.filterByCommentKeyword(testMovies, "terrible");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by minimum in-app rating.
     */
    @Test
    void filterByMinInAppRating() {
        // Test threshold that includes all movies
        List<Movie> result = MovieFilter.filterByMinInAppRating(testMovies, 4.0);
        assertEquals(3, result.size());

        // Test threshold that includes some movies
        result = MovieFilter.filterByMinInAppRating(testMovies, 4.8);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test threshold that includes no movies
        result = MovieFilter.filterByMinInAppRating(testMovies, 5.0);
        assertEquals(0, result.size());
    }

    /**
     * Tests combining filters with AND logic.
     */
    @Test
    void combineAnd() {
        // Test combining two filters with overlapping results
        List<Movie> dramaMovies = MovieFilter.filterByGenre(testMovies, "drama");
        List<Movie> crimeMovies = MovieFilter.filterByGenre(testMovies, "crime");

        List<Movie> result = MovieFilter.combineAnd(dramaMovies, crimeMovies);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
        assertTrue(result.contains(movie3));

        // Test combining with no overlap
        List<Movie> recentMovies = MovieFilter.filterByYear(testMovies, 2008);
        List<Movie> oldMovies = MovieFilter.filterByYear(testMovies, 1972);

        result = MovieFilter.combineAnd(recentMovies, oldMovies);
        assertEquals(0, result.size());

        // Test with empty list
        result = MovieFilter.combineAnd(new ArrayList<>(), crimeMovies);
        assertEquals(0, result.size());
    }
}
