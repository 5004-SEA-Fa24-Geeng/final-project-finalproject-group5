package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieFilter utility.
 */
class MovieFilterTest {

    /**
     * List of test movies.
     */
    private List<Movie> testMovies;
    /**
     * First test movie.
     */
    private Movie movie1;
    /**
     * Second test movie.
     */
    private Movie movie2;
    /**
     * Third test movie.
     */
    private Movie movie3;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        testMovies = new ArrayList<>();

        movie1 = new Movie.Builder()
                .movieId(1)
                .title("The Godfather")
                .directors(List.of("Francis Ford Coppola"))
                .year(1999)
                .rating(8.5)
                .genres(List.of(Genre.ACTION, Genre.ANIMATION))
                .overview("A computer hacker learns about the true nature of reality")
                .castings(List.of("Morgan Freeman"))
                .imgUrl("")
                .build();
        movie1.setComments(Arrays.asList("Great prison movie", "Classic film"));
        movie1.setInAppRating(Arrays.asList(5.0, 4.5));

        movie2 = new Movie.Builder()
                .movieId(2)
                .title("Inception")
                .directors(List.of("Christopher Nolan"))
                .year(2010)
                .rating(8.8)
                .genres(List.of(Genre.TV_MOVIE, Genre.THRILLER))
                .overview("A thief who steals corporate secrets through the use of dream-sharing technology")
                .castings(List.of("Leonardo DiCaprio"))
                .imgUrl("")
                .build();
        movie2.setComments(Arrays.asList("Masterpiece of cinema", "Excellent performances"));
        movie2.setInAppRating(Arrays.asList(4.8, 4.9));

        movie3 = new Movie.Builder()
                .movieId(3)
                .title("The Dark Knight")
                .directors(List.of("Christopher Nolan"))
                .year(2008)
                .rating(9.0)
                .genres(List.of(Genre.ACTION, Genre.CRIME, Genre.COMEDY))
                .overview("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham")
                .castings(List.of("Jeff Albertson", "National Guard", "Tracy L. Aldaz", "Tracy L.", "Matthew W>"))
                .imgUrl("")
                .build();
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
        assertTrue(result.contains(movie1)); // ← was movie2

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
        assertTrue(result.contains(movie1)); // ← was movie2

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
        assertEquals(2, result.size());
        assertTrue(result.contains(movie2));
        assertTrue(result.contains(movie3));

        // Test case insensitivity
        result = MovieFilter.filterByDirector(testMovies, "COPPOLA");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test partial match
        result = MovieFilter.filterByDirector(testMovies, "Fra");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));
    }

    /**
     * Tests filtering movies by year.
     */
    @Test
    void filterByYear() {
        List<Movie> result = MovieFilter.filterByYear(testMovies, 1999);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        result = MovieFilter.filterByYear(testMovies, 2000);
        assertEquals(0, result.size());

        result = MovieFilter.filterByYear(null, 1999);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by minimum rating.
     */
    @Test
    void filterByMinRating() {
        List<Movie> result = MovieFilter.filterByMinRating(testMovies, 8.5f);
        assertEquals(3, result.size());

        result = MovieFilter.filterByMinRating(testMovies, 8.9f);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        result = MovieFilter.filterByMinRating(testMovies, 9.5f);
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by actor.
     */
    @Test
    void filterByActor() {
        List<Movie> result = MovieFilter.filterByActor(testMovies, "Freeman");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        result = MovieFilter.filterByActor(testMovies, "DiCaprio");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByActor(testMovies, "Heath");
        assertEquals(0, result.size()); // no Heath Ledger

        result = MovieFilter.filterByActor(testMovies, "Tracy");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));
    }

    /**
     * Tests filtering movies by genre.
     */
    @Test
    void filterByGenre() {
        List<Movie> result = MovieFilter.filterByGenre(testMovies, "animation");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        result = MovieFilter.filterByGenre(testMovies, "thriller");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByGenre(testMovies, "comedy");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie3));

        result = MovieFilter.filterByGenre(testMovies, "horror");
        assertEquals(0, result.size());
    }

    /**
     * Tests filtering movies by comment keyword.
     */
    @Test
    void filterByCommentKeyword() {
        List<Movie> result = MovieFilter.filterByCommentKeyword(testMovies, "prison");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        result = MovieFilter.filterByCommentKeyword(testMovies, "MASTERPIECE");
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByCommentKeyword(testMovies, "terrible");
        assertEquals(0, result.size());
    }
}
