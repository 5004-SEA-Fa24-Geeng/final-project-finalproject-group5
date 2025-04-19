package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import com.moviefeaster.service.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
class MovieParserTest {

    @Test
    void getParsedMoviesSummary() {

        Collection<MovieParser.MovieSummary> parsedMovies = MovieParser.getParsedMoviesSummary();

//        for (MovieParser.MovieSummary movie : parsedMovies) {
//            System.out.println(movie);
//        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }

    @Test
    void getParsedMovies() {
        Collection<Movie> parsedMovies = MovieParser.getMoviesFromApi();

//        for (Movie movie : parsedMovies) {
//            System.out.println(movie);
//        }

        assertNotNull(parsedMovies, "Parsed movies should not be null");
        assertFalse(parsedMovies.isEmpty(), "Parsed movies list should not be empty");
    }
}