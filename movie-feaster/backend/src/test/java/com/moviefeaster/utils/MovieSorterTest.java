package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MovieSorter utility class.
 * Contains tests for various sorting methods of movies.
 */
class MovieSorterTest {
    
    /** List of test movies used in the test cases. */
    private List<Movie> testMovies;
    
    /** First test movie with medium year, high rating, and "S" title. */
    private Movie movie1;
    
    /** Second test movie, oldest with medium-high rating and "G" title. */
    private Movie movie2;
    
    /** Third test movie, recent with medium rating and "D" title. */
    private Movie movie3;
    
    /** Fourth test movie, newest with lowest rating and "I" title. */
    private Movie movie4;

    @BeforeEach
    void setUp() {
        // Create test movies
        testMovies = new ArrayList<>();
        // Movie 1 - Medium year, high rating, "S" title
        movie1 = new Movie.Builder()
                .movieId(1) // ID
                .title("The Shawshank Redemption")
                .directors(Arrays.asList("Frank Darabont"))
                .year(1994)
                .rating(9.3)
                .genres(Arrays.asList(Genre.DRAMA))
                .overview("Two imprisoned men bond over a number of years...")
                .castings(Arrays.asList("Tim Robbins", "Morgan Freeman"))
                .imgUrl("https://example.com/shawshank.jpg")
                .build();
        movie1.setInAppRating(Arrays.asList(5.0, 4.5)); // Average: 4.75

        movie2 = new Movie.Builder()
                .movieId(2) // ID
                .title("The Godfather")
                .directors(Arrays.asList("Francis Ford Coppola"))
                .year(1972)
                .rating(9.2)
                .genres(Arrays.asList(Genre.CRIME, Genre.DRAMA))
                .overview("The aging patriarch of an organized crime dynasty...")
                .castings(Arrays.asList("Marlon Brando", "Al Pacino"))
                .imgUrl("https://example.com/godfather.jpg")
                .build();
        movie2.setInAppRating(Arrays.asList(4.8, 4.9)); // Average: 4.85

        movie3 = new Movie.Builder()
                .movieId(3) // ID
                .title("The Dark Knight")
                .directors(Arrays.asList("Christopher Nolan"))
                .year(2008)
                .rating(9.0)
                .genres(Arrays.asList(Genre.ACTION, Genre.CRIME, Genre.DRAMA))
                .overview("When the menace known as the Joker wreaks havoc...")
                .castings(Arrays.asList("Christian Bale", "Heath Ledger"))
                .imgUrl("https://example.com/darkknight.jpg")
                .build();
        movie3.setInAppRating(Arrays.asList(4.7, 4.2)); // Average: 4.45

        movie4 = new Movie.Builder()
                .movieId(4) // ID
                .title("Inception")
                .directors(Arrays.asList("Christopher Nolan"))
                .year(2010)
                .rating(8.8)
                .genres(Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION))
                .overview("A thief who steals corporate secrets...")
                .castings(Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt"))
                .imgUrl("https://example.com/inception.jpg")
                .build();
        movie4.setInAppRating(Arrays.asList(4.0, 4.2)); // Average: 4.1

        testMovies.add(movie1);
        testMovies.add(movie2);
        testMovies.add(movie3);
        testMovies.add(movie4);
    }

    @Test
    void sortByTitle() {
        // Sort by title (A-Z)
        List<Movie> sorted = MovieSorter.sortByTitle(testMovies);

        // Expected order: The Dark Knight, The Godfather, Inception, The Shawshank Redemption
        assertEquals(4, sorted.size());
        assertEquals("Inception", sorted.get(0).getTitle());
        assertEquals("The Dark Knight", sorted.get(1).getTitle());
        assertEquals("The Godfather", sorted.get(2).getTitle());
        assertEquals("The Shawshank Redemption", sorted.get(3).getTitle());
    }

    @Test
    void sortByTitleDescending() {
        // Sort by title (Z-A)
        List<Movie> sorted = MovieSorter.sortByTitleDescending(testMovies);

        // Expected order: The Shawshank Redemption, The Godfather, The Dark Knight, Inception
        assertEquals(4, sorted.size());
        assertEquals("The Shawshank Redemption", sorted.get(0).getTitle());
        assertEquals("The Godfather", sorted.get(1).getTitle());
        assertEquals("The Dark Knight", sorted.get(2).getTitle());
        assertEquals("Inception", sorted.get(3).getTitle());
    }

