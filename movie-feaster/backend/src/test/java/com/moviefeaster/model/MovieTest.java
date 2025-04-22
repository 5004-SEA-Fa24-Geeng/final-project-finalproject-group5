package com.moviefeaster.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Movie} class.
 *
 * This test suite verifies the functionality of the Movie class, focusing on:
 * - Setter methods and their validation logic
 * - Builder pattern implementation and validation rules
 * - Default value handling for null or invalid inputs
 * - Edge cases for all validation criteria
 *
 * @author Your Name
 * @version 1.0
 * @see Movie
 */
public class MovieTest {

    /** Movie instance used across test methods. */
    private Movie movie;

    /**
     * Set up method executed before each test.
     * Creates a standard Movie instance with predefined values using the Builder pattern.
     * This provides a consistent starting point for each test method.
     */
    @BeforeEach
    public void setUp() {
        // Create a basic movie using the Builder pattern before each test
        movie = new Movie.Builder()
                .movieId(1)
                .title("Test Movie")
                .directors(Arrays.asList("Director One", "Director Two"))
                .year(2023)
                .rating(8.5)
                .genres(Arrays.asList(Genre.ACTION, Genre.COMEDY))
                .overview("This is a test movie overview")
                .castings(Arrays.asList("Actor One", "Actor Two"))
                .imgUrl("http://example.com/poster.jpg")
                .build();
    }

    /**
     * Tests the {@link Movie#setMovieId(int)} method.
     * Verifies that the movieId is correctly set.
     */
    @Test
    public void testSetMovieId() {
        // Set a new movie ID
        movie.setMovieId(2);
        assertEquals(2, movie.getMovieId());
    }

    /**
     * Tests the {@link Movie#setTitle(String)} method.
     * Verifies:
     * - Valid titles are properly set
     * - Null titles are rejected (current value maintained)
     * - Blank titles are rejected (current value maintained)
     */
    @Test
    public void testSetTitle() {
        // Set a valid title
        movie.setTitle("New Title");
        assertEquals("New Title", movie.getTitle());

        // Set a null title (should not change the current title)
        movie.setTitle(null);
        assertEquals("New Title", movie.getTitle());

        // Set a blank title (should not change the current title)
        movie.setTitle("  ");
        assertEquals("New Title", movie.getTitle());
    }

    /**
     * Tests the {@link Movie#setDirectors(List)} method.
     * Verifies:
     * - Valid director lists are properly set
     * - Null lists are replaced with empty lists
     */
    @Test
    public void testSetDirectors() {
        List<String> newDirectors = Arrays.asList("New Director One", "New Director Two");
        movie.setDirectors(newDirectors);
        assertEquals(newDirectors, movie.getDirectors());

        // Test with null (should set an empty list)
        movie.setDirectors(null);
        assertNotNull(movie.getDirectors());
        assertTrue(movie.getDirectors().isEmpty());
    }

    /**
     * Tests the {@link Movie#setYear(int)} method.
     * Verifies:
     * - Valid years (> 1800) are properly set
     * - Invalid years (≤ 1800) are rejected (current value maintained)
     */
    @Test
    public void testSetYear() {
        // Set a valid year
        movie.setYear(2024);
        assertEquals(2024, movie.getYear());

        // Set an invalid year (before 1800)
        movie.setYear(1799);
        // Should not change from previous value
        assertEquals(2024, movie.getYear());
    }

    /**
     * Tests the {@link Movie#setRating(double)} method.
     * Verifies:
     * - Valid ratings (between 0.0 and 10.0) are properly set
     * - Boundary values (0.0 and 10.0) are accepted
     * - Invalid ratings (negative or > 10.0) are rejected (current value maintained)
     *
     * Note: The setter method uses different validation criteria (0.0-10.0) than
     * the constructor's validation (0.0-10000.0).
     */
    @Test
    public void testSetRating() {
        // Set a valid rating
        movie.setRating(9.5);
        assertEquals(9.5, movie.getRating());

        // Set a boundary rating (0.0)
        movie.setRating(0.0);
        assertEquals(0.0, movie.getRating());

        // Set a boundary rating (10.0)
        movie.setRating(10.0);
        assertEquals(10.0, movie.getRating());

        // Set an invalid rating (negative)
        movie.setRating(-1.0);
        // Should not change from previous value
        assertEquals(10.0, movie.getRating());

        // Set an invalid rating (above 10.0)
        movie.setRating(11.0);
        // Should not change from previous value
        assertEquals(10.0, movie.getRating());
    }

    /**
     * Tests the {@link Movie#setGenres(List)} method.
     * Verifies:
     * - Valid genre lists are properly set
     * - Null lists are replaced with empty lists
     */
    @Test
    public void testSetGenres() {
        List<Genre> newGenres = Arrays.asList(Genre.HORROR, Genre.THRILLER);
        movie.setGenres(newGenres);
        assertEquals(newGenres, movie.getGenres());

        // Test with null (should set an empty list)
        movie.setGenres(null);
        assertNotNull(movie.getGenres());
        assertTrue(movie.getGenres().isEmpty());
    }

    /**
     * Tests the {@link Movie#setOverview(String)} method.
     * Verifies:
     * - Valid overview strings are properly set
     * - Null overviews are accepted but not validated (unlike the constructor)
     * - Empty overviews are accepted but not validated (unlike the constructor)
     *
     * Note: This test highlights an inconsistency between the constructor
     * (which validates for null/blank) and the setter (which doesn't).
     */
    @Test
    public void testSetOverview() {
        // Set a valid overview
        movie.setOverview("New overview text");
        assertEquals("New overview text", movie.getOverview());

        // Set a null overview - Note: This setter doesn't validate null or blank
        movie.setOverview(null);
        assertNull(movie.getOverview());

        // Reset to a valid value for further tests
        movie.setOverview("Reset overview");

        // Set an empty overview - Note: This setter doesn't validate null or blank
        movie.setOverview("");
        assertEquals("", movie.getOverview());
    }

    /**
     * Tests the {@link Movie#setCastings(List)} method.
     * Verifies:
     * - Valid casting lists are properly set
     * - Null lists are replaced with empty lists
     */
    @Test
    public void testSetCastings() {
        List<String> newCastings = Arrays.asList("New Actor One", "New Actor Two");
        movie.setCastings(newCastings);
        assertEquals(newCastings, movie.getCastings());

        // Test with null (should set an empty list)
        movie.setCastings(null);
        assertNotNull(movie.getCastings());
        assertTrue(movie.getCastings().isEmpty());
    }

    /**
     * Tests the {@link Movie#setComments(List)} method.
     * Verifies:
     * - Valid comment lists are properly set
     * - Null lists are replaced with empty lists
     */
    @Test
    public void testSetComments() {
        List<String> testComments = Arrays.asList("Great movie!", "Would watch again");
        movie.setComments(testComments);
        assertEquals(testComments, movie.getComments());

        // Test with null (should set an empty list)
        movie.setComments(null);
        assertNotNull(movie.getComments());
        assertTrue(movie.getComments().isEmpty());
    }

    /**
     * Tests the {@link Movie#addComment(String)} method.
     * Verifies that comments are successfully added to the comments list.
     */
    @Test
    public void testAddComment() {
        // Start with empty comments
        movie.setComments(new ArrayList<>());

        // Add a comment
        movie.addComment("First comment");
        assertEquals(1, movie.getComments().size());
        assertEquals("First comment", movie.getComments().get(0));

        // Add another comment
        movie.addComment("Second comment");
        assertEquals(2, movie.getComments().size());
        assertEquals("Second comment", movie.getComments().get(1));
    }

    /**
     * Tests the {@link Movie#setInAppRating(List)} method.
     * Verifies:
     * - Valid rating lists are properly set
     * - Null lists are replaced with empty lists
     * - The average calculation in {@link Movie#getInAppRating()} works correctly
     */
    @Test
    public void testSetInAppRating() {
        List<Double> testRatings = Arrays.asList(8.0, 9.0, 7.5);
        movie.setInAppRating(testRatings);

        // Calculate expected average
        double expectedAverage = (8.0 + 9.0 + 7.5) / 3;
        assertEquals(expectedAverage, movie.getInAppRating());

        // Test with null (should set an empty list)
        movie.setInAppRating(null);
        assertNotNull(movie.getInAppRating());
        // With an empty list, getInAppRating() should return 0.0
        assertEquals(0.0, movie.getInAppRating());
    }

    /**
     * Tests the {@link Movie#addInAppRating(Double)} method.
     * Verifies:
     * - Ratings are successfully added to the ratings list
     * - The average calculation in {@link Movie#getInAppRating()} is updated
     */
    @Test
    public void testAddInAppRating() {
        // Start with empty ratings
        movie.setInAppRating(new ArrayList<>());

        // Add a rating
        movie.addInAppRating(8.5);
        assertEquals(8.5, movie.getInAppRating());

        // Add another rating
        movie.addInAppRating(9.5);
        // Expected average: (8.5 + 9.5) / 2 = 9.0
        assertEquals(9.0, movie.getInAppRating());
    }

    /**
     * Tests the {@link Movie#setImgUrl(String)} method.
     * Verifies:
     * - Valid image URLs are properly set
     * - Null URLs are replaced with empty strings
     */
    @Test
    public void testSetImgUrl() {
        // Set a valid image URL
        movie.setImgUrl("http://new-example.com/poster.jpg");
        assertEquals("http://new-example.com/poster.jpg", movie.getImgUrl());

        // Set a null image URL (should be replaced with empty string)
        movie.setImgUrl(null);
        assertEquals("", movie.getImgUrl());
    }

    /**
     * Tests the basic functionality of the {@link Movie.Builder} class.
     * Verifies that a minimal Movie can be built with only the movieId
     * and that default values are properly applied to all other properties.
     */
    @Test
    public void testBuilder() {
        // Test building a movie with minimum valid values
        Movie minimalMovie = new Movie.Builder()
                .movieId(2)
                .build();

        // Check default values are applied
        assertEquals(2, minimalMovie.getMovieId());
        assertEquals("Unknown Title", minimalMovie.getTitle());
        assertNotNull(minimalMovie.getDirectors());
        assertTrue(minimalMovie.getDirectors().isEmpty());
        assertEquals(0, minimalMovie.getYear());
        assertEquals(0.0, minimalMovie.getRating());
        assertNotNull(minimalMovie.getGenres());
        assertTrue(minimalMovie.getGenres().isEmpty());
        assertEquals("No Overview", minimalMovie.getOverview());
        assertNotNull(minimalMovie.getCastings());
        assertTrue(minimalMovie.getCastings().isEmpty());
        assertEquals("", minimalMovie.getImgUrl());
        assertNotNull(minimalMovie.getComments());
        assertTrue(minimalMovie.getComments().isEmpty());
        assertEquals(0.0, minimalMovie.getInAppRating());
    }

    /**
     * Tests the title validation in the {@link Movie.Builder} class.
     * Verifies:
     * - Null titles are replaced with the default "Unknown Title"
     * - Blank titles are replaced with the default "Unknown Title"
     * - Valid titles are properly set
     */
    @Test
    public void testBuilderTitleValidation() {
        // Test null title (should default to "Unknown Title")
        Movie nullTitleMovie = new Movie.Builder()
                .movieId(3)
                .title(null)
                .build();
        assertEquals("Unknown Title", nullTitleMovie.getTitle());

        // Test blank title (should default to "Unknown Title")
        Movie blankTitleMovie = new Movie.Builder()
                .movieId(4)
                .title("   ")
                .build();
        assertEquals("Unknown Title", blankTitleMovie.getTitle());

        // Test valid title
        Movie validTitleMovie = new Movie.Builder()
                .movieId(5)
                .title("Valid Title")
                .build();
        assertEquals("Valid Title", validTitleMovie.getTitle());
    }

