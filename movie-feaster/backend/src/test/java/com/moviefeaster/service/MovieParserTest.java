package com.moviefeaster.service;

import com.moviefeaster.model.Movie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MovieParserTest {

    @Test
    void getParsedMovies() {
        List<Movie> parsedMovies = MovieParser.getMoviesFromApi();

//        for (Movie movie : parsedMovies) {
//            System.out.println(movie);
//        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }
}