    @Test
    void sortByRating() {
        // Sort by rating (high to low)
        List<Movie> sorted = MovieSorter.sortByRating(testMovies);

        // Expected order: Shawshank (9.3), Godfather (9.2), Dark Knight (9.0), Inception (8.8)
        assertEquals(4, sorted.size());
        assertEquals(9.3, sorted.get(0).getRating(), 0.01);
        assertEquals(9.2, sorted.get(1).getRating(), 0.01);
        assertEquals(9.0, sorted.get(2).getRating(), 0.01);
        assertEquals(8.8, sorted.get(3).getRating(), 0.01);
    }

    @Test
    void sortByRatingAscending() {
        // Sort by rating (low to high)
        List<Movie> sorted = MovieSorter.sortByRatingAscending(testMovies);

        // Expected order: Inception (8.8), Dark Knight (9.0), Godfather (9.2), Shawshank (9.3)
        assertEquals(4, sorted.size());
        assertEquals(8.8, sorted.get(0).getRating(), 0.01);
        assertEquals(9.0, sorted.get(1).getRating(), 0.01);
        assertEquals(9.2, sorted.get(2).getRating(), 0.01);
        assertEquals(9.3, sorted.get(3).getRating(), 0.01);
    }

    @Test
    void sortByYear() {
        // Sort by year (newest to oldest)
        List<Movie> sorted = MovieSorter.sortByYear(testMovies);

        // Expected order: Inception (2010), Dark Knight (2008), Shawshank (1994), Godfather (1972)
        assertEquals(4, sorted.size());
        assertEquals(2010, sorted.get(0).getYear());
        assertEquals(2008, sorted.get(1).getYear());
        assertEquals(1994, sorted.get(2).getYear());
        assertEquals(1972, sorted.get(3).getYear());
    }

    @Test
    void sortByYearAscending() {
        // Sort by year (oldest to newest)
        List<Movie> sorted = MovieSorter.sortByYearAscending(testMovies);

        // Expected order: Godfather (1972), Shawshank (1994), Dark Knight (2008), Inception (2010)
        assertEquals(4, sorted.size());
        assertEquals(1972, sorted.get(0).getYear());
        assertEquals(1994, sorted.get(1).getYear());
        assertEquals(2008, sorted.get(2).getYear());
        assertEquals(2010, sorted.get(3).getYear());
    }

    @Test
    void sortByInAppRating() {
        // Sort by in-app rating (high to low)
        List<Movie> sorted = MovieSorter.sortByInAppRating(testMovies);

        // Expected order: Godfather (4.85), Shawshank (4.75), Dark Knight (4.45), Inception (4.1)
        assertEquals(4, sorted.size());
        assertEquals("The Godfather", sorted.get(0).getTitle()); // 4.85
        assertEquals("The Shawshank Redemption", sorted.get(1).getTitle()); // 4.75
        assertEquals("The Dark Knight", sorted.get(2).getTitle()); // 4.45
        assertEquals("Inception", sorted.get(3).getTitle()); // 4.1
    }

    @Test
    void sortByInAppRatingAscending() {
        // Sort by in-app rating (low to high)
        List<Movie> sorted = MovieSorter.sortByInAppRatingAscending(testMovies);

        // Expected order: Inception (4.1), Dark Knight (4.45), Shawshank (4.75), Godfather (4.85)
        assertEquals(4, sorted.size());
        assertEquals("Inception", sorted.get(0).getTitle()); // 4.1
        assertEquals("The Dark Knight", sorted.get(1).getTitle()); // 4.45
        assertEquals("The Shawshank Redemption", sorted.get(2).getTitle()); // 4.75
        assertEquals("The Godfather", sorted.get(3).getTitle()); // 4.85
    }