    /**
     * Tests the year validation in the {@link Movie.Builder} class.
     * Verifies:
     * - Years below 1800 are replaced with the default 0
     * - The boundary year 1800 is considered invalid and replaced with 0
     * - Years above 1800 are properly set
     */
    @Test
    public void testBuilderYearValidation() {
        // Test invalid year (below 1800)
        Movie invalidYearMovie = new Movie.Builder()
                .movieId(6)
                .year(1700)
                .build();
        assertEquals(0, invalidYearMovie.getYear()); // Should default to 0

        // Test boundary year (1800) - should still be invalid
        Movie boundaryYearMovie = new Movie.Builder()
                .movieId(7)
                .year(1800)
                .build();
        assertEquals(0, boundaryYearMovie.getYear()); // Should default to 0

        // Test valid year (1801)
        Movie validYearMovie = new Movie.Builder()
                .movieId(8)
                .year(1801)
                .build();
        assertEquals(1801, validYearMovie.getYear());
    }

    /**
     * Tests the rating validation in the {@link Movie.Builder} class.
     * Verifies:
     * - Negative ratings are replaced with the default 0.0
     * - The boundary rating 0.0 is considered valid
     * - Valid ratings within the range are properly set
     * - The boundary rating 10000.0 is considered valid
     * - Ratings above 10000.0 are replaced with the default 0.0
     *
     * Note: This test highlights the different validation criteria in the Builder (0.0-10000.0)
     * versus the setter method (0.0-10.0).
     */
    @Test
    public void testBuilderRatingValidation() {
        // Test negative rating (should default to 0.0)
        Movie negativeRatingMovie = new Movie.Builder()
                .movieId(9)
                .rating(-1.0)
                .build();
        assertEquals(0.0, negativeRatingMovie.getRating());

        // Test boundary rating (0.0) - should be valid
        Movie zeroRatingMovie = new Movie.Builder()
                .movieId(10)
                .rating(0.0)
                .build();
        assertEquals(0.0, zeroRatingMovie.getRating());

        // Test valid rating (5000.0)
        Movie validRatingMovie = new Movie.Builder()
                .movieId(11)
                .rating(5000.0)
                .build();
        assertEquals(5000.0, validRatingMovie.getRating());

        // Test boundary rating (10000.0) - should be valid
        Movie maxRatingMovie = new Movie.Builder()
                .movieId(12)
                .rating(10000.0)
                .build();
        assertEquals(10000.0, maxRatingMovie.getRating());

        // Test above max rating (10001.0) - should default to 0.0
        Movie aboveMaxRatingMovie = new Movie.Builder()
                .movieId(13)
                .rating(10001.0)
                .build();
        assertEquals(0.0, aboveMaxRatingMovie.getRating());
    }

    /**
     * Tests the overview validation in the {@link Movie.Builder} class.
     * Verifies:
     * - Null overviews are replaced with the default "No Overview"
     * - Blank overviews are replaced with the default "No Overview"
     * - Valid overviews are properly set
     *
     * Note: This test highlights the difference between the Builder (which validates)
     * and the setter method (which doesn't validate for null/blank).
     */
    @Test
    public void testBuilderOverviewValidation() {
        // Test null overview (should default to "No Overview")
        Movie nullOverviewMovie = new Movie.Builder()
                .movieId(14)
                .overview(null)
                .build();
        assertEquals("No Overview", nullOverviewMovie.getOverview());

        // Test blank overview (should default to "No Overview")
        Movie blankOverviewMovie = new Movie.Builder()
                .movieId(15)
                .overview("   ")
                .build();
        assertEquals("No Overview", blankOverviewMovie.getOverview());

        // Test valid overview
        Movie validOverviewMovie = new Movie.Builder()
                .movieId(16)
                .overview("This is a valid overview")
                .build();
        assertEquals("This is a valid overview", validOverviewMovie.getOverview());
    }

