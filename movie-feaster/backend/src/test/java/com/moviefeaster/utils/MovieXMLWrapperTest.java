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
        Movie movie = new Movie(1, "Test", Collections.emptyList(),
                2020, 8.0, Collections.emptyList(),
                "Overview", Collections.emptyList(), "");
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
    void setMovie_shouldUpdateCollection() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(null);
        assertNull(wrapper.getMovie());

        Movie movie = new Movie(2, "New Movie", Collections.emptyList(),
                2021, 9.0, Collections.emptyList(),
                "New Overview", Collections.emptyList(), "");
        wrapper.setMovie(Collections.singletonList(movie));

        assertEquals(1, wrapper.getMovie().size());
        assertEquals("New Movie", wrapper.getMovie().iterator().next().getTitle());
    }


    @Test
    void setMovie_shouldUpdateStoredReference() {
        MovieXMLWrapper wrapper = new MovieXMLWrapper(Collections.emptyList());
        Movie movie = new Movie(1, "Test", Collections.emptyList(),
                2020, 8.0, Collections.emptyList(),
                "Overview", Collections.emptyList(), "");
        Collection<Movie> newMovies = Collections.singletonList(movie);

        wrapper.setMovie(newMovies);
        assertSame(newMovies, wrapper.getMovie());
    }
}
