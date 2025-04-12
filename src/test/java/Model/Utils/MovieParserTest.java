package Model.Utils;

import Model.Movie;
import Service.MovieParser;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
class MovieParserTest {

    @Test
<<<<<<< HEAD
    void getParsedMoviesSummary() {

        Collection<MovieParser.MovieSummary> parsedMovies = MovieParser.getParsedMoviesSummary();

        for (MovieParser.MovieSummary movie : parsedMovies) {
            System.out.println(movie);
        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }

    @Test
=======
>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098
    void getParsedMovies() {
        Collection<Movie> parsedMovies = MovieParser.getMoviesFromApi();

        for (Movie movie : parsedMovies) {
            System.out.println(movie);
        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }
}