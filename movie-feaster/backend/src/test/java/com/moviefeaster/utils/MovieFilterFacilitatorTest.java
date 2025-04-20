package com.moviefeaster.utils;

import com.moviefeaster.model.Genre;
import com.moviefeaster.model.Movie;
import com.moviefeaster.model.MovieFilterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieFilterFacilitator utility.
 */
class MovieFilterFacilitatorTest {

    /** List of test movies. */
    private List<Movie> movies;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        Movie m1 = new Movie(
                1,
                "The Matrix",
                List.of("Lana Wachowski", "Lilly Wachowski"),
                1999,
                8.7,
                List.of(Genre.ACTION, Genre.SCIENCE_FICTION),
                "A computer hacker learns the nature of reality.",
                List.of("Keanu Reeves", "Carrie-Anne Moss"),
                "https://image.tmdb.org/t/p/matrix.jpg"
        );

        Movie m2 = new Movie(
                2,
                "Inception",
                List.of("Christopher Nolan"),
                2010,
                8.8,
                List.of(Genre.SCIENCE_FICTION),
                "A thief who steals corporate secrets through dream-sharing technology.",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"),
                "https://image.tmdb.org/t/p/inception.jpg"
        );

        Movie m3 = new Movie(
                3,
                "Barbie",
                List.of("Greta Gerwig"),
                2023,
                7.1,
                List.of(Genre.COMEDY, Genre.FAMILY),
                "Barbie and Ken embark on a journey of self-discovery.",
                List.of("Margot Robbie", "Ryan Gosling"),
                "https://image.tmdb.org/t/p/barbie.jpg"
        );

        // Add comments or ratings manually to test those filters
        m3.addComment("Very pink!");
        m2.addInAppRating(4.0);
        m3.addInAppRating(5.0);

        movies = List.of(m1, m2, m3);
    }

    /**
     * Tests filtering movies by title keyword.
     */
    @Test
    void filterByTitleKeyword() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "Matrix");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("The Matrix", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by exact title.
     */
    @Test
    void filterByExactTitle() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.EXACT_TITLE, "Inception");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("Inception", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by director.
     */
    @Test
    void filterByDirector() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.DIRECTOR, "Christopher Nolan");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("Inception", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by actor.
     */
    @Test
    void filterByActor() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.ACTOR, "Keanu Reeves");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("The Matrix", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by year.
     */
    @Test
    void filterByYear() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.YEAR, 1999);

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("The Matrix", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by year range.
     */
    @Test
    void filterByYearRange() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.YEAR_RANGE, new int[]{2000, 2025});

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(2, filtered.size());
    }

    /**
     * Tests filtering movies by genre.
     */
    @Test
    void filterByGenre() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.GENRE, "COMEDY");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("Barbie", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by minimum rating.
     */
    @Test
    void filterByMinRating() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.MIN_RATING, 8.0);

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(2, filtered.size());
    }

    /**
     * Tests filtering movies by maximum rating.
     */
    @Test
    void filterByMaxRating() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.MAX_RATING, 7.5);

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("Barbie", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering movies by comment keyword with no matches.
     */
    @Test
    void filterByCommentKeywordComments() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.COMMENT_KEYWORD, "awesome");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(0, filtered.size());
    }

    /**
     * Tests filtering movies by comment keyword with matches.
     */
    @Test
    void filterByCommentKeywordMatchComments() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.COMMENT_KEYWORD, "Very pink!");

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
    }

    /**
     * Tests filtering movies by minimum in-app rating when some movies have no ratings.
     */
    @Test
    void filterByMinInAppRatingNoRatingsYet() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.MIN_INAPP_RATING, 4.0);

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(2, filtered.size());
    }
}
