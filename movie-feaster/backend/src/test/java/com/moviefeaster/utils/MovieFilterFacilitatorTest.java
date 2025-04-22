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
        Movie m1 = new Movie.Builder()
                .movieId(1)
                .title("The Matrix")
                .directors(List.of("Lana Wachowski", "Lilly Wachowski"))
                .year(1999)
                .rating(8.7)
                .genres(List.of(Genre.ACTION, Genre.SCIENCE_FICTION))
                .overview("A computer hacker learns the nature of reality.")
                .castings(List.of("Keanu Reeves", "Carrie-Anne Moss"))
                .imgUrl("https://image.tmdb.org/t/p/matrix.jpg")
                .build();

        Movie m2 = new Movie.Builder()
                .movieId(2)
                .title("Inception")
                .directors(List.of("Christopher Nolan"))
                .year(2010)
                .rating(8.8)
                .genres(List.of(Genre.SCIENCE_FICTION))
                .overview("A thief who steals corporate secrets through dream-sharing technology.")
                .castings(List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt"))
                .imgUrl("https://image.tmdb.org/t/p/inception.jpg")
                .build();

        Movie m3 = new Movie.Builder()
                .movieId(3)
                .title("Barbie")
                .directors(List.of("Greta Gerwig"))
                .year(2023)
                .rating(7.1)
                .genres(List.of(Genre.COMEDY, Genre.FAMILY))
                .overview("Barbie and Ken embark on a journey of self-discovery.")
                .castings(List.of("Margot Robbie", "Ryan Gosling"))
                .imgUrl("https://image.tmdb.org/t/p/barbie.jpg")
                .build();

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
     * Tests filtering with invalid year range length.
     */
    @Test
    void filterWithInvalidYearRangeLength() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        // Create a year range array with length != 2
        filters.put(MovieFilterType.YEAR_RANGE, new int[]{1999, 2010, 2023});

        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        // Should return the original list since the invalid filter would be ignored
        assertEquals(movies.size(), filtered.size());
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

    /**
     * Tests filtering with empty movie list.
     */
    @Test
    void filterWithEmptyMovieList() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "Matrix");
        
        List<Movie> emptyList = Collections.emptyList();
        List<Movie> filtered = MovieFilterFacilitator.filter(emptyList, filters);
        assertTrue(filtered.isEmpty());
    }

    /**
     * Tests filtering with empty filters map.
     */
    @Test
    void filterWithEmptyFilters() {
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, new HashMap<>());
        assertEquals(movies.size(), filtered.size());
    }

    /**
     * Tests filtering with case-insensitive title search.
     */
    @Test
    void filterWithCaseInsensitiveTitle() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "matrix");
        
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("The Matrix", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering with partial match in title.
     */
    @Test
    void filterWithPartialTitleMatch() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "The");
        
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(1, filtered.size());
        assertEquals("The Matrix", filtered.get(0).getTitle());
    }

    /**
     * Tests filtering with special characters in title.
     */
    @Test
    void filterWithSpecialCharacters() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.TITLE_KEYWORD, "!");
        
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertTrue(filtered.isEmpty());
    }

    /**
     * Tests filtering with non-existent genre.
     */
    @Test
    void filterWithNonExistentGenre() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.GENRE, "NON_EXISTENT");
        
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertTrue(filtered.isEmpty());
    }

    /**
     * Tests filtering with exact year range boundaries.
     */
    @Test
    void filterWithExactYearRangeBoundaries() {
        Map<MovieFilterType, Object> filters = new HashMap<>();
        filters.put(MovieFilterType.YEAR_RANGE, new int[]{1999, 2023});
        
        List<Movie> filtered = MovieFilterFacilitator.filter(movies, filters);
        assertEquals(3, filtered.size());
    }
}
