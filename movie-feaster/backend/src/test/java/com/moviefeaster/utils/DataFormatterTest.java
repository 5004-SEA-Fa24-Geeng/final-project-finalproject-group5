package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DataFormatter utility.
 */
public class DataFormatterTest {

    /** Test movie with complete data. */
    private Movie movie1;
    /** Test movie with different data. */
    private Movie movie2;
    /** Collection of test movies. */
    private Collection<Movie> testMovies;
    /** Output stream for testing write operations. */
    private ByteArrayOutputStream outputStream;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    public void setUp() {
        // Initialize Movie 1 with complete data
        movie1 = new Movie.Builder()
                .movieId(1)
                .title("Movie 1")
                .directors(Arrays.asList("Director A", "Director B"))
                .year(2020)
                .rating(8.5)
                .genres(Arrays.asList(Genre.ACTION, Genre.DRAMA))
                .overview("Plot summary for Movie 1")
                .castings(Arrays.asList("Actor X", "Actor Y"))
                .imgUrl("http://example.com/movie1.jpg")
                .build();
        movie1.setComments(Arrays.asList("Excellent film", "Highly recommended"));
        movie1.setInAppRating(Arrays.asList(4.8, 4.9));

// Initialize Movie 2 with different data
        movie2 = new Movie.Builder()
                .movieId(2)
                .title("Movie 2")
                .directors(Arrays.asList("Director C"))
                .year(2021)
                .rating(7.9)
                .genres(Arrays.asList(Genre.COMEDY))
                .overview("Plot summary for Movie 2")
                .castings(Arrays.asList("Actor Z"))
                .imgUrl("http://example.com/movie2.jpg")
                .build();
        movie2.setComments(Arrays.asList("Funny movie"));
        movie2.setInAppRating(Arrays.asList(4.2));

// Create test collection
        testMovies = new ArrayList<>();
        testMovies.add(movie1);
        testMovies.add(movie2);
//        // Initialize Movie 1 with complete data
//        movie1 = new Movie(
//                1,
//                "Movie 1",
//                Arrays.asList("Director A", "Director B"),
//                2020,
//                8.5,
//                Arrays.asList(Genre.ACTION, Genre.DRAMA),
//                "Plot summary for Movie 1",
//                Arrays.asList("Actor X", "Actor Y"),
//                "http://example.com/movie1.jpg"
//        );
//        movie1.setComments(Arrays.asList("Excellent film", "Highly recommended"));
//        movie1.setInAppRating(Arrays.asList(4.8, 4.9));
//
//        // Initialize Movie 2 with different data
//        movie2 = new Movie(
//                2,
//                "Movie 2",
//                Arrays.asList("Director C"),
//                2021,
//                7.9,
//                Arrays.asList(Genre.COMEDY),
//                "Plot summary for Movie 2",
//                Arrays.asList("Actor Z"),
//                "http://example.com/movie2.jpg"
//        );
//        movie2.setComments(Arrays.asList("Funny movie"));
//        movie2.setInAppRating(Arrays.asList(4.2));
//
//        // Create test collection
//        testMovies = new ArrayList<>();
//        testMovies.add(movie1);
//        testMovies.add(movie2);

        // Initialize output stream
        outputStream = new ByteArrayOutputStream();
    }

    /**
     * Tests formatting of a single movie with all fields.
     */
    @Test
    public void singleMovieContainsAllFields() {
        String result = DataFormatter.formatSingleMovie(movie1);

        // Verify all core fields are present
        assertTrue(result.contains("Title: Movie 1"), "Should contain movie title");
        assertTrue(result.contains("Year: 2020"), "Should contain release year");
        assertTrue(result.contains("Rating: 8.5"), "Should contain rating");
        assertTrue(result.contains("Director A, Director B"), "Should contain directors");
        assertTrue(result.contains("ACTION, DRAMA") || result.contains("DRAMA, ACTION"),
                "Should contain genres");
        assertTrue(result.contains("Actor X, Actor Y"), "Should contain cast");
        assertTrue(result.contains("Excellent film"), "Should contain comments");
    }

    /**
     * Tests formatting of a movie list containing multiple movies.
     */
    @Test
    public void movieListContainsAllMovies() {
        String result = DataFormatter.formatMovieList(testMovies);

        // Verify both movies are present
        assertTrue(result.contains("Movie 1"), "Should contain Movie 1");
        assertTrue(result.contains("Movie 2"), "Should contain Movie 2");

        // Verify separator exists between movies
        assertTrue(result.contains("-------------------"),
                "Should have separator between movies");
    }

    /**
     * Tests writing movies in pretty format.
     */
    @Test
    public void writePretty() {
        DataFormatter.write(testMovies, Format.PRETTY, outputStream);
        String result = outputStream.toString();

        assertTrue(result.contains("Movie 1"), "Default format should contain Movie 1");
        assertTrue(result.contains("Movie 2"), "Default format should contain Movie 2");
    }

    /**
     * Tests writing movies in CSV format.
     */
    @Test
    public void writeCsv() {
        DataFormatter.write(testMovies, Format.CSV, outputStream);
        String result = outputStream.toString();
        String[] lines = result.split("\n");

        // Verify CSV header
        assertEquals("Title,Year,Rating,Directors,Genres,Castings,Comments,InAppRating",
                lines[0].trim(), "CSV header is incorrect");

        // Verify data rows
        assertTrue(lines[1].contains("Movie 1,2020,8.5"), "Movie 1 data is incorrect");
        assertTrue(lines[2].contains("Movie 2,2021,7.9"), "Movie 2 data is incorrect");
    }

    /**
     * Tests writing movies in JSON format.
     */
    @Test
    public void writeJson() {
        DataFormatter.write(testMovies, Format.JSON, outputStream);
        String result = outputStream.toString();

        assertTrue(result.contains("\"title\" : \"Movie 1\""), "Should contain Movie 1 title");
        assertTrue(result.contains("\"year\" : 2020"), "Should contain Movie 1 year");
        assertTrue(result.contains("\"title\" : \"Movie 2\""), "Should contain Movie 2 title");
        assertTrue(result.contains("\"year\" : 2021"), "Should contain Movie 2 year");
    }

    /**
     * Tests writing movies in XML format.
     */
    @Test
    public void writeXml() {
        DataFormatter.write(testMovies, Format.XML, outputStream);
        String result = outputStream.toString();

        assertTrue(result.contains("<title>Movie 1</title>"), "Should contain Movie 1 title");
        assertTrue(result.contains("<year>2020</year>"), "Should contain Movie 1 year");
        assertTrue(result.contains("<title>Movie 2</title>"), "Should contain Movie 2 title");
        assertTrue(result.contains("<year>2021</year>"), "Should contain Movie 2 year");
    }

    /**
     * Tests formatting of a movie with empty fields.
     */
    @Test
    public void movieWithEmptyFields() {
//        Movie emptyMovie = new Movie(
//                3,
//                "Empty Fields Movie",
//                Collections.emptyList(),
//                0,
//                0.0,
//                Collections.emptyList(),
//                "",
//                Collections.emptyList(),
//                ""
//        );
        Movie emptyMovie = new Movie.Builder()
                .movieId(3)
                .title("Empty Fields Movie")
                .directors(Collections.emptyList())
                .year(0)
                .rating(0.0)
                .genres(Collections.emptyList())
                .overview("")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build();

        String result = DataFormatter.formatSingleMovie(emptyMovie);

        assertTrue(result.contains("Directors: Unknown"), "Empty directors should show Unknown");
        assertTrue(result.contains("Genres: Unknown"), "Empty genres should show Unknown");
        assertTrue(result.contains("Cast: Unknown"), "Empty cast should show Unknown");
        assertFalse(result.contains("Poster:"), "Empty URL should not show Poster");
    }

    /**
     * Tests writing an empty movie list.
     */
    @Test
    public void writeEmptyList() {
        DataFormatter.write(Collections.emptyList(), Format.PRETTY, outputStream);
        assertEquals("", outputStream.toString(), "Empty list should produce empty output");
    }
}