    /**
     * Tests the List fields in the {@link Movie.Builder} class.
     * Verifies that null lists (directors, genres, castings) are
     * replaced with empty lists rather than remaining null.
     */
    @Test
    public void testBuilderListFields() {
        // Test null directors, genres, and castings (should default to empty lists)
        Movie nullListsMovie = new Movie.Builder()
                .movieId(17)
                .directors(null)
                .genres(null)
                .castings(null)
                .build();

        assertNotNull(nullListsMovie.getDirectors());
        assertTrue(nullListsMovie.getDirectors().isEmpty());

        assertNotNull(nullListsMovie.getGenres());
        assertTrue(nullListsMovie.getGenres().isEmpty());

        assertNotNull(nullListsMovie.getCastings());
        assertTrue(nullListsMovie.getCastings().isEmpty());
    }

    /**
     * Tests the imgUrl validation in the {@link Movie.Builder} class.
     * Verifies:
     * - Null image URLs are replaced with empty strings
     * - Valid image URLs are properly set
     */
    @Test
    public void testBuilderImgUrlValidation() {
        // Test null imgUrl (should default to empty string)
        Movie nullImgUrlMovie = new Movie.Builder()
                .movieId(18)
                .imgUrl(null)
                .build();
        assertEquals("", nullImgUrlMovie.getImgUrl());

        // Test valid imgUrl
        Movie validImgUrlMovie = new Movie.Builder()
                .movieId(19)
                .imgUrl("http://example.com/valid.jpg")
                .build();
        assertEquals("http://example.com/valid.jpg", validImgUrlMovie.getImgUrl());
    }

    /**
     * Tests the {@link Movie#toString()} method.
     * Verifies that the string representation contains all the expected fields
     * and that the values match the Movie object's current state.
     */
    @Test
    public void testToString() {
        // Create a movie with specific values for testing
        Movie testMovie = new Movie.Builder()
                .movieId(100)
                .title("Test Movie Title")
                .directors(Arrays.asList("Director A", "Director B"))
                .year(2022)
                .rating(8.7)
                .genres(Arrays.asList(Genre.ACTION, Genre.THRILLER))
                .overview("Test overview")
                .castings(Arrays.asList("Actor A", "Actor B"))
                .imgUrl("http://test.com/image.jpg")
                .build();

        // Add some comments
        testMovie.addComment("Comment 1");
        testMovie.addComment("Comment 2");

        // Add some in-app ratings
        testMovie.addInAppRating(9.0);
        testMovie.addInAppRating(8.0);

        // Get the string representation
        String toString = testMovie.toString();

        // Verify all expected fields are present
        assertTrue(toString.contains("movieId=" + testMovie.getMovieId()));
        assertTrue(toString.contains("title='" + testMovie.getTitle() + "'"));
        assertTrue(toString.contains("directors=" + testMovie.getDirectors()));
        assertTrue(toString.contains("year=" + testMovie.getYear()));
        assertTrue(toString.contains("rating=" + testMovie.getRating()));
        assertTrue(toString.contains("genres=" + testMovie.getGenres()));
        assertTrue(toString.contains("castings=" + testMovie.getCastings()));
        assertTrue(toString.contains("comments=" + testMovie.getComments()));
        assertTrue(toString.contains("inAppRating="));

        // Check for calculated average in-app rating
        double expectedAverage = (9.0 + 8.0) / 2.0;
        assertTrue(toString.contains("updatedAverageInAppRating=" + expectedAverage));

        assertTrue(toString.contains("imgUrl='" + testMovie.getImgUrl() + "'"));

        // Verify the string starts and ends with the expected format
        assertTrue(toString.startsWith("Movie{"));
        assertTrue(toString.endsWith("}"));
    }
}
