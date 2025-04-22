package com.moviefeaster.utils;

import com.moviefeaster.model.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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

    @Test
    void shouldHandleLargeCollection() {
        // Create a large collection of movies
        List<Movie> largeCollection = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Movie movie = new Movie.Builder()
                    .movieId(i)
                    .title("Movie " + i)
                    .directors(Collections.emptyList())
                    .year(2000 + (i % 20))
                    .rating(5.0 + (i % 5))
                    .genres(Collections.emptyList())
                    .overview("Overview " + i)
                    .castings(Collections.emptyList())
                    .imgUrl("")
                    .build();
            largeCollection.add(movie);
        }

        MovieXMLWrapper wrapper = new MovieXMLWrapper(largeCollection);
        assertEquals(1000, wrapper.getMovie().size());
    }

    @Test
    void shouldHandleCollectionWithNullElements() {
        List<Movie> collectionWithNulls = new ArrayList<>();
        collectionWithNulls.add(null);
        collectionWithNulls.add(new Movie.Builder()
                .movieId(1)
                .title("Valid Movie")
                .directors(Collections.emptyList())
                .year(2020)
                .rating(8.0)
                .genres(Collections.emptyList())
                .overview("Overview")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build());
        collectionWithNulls.add(null);

        MovieXMLWrapper wrapper = new MovieXMLWrapper(collectionWithNulls);
        assertEquals(3, wrapper.getMovie().size());
        assertNull(wrapper.getMovie().iterator().next());
    }

    @Test
    void shouldHandlePartiallyInitializedMovies() {
        List<Movie> partialMovies = new ArrayList<>();
        
        // Movie with only required fields
        partialMovies.add(new Movie.Builder()
                .movieId(1)
                .title("Minimal Movie")
                .directors(Collections.emptyList())
                .year(2020)
                .rating(0.0)
                .genres(Collections.emptyList())
                .overview("")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build());

        // Movie with null title
        partialMovies.add(new Movie.Builder()
                .movieId(2)
                .title(null)
                .directors(Collections.emptyList())
                .year(2020)
                .rating(0.0)
                .genres(Collections.emptyList())
                .overview("")
                .castings(Collections.emptyList())
                .imgUrl("")
                .build());

        MovieXMLWrapper wrapper = new MovieXMLWrapper(partialMovies);
        assertEquals(2, wrapper.getMovie().size());
        assertEquals("Minimal Movie", wrapper.getMovie().iterator().next().getTitle());
    }
}
