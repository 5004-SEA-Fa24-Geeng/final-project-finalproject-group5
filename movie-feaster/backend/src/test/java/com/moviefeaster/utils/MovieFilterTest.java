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
     * Tests filterByTitle with blank keyword.
     */
    @Test
    void filterByTitleBlankKeyword() {
        // Test with empty string
        List<Movie> result = MovieFilter.filterByTitle(testMovies, "");
        assertTrue(result.isEmpty());

        // Test with whitespace only
        result = MovieFilter.filterByTitle(testMovies, "   ");
        assertTrue(result.isEmpty());
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
     * Tests edge cases for filtering movies by minimum rating.
     */
    @Test
    void filterByMinRatingEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByMinRating(null, 8.5);
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByMinRating(new ArrayList<>(), 8.5);
        assertTrue(result.isEmpty());

        // Test negative rating (this specifically tests minRating < 0.0)
        result = MovieFilter.filterByMinRating(testMovies, -1.0);
        assertTrue(result.isEmpty());

        // Test rating above valid range (this specifically tests minRating > 10.0)
        result = MovieFilter.filterByMinRating(testMovies, 11.0);
        assertTrue(result.isEmpty());
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

    /**
     * Tests edge cases for filtering movies by exact title.
     */
    @Test
    void filterByExactTitleEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByExactTitle(null, "The Godfather");
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByExactTitle(new ArrayList<>(), "The Godfather");
        assertTrue(result.isEmpty());

        // Test null search term
        result = MovieFilter.filterByExactTitle(testMovies, null);
        assertTrue(result.isEmpty());

        // Test empty search term
        result = MovieFilter.filterByExactTitle(testMovies, "");
        assertTrue(result.isEmpty());

        // Test whitespace search term
        result = MovieFilter.filterByExactTitle(testMovies, "   ");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests edge cases for filtering movies by director.
     */
    @Test
    void filterByDirectorEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByDirector(null, "Nolan");
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByDirector(new ArrayList<>(), "Nolan");
        assertTrue(result.isEmpty());

        // Test null search term
        result = MovieFilter.filterByDirector(testMovies, null);
        assertTrue(result.isEmpty());

        // Test empty search term
        result = MovieFilter.filterByDirector(testMovies, "");
        assertTrue(result.isEmpty());

        // Test whitespace search term
        result = MovieFilter.filterByDirector(testMovies, "   ");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests edge cases for filtering movies by year.
     */
    @Test
    void filterByYearEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByYear(null, 1999);
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByYear(new ArrayList<>(), 1999);
        assertTrue(result.isEmpty());

        // Test negative year
        result = MovieFilter.filterByYear(testMovies, -1);
        assertTrue(result.isEmpty());

        // Test future year
        result = MovieFilter.filterByYear(testMovies, 3000);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests edge cases for filtering movies by actor.
     */
    @Test
    void filterByActorEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByActor(null, "Freeman");
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByActor(new ArrayList<>(), "Freeman");
        assertTrue(result.isEmpty());

        // Test null search term
        result = MovieFilter.filterByActor(testMovies, null);
        assertTrue(result.isEmpty());

        // Test empty search term
        result = MovieFilter.filterByActor(testMovies, "");
        assertTrue(result.isEmpty());

        // Test whitespace search term
        result = MovieFilter.filterByActor(testMovies, "   ");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests edge cases for filtering movies by genre.
     */
    @Test
    void filterByGenreEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByGenre(null, "action");
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByGenre(new ArrayList<>(), "action");
        assertTrue(result.isEmpty());

        // Test null search term
        result = MovieFilter.filterByGenre(testMovies, null);
        assertTrue(result.isEmpty());

        // Test empty search term
        result = MovieFilter.filterByGenre(testMovies, "");
        assertTrue(result.isEmpty());

        // Test whitespace search term
        result = MovieFilter.filterByGenre(testMovies, "   ");
        assertTrue(result.isEmpty());

        // Test non-existent genre
        result = MovieFilter.filterByGenre(testMovies, "nonexistent");
        assertTrue(result.isEmpty());
    }
    /**
     * Tests filtering movies by year range.
     */
    @Test
    void filterByYearRange() {
        // Test normal case
        List<Movie> result = MovieFilter.filterByYearRange(testMovies, 1999, 2009);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie3));

        // Test inclusive bounds
        result = MovieFilter.filterByYearRange(testMovies, 1999, 1999);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test no matches
        result = MovieFilter.filterByYearRange(testMovies, 2011, 2020);
        assertEquals(0, result.size());
    }

    /**
     * Tests edge cases for filtering movies by year range.
     */
    @Test
    void filterByYearRangeEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByYearRange(null, 1999, 2010);
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByYearRange(new ArrayList<>(), 1999, 2010);
        assertTrue(result.isEmpty());

        // Test negative start year
        result = MovieFilter.filterByYearRange(testMovies, -1, 2010);
        assertTrue(result.isEmpty());

        // Test negative end year
        result = MovieFilter.filterByYearRange(testMovies, 1999, -1);
        assertTrue(result.isEmpty());

        // Test invalid range (start > end)
        result = MovieFilter.filterByYearRange(testMovies, 2010, 1999);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests filtering movies by maximum rating.
     */
    @Test
    void filterByMaxRating() {
        List<Movie> result = MovieFilter.filterByMaxRating(testMovies, 8.5f);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        result = MovieFilter.filterByMaxRating(testMovies, 8.9f);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByMaxRating(testMovies, 7.0f);
        assertEquals(0, result.size());
    }

    /**
     * Tests edge cases for filtering movies by maximum rating.
     */
    @Test
    void filterByMaxRatingEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByMaxRating(null, 8.5f);
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByMaxRating(new ArrayList<>(), 8.5f);
        assertTrue(result.isEmpty());

        // Test negative rating
        result = MovieFilter.filterByMaxRating(testMovies, -1.0f);
        assertTrue(result.isEmpty());

        // Test rating above valid range
        result = MovieFilter.filterByMaxRating(testMovies, 11.0f);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests filtering movies by minimum in-app rating.
     */
    @Test
    void filterByMinInAppRating() {
        List<Movie> result = MovieFilter.filterByMinInAppRating(testMovies, 4.5);
        assertEquals(2, result.size());
        assertTrue(result.contains(movie1));
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByMinInAppRating(testMovies, 4.8);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie2));

        result = MovieFilter.filterByMinInAppRating(testMovies, 5.0);
        assertEquals(0, result.size());
    }

    /**
     * Tests edge cases for filtering movies by minimum in-app rating.
     */
    @Test
    void filterByMinInAppRatingEdgeCases() {
        // Test null input list
        List<Movie> result = MovieFilter.filterByMinInAppRating(null, 4.5);
        assertTrue(result.isEmpty());

        // Test empty input list
        result = MovieFilter.filterByMinInAppRating(new ArrayList<>(), 4.5);
        assertTrue(result.isEmpty());
    }

    /**
     * Tests combining two movie lists with AND operation.
     */
    @Test
    void combineAnd() {
        // Create test lists
        List<Movie> actionMovies = MovieFilter.filterByGenre(testMovies, "action");
        List<Movie> moviesFrom1999 = MovieFilter.filterByYear(testMovies, 1999);

        // Test normal case - intersection
        List<Movie> result = MovieFilter.combineAnd(actionMovies, moviesFrom1999);
        assertEquals(1, result.size());
        assertTrue(result.contains(movie1));

        // Test no common movies
        List<Movie> thrillerMovies = MovieFilter.filterByGenre(testMovies, "thriller");
        result = MovieFilter.combineAnd(thrillerMovies, moviesFrom1999);
        assertEquals(0, result.size());
    }

    /**
     * Tests edge cases for combining two movie lists.
     */
    @Test
    void combineAndEdgeCases() {
        List<Movie> actionMovies = MovieFilter.filterByGenre(testMovies, "action");

        // Test first list null
        List<Movie> result = MovieFilter.combineAnd(null, actionMovies);
        assertTrue(result.isEmpty());

        // Test second list null
        result = MovieFilter.combineAnd(actionMovies, null);
        assertTrue(result.isEmpty());

        // Test both lists null
        result = MovieFilter.combineAnd(null, null);
        assertTrue(result.isEmpty());

        // Test first list empty
        result = MovieFilter.combineAnd(new ArrayList<>(), actionMovies);
        assertTrue(result.isEmpty());

        // Test second list empty
        result = MovieFilter.combineAnd(actionMovies, new ArrayList<>());
        assertTrue(result.isEmpty());
    }

    /**
     * Tests for handling null comments in filterByCommentKeyword.
     */
    @Test
    void filterByCommentKeywordNullComments() {
        // Create a movie with null comments list
        Movie movieWithNullComments = new Movie.Builder()
                .movieId(4)
                .title("No Comments Movie")
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullComments);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByCommentKeyword(testList, "keyword");
        assertEquals(0, result.size());
    }

    /**
     * Tests for handling null or empty comments in movies.
     */
    @Test
    void filterByCommentKeywordEdgeCases() {
        // Test null keyword
        List<Movie> result = MovieFilter.filterByCommentKeyword(testMovies, null);
        assertTrue(result.isEmpty());

        // Test whitespace keyword
        result = MovieFilter.filterByCommentKeyword(testMovies, "   ");
        assertTrue(result.isEmpty());
    }

    /**
     * Tests handling of null inAppRating lists in movies.
     */
    @Test
    void filterByMinInAppRatingNullRatingList() {
        // Create a movie with null inAppRating list
        Movie movieWithNullRatings = new Movie.Builder()
                .movieId(4)
                .title("No Ratings Movie")
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullRatings);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByMinInAppRating(testList, 4.0);
        assertEquals(0, result.size());
    }

    /**
     * Tests for when a movie has an empty inAppRating list.
     */
    @Test
    void filterByMinInAppRatingEmptyRatingList() {
        // Create a movie with empty inAppRating list
        Movie movieWithEmptyRatings = new Movie.Builder()
                .movieId(4)
                .title("Empty Ratings Movie")
                .year(2020)
                .build();
        movieWithEmptyRatings.setInAppRating(new ArrayList<>());

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithEmptyRatings);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByMinInAppRating(testList, 4.0);
        assertEquals(0, result.size());
    }

    /**
     * Tests for when movies have null genre lists.
     */
    @Test
    void filterByGenreNullGenreList() {
        // Create a movie with null genres list
        Movie movieWithNullGenres = new Movie.Builder()
                .movieId(4)
                .title("No Genres Movie")
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullGenres);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByGenre(testList, "action");
        assertEquals(0, result.size());
    }

    /**
     * Tests for when movies have null castings lists.
     */
    @Test
    void filterByActorNullCastingsList() {
        // Create a movie with null castings list
        Movie movieWithNullCastings = new Movie.Builder()
                .movieId(4)
                .title("No Castings Movie")
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullCastings);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByActor(testList, "actor");
        assertEquals(0, result.size());
    }

    /**
     * Tests for when movies have null directors lists.
     */
    @Test
    void filterByDirectorNullDirectorsList() {
        // Create a movie with null directors list
        Movie movieWithNullDirectors = new Movie.Builder()
                .movieId(4)
                .title("No Directors Movie")
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullDirectors);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByDirector(testList, "director");
        assertEquals(0, result.size());
    }

    /**
     * Tests for when movies have null titles.
     */
    @Test
    void filterByTitleNullTitle() {
        // Create a movie with null title
        Movie movieWithNullTitle = new Movie.Builder()
                .movieId(4)
                .year(2020)
                .build();

        // Add this movie to a test list
        List<Movie> testList = new ArrayList<>();
        testList.add(movieWithNullTitle);

        // This should not throw an exception
        List<Movie> result = MovieFilter.filterByTitle(testList, "title");
        assertEquals(1, result.size());

        // Also test filterByExactTitle
        result = MovieFilter.filterByExactTitle(testList, "title");
        assertEquals(0, result.size());
    }
}
