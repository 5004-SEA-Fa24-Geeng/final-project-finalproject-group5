package com.moviefeaster.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenreTest {

    /**
     * Tests Genre from ID.
     */
    @Test
    void testGenreFromId() {
        assertEquals(Genre.ACTION, Genre.fromId(28));
        assertEquals(Genre.ADVENTURE, Genre.fromId(12));
    }

    /**
     * Tests getting Genre ID.
     */
    @Test
    void testGetGenreId() {
        assertEquals(28, Genre.ACTION.getGenreId());
        assertEquals(12, Genre.ADVENTURE.getGenreId());
    }

    /**
     * Tests that Genre.fromId returns null when given an invalid ID.
     */
    @Test
    void testGenreFromIdWithInvalidId() {
        assertNull(Genre.fromId(999)); // An ID that doesn't match any genre
    }

    /**
     * Tests that Genre.fromId returns the correct genre for all defined genre IDs.
     */
    @Test
    void testGenreFromIdForAllGenres() {
        // Test all genres to ensure complete coverage of the loop
        assertEquals(Genre.ACTION, Genre.fromId(28));
        assertEquals(Genre.ADVENTURE, Genre.fromId(12));
        assertEquals(Genre.ANIMATION, Genre.fromId(16));
        assertEquals(Genre.COMEDY, Genre.fromId(35));
        assertEquals(Genre.CRIME, Genre.fromId(80));
        assertEquals(Genre.DOCUMENTARY, Genre.fromId(99));
        assertEquals(Genre.DRAMA, Genre.fromId(18));
        assertEquals(Genre.FAMILY, Genre.fromId(10751));
        assertEquals(Genre.FANTASY, Genre.fromId(14));
        assertEquals(Genre.HISTORY, Genre.fromId(36));
        assertEquals(Genre.HORROR, Genre.fromId(27));
        assertEquals(Genre.MUSIC, Genre.fromId(10402));
        assertEquals(Genre.MYSTERY, Genre.fromId(9648));
        assertEquals(Genre.ROMANCE, Genre.fromId(10749));
        assertEquals(Genre.SCIENCE_FICTION, Genre.fromId(878));
        assertEquals(Genre.TV_MOVIE, Genre.fromId(10770));
        assertEquals(Genre.THRILLER, Genre.fromId(53));
        assertEquals(Genre.WAR, Genre.fromId(10752));
        assertEquals(Genre.WESTERN, Genre.fromId(37));
    }

    /**
     * Tests that Genre.fromName correctly returns a Genre when given a valid name.
     */
    @Test
    void testGenreFromNameWithValidName() {
        assertEquals(Genre.ACTION, Genre.fromName("ACTION"));
        assertEquals(Genre.COMEDY, Genre.fromName("COMEDY"));
    }

    /**
     * Tests that Genre.fromName correctly returns null when given an invalid name.
     */
    @Test
    void testGenreFromNameWithInvalidName() {
        assertNull(Genre.fromName("NOT_A_GENRE"));
    }

    /**
     * Tests that Genre.fromName is case-sensitive as implemented.
     */
    @Test
    void testGenreFromNameCaseSensitivity() {
        assertNull(Genre.fromName("Action"));
        assertNull(Genre.fromName("action"));
    }
}
