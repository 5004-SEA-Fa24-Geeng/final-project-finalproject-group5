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
                "The Godfather",
                List.of("Francis Ford Coppola"),
                1999,
                8.5,
                List.of(Genre.ACTION, Genre.ANIMATION),
                "A computer hacker learns about the true nature of reality",
                List.of("Morgan Freeman"),
                ""
        );
        movie1.setComments(Arrays.asList("Great prison movie", "Classic film"));
        movie1.setInAppRating(Arrays.asList(5.0, 4.5));

        // Movie 2
        movie2 = new Movie(
                2,
                "Inception",
                List.of("Christopher Nolan"),
                2010,
                8.8,
                List.of(Genre.TV_MOVIE, Genre.THRILLER),
                "A thief who steals corporate secrets through the use of dream-sharing technology",
                List.of("Leonardo DiCaprio"),
                ""
        );
        movie2.setComments(Arrays.asList("Masterpiece of cinema", "Excellent performances"));
        movie2.setInAppRating(Arrays.asList(4.8, 4.9));

        // Movie 3
        movie3 = new Movie(
                3,
                "The Dark Knight",
                List.of("Christopher Nolan"),
                2008,
                9.0,
                List.of(Genre.ACTION, Genre.CRIME, Genre.COMEDY),
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham",
                List.of("Jeff Albertson", "National Guard", "Tracy L. Aldaz", "Tracy L.", "Matthew W>"),
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
        List<Movie> result = MovieFilter.filterByTitle(testMovies, "Godfather");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

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
        assertTrue(result.contains(movie1));

        // Test case insensitivity
        result = MovieFilter.filterByExactTitle(testMovies, "THE DARK KNIGHT");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test no match for partial title
        result = MovieFilter.filterByExactTitle(testMovies, "matrix");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by director.
     */
    @Test
    void filterByDirector() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByDirector(testMovies, "Nolan");
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));

        // Test case insensitivity
        result = MovieFilter.filterByDirector(testMovies, "COPPOLA");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test partial match
        result = MovieFilter.filterByDirector(testMovies, "Chris");
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
    }

    /**
     * Tests filtering movies by year.
     */
    @Test
    void filterByYear() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByYear(testMovies, 1999);
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
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test range containing one movie
        result = MovieFilter.filterByYearRange(testMovies, 2000, 2010);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
        assertTrue(result.contains(movie3));

        // Test range containing no movies
        result = MovieFilter.filterByYearRange(testMovies, 2011, 2020);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by minimum rating.
     */
    @Test
    void filterByMinRating() {
        // Test threshold that includes all movies
        List<Movie> result = MovieFilter.filterByMinRating(testMovies, 8.0f);
        assertEquals(3, result.size());

        // Test threshold that includes some movies
        result = MovieFilter.filterByMinRating(testMovies, 8.8f);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

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
        result = MovieFilter.filterByMaxRating(testMovies, 8.8f);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));

        // Test threshold that includes no movies
        result = MovieFilter.filterByMaxRating(testMovies, 8.4f);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by genre.
     */
    @Test
    void filterByGenre() {
        // Test genre present in all movies
        List<Movie> result = MovieFilter.filterByGenre(testMovies, "tv_movie");
        assertEquals(1, result.size());

        // Test genre present in some movies
        result = MovieFilter.filterByGenre(testMovies, "crime");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test genre present in one movie
        result = MovieFilter.filterByGenre(testMovies, "action");
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));

        // Test genre not present
        result = MovieFilter.filterByGenre(testMovies, "family");
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
        result = MovieFilter.filterByActor(testMovies, "DICAPRIO");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        // Test partial match
        result = MovieFilter.filterByActor(testMovies, "man");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test no match
        result = MovieFilter.filterByActor(testMovies, "Hathaway");
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
        List<Movie> actionMovies = MovieFilter.filterByGenre(testMovies, "action");
        List<Movie> crimeMovies = MovieFilter.filterByGenre(testMovies, "crime");

        List<Movie> result = MovieFilter.combineAnd(actionMovies, crimeMovies);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        // Test combining with no overlap
        List<Movie> recentMovies = MovieFilter.filterByYear(testMovies, 1999);
        List<Movie> oldMovies = MovieFilter.filterByYear(testMovies, 1972);

        result = MovieFilter.combineAnd(recentMovies, oldMovies);
        assertEquals(0, result.size());

        // Test with empty list
        result = MovieFilter.combineAnd(new ArrayList<>(), crimeMovies);
        assertEquals(0, result.size());
    }
}