    @Test
    void getTopN() {
        // Get top 2 from a sorted list (by rating)
        List<Movie> sorted = MovieSorter.sortByRating(testMovies);
        List<Movie> topTwo = MovieSorter.getTopN(sorted, 2);

        assertEquals(2, topTwo.size());
        assertEquals("The Shawshank Redemption", topTwo.get(0).getTitle());
        assertEquals("The Godfather", topTwo.get(1).getTitle());

        // Request more than available
        List<Movie> topTen = MovieSorter.getTopN(sorted, 10);
        assertEquals(4, topTen.size()); // Should return all movies

        // Test with n=0
        List<Movie> topZero = MovieSorter.getTopN(sorted, 0);
        assertEquals(0, topZero.size());

        // Test with n<0
        List<Movie> topNegative = MovieSorter.getTopN(sorted, -1);
        assertEquals(0, topNegative.size());

        // Test with null input
        List<Movie> nullList = MovieSorter.getTopN(null, 2);
        assertEquals(0, nullList.size());
    }

    @Test
    void edgeCases() {
        // Test empty list
        List<Movie> emptyList = new ArrayList<>();
        assertTrue(MovieSorter.sortByTitle(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByTitleDescending(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByRating(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByRatingAscending(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByYear(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByYearAscending(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByInAppRating(emptyList).isEmpty());
        assertTrue(MovieSorter.sortByInAppRatingAscending(emptyList).isEmpty());
        assertTrue(MovieSorter.getTopN(emptyList, 2).isEmpty());

        // Test null list
        assertTrue(MovieSorter.sortByTitle(null).isEmpty());
        assertTrue(MovieSorter.sortByTitleDescending(null).isEmpty());
        assertTrue(MovieSorter.sortByRating(null).isEmpty());
        assertTrue(MovieSorter.sortByRatingAscending(null).isEmpty());
        assertTrue(MovieSorter.sortByYear(null).isEmpty());
        assertTrue(MovieSorter.sortByYearAscending(null).isEmpty());
        assertTrue(MovieSorter.sortByInAppRating(null).isEmpty());
        assertTrue(MovieSorter.sortByInAppRatingAscending(null).isEmpty());
        assertTrue(MovieSorter.getTopN(null, 2).isEmpty());

        // Test single movie list
        List<Movie> singleMovieList = new ArrayList<>();
        singleMovieList.add(movie1);
        
        List<Movie> sortedSingle = MovieSorter.sortByTitle(singleMovieList);
        assertEquals(1, sortedSingle.size());
        assertEquals("The Shawshank Redemption", sortedSingle.get(0).getTitle());
        
        sortedSingle = MovieSorter.sortByRating(singleMovieList);
        assertEquals(1, sortedSingle.size());
        assertEquals(9.3, sortedSingle.get(0).getRating(), 0.01);
        
        sortedSingle = MovieSorter.sortByYear(singleMovieList);
        assertEquals(1, sortedSingle.size());
        assertEquals(1994, sortedSingle.get(0).getYear());
        
        sortedSingle = MovieSorter.sortByInAppRating(singleMovieList);
        assertEquals(1, sortedSingle.size());
        assertEquals("The Shawshank Redemption", sortedSingle.get(0).getTitle());
        
        List<Movie> topN = MovieSorter.getTopN(singleMovieList, 1);
        assertEquals(1, topN.size());
        assertEquals("The Shawshank Redemption", topN.get(0).getTitle());
        
        // Test with movie having null fields
        Movie nullFieldMovie = new Movie.Builder()
                .movieId(5)
                .title(null)
                .directors(null)
                .year(0)
                .rating(0.0)
                .genres(null)
                .overview(null)
                .castings(null)
                .imgUrl(null)
                .build();
        nullFieldMovie.setInAppRating(null);
        
        List<Movie> nullFieldList = new ArrayList<>();
        nullFieldList.add(nullFieldMovie);
        
        // These should not throw exceptions
        assertDoesNotThrow(() -> MovieSorter.sortByTitle(nullFieldList));
        assertDoesNotThrow(() -> MovieSorter.sortByRating(nullFieldList));
        assertDoesNotThrow(() -> MovieSorter.sortByYear(nullFieldList));
        assertDoesNotThrow(() -> MovieSorter.sortByInAppRating(nullFieldList));
    }
}
