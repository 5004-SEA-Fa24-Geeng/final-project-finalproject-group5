package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MovieXMLWrapperTest {

    @Test
    void shouldStoreAndRetrieveMovies() {
        // Setup
        Movie movie = new Movie.Builder()
                .movieId(1)
                .title("Test")
                .directors(Collections.emptyList())
                .year(2020)
                .rating(8.0)
                .genres(Collections.emptyList())
                .overview("Overview")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build();

        MovieXMLWrapper wrapper = new MovieXMLWrapper(Collections.singletonList(movie));

        // Verify
        assertEquals(1, wrapper.getMovie().size());
        assertEquals("Test", wrapper.getMovie().iterator().next().getTitle());
    }

    @Test
    void shouldHandleEmptyCollection() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(Collections.emptyList());
        assertTrue(wrapper.getMovie().isEmpty());
    }

    @Test
    void shouldHandleNullCollection() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(null);
        assertNull(wrapper.getMovie());
    }

    @Test
    void setMovieShouldUpdateCollection() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(null);
        assertNull(wrapper.getMovie());

        Movie movie = new Movie.Builder()
                .movieId(2)
                .title("New Movie")
                .directors(Collections.emptyList())
                .year(2021)
                .rating(9.0)
                .genres(Collections.emptyList())
                .overview("New Overview")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build();
        wrapper.setMovie(Collections.singletonList(movie));

        assertEquals(1, wrapper.getMovie().size());
        assertEquals("New Movie", wrapper.getMovie().iterator().next().getTitle());
    }

    @Test
    void setMovieShouldUpdateStoredReference() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(Collections.emptyList());
        Movie movie = new Movie.Builder()
                .movieId(1)
                .title("Test")
                .directors(Collections.emptyList())
                .year(2020)
                .rating(8.0)
                .genres(Collections.emptyList())
                .overview("Overview")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build();
        Collection<Movie> newMovies = Collections.singletonList(movie);

        wrapper.setMovie(newMovies);
        assertSame(newMovies, wrapper.getMovie());
    }
}
