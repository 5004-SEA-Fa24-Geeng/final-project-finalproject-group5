package Model.Net;

import Model.Movie;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
class MovieParserTest {

    @Test
    void getParsedMovies() {

        Collection<Movie> parsedMovies = MovieParser.getParsedMovies();

        for (Movie movie : parsedMovies) {
            System.out.println(movie);
        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }
}