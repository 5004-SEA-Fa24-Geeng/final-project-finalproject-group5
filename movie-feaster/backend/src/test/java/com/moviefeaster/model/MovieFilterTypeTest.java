package com.moviefeaster.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovieFilterTypeTest {

    @Test
    void testEnumValues() {
        MovieFilterType[] values = MovieFilterType.values();
        assertEquals(11, values.length);
        
        assertEquals(MovieFilterType.TITLE_KEYWORD, values[0]);
        assertEquals(MovieFilterType.EXACT_TITLE, values[1]);
        assertEquals(MovieFilterType.DIRECTOR, values[2]);
        assertEquals(MovieFilterType.ACTOR, values[3]);
        assertEquals(MovieFilterType.GENRE, values[4]);
        assertEquals(MovieFilterType.YEAR, values[5]);
        assertEquals(MovieFilterType.YEAR_RANGE, values[6]);
        assertEquals(MovieFilterType.MIN_RATING, values[7]);
        assertEquals(MovieFilterType.MAX_RATING, values[8]);
        assertEquals(MovieFilterType.COMMENT_KEYWORD, values[9]);
        assertEquals(MovieFilterType.MIN_INAPP_RATING, values[10]);
    }

    @Test
    void testValueOf() {
        assertEquals(MovieFilterType.TITLE_KEYWORD, MovieFilterType.valueOf("TITLE_KEYWORD"));
        assertEquals(MovieFilterType.EXACT_TITLE, MovieFilterType.valueOf("EXACT_TITLE"));
        assertEquals(MovieFilterType.DIRECTOR, MovieFilterType.valueOf("DIRECTOR"));
        assertEquals(MovieFilterType.ACTOR, MovieFilterType.valueOf("ACTOR"));
        assertEquals(MovieFilterType.GENRE, MovieFilterType.valueOf("GENRE"));
        assertEquals(MovieFilterType.YEAR, MovieFilterType.valueOf("YEAR"));
        assertEquals(MovieFilterType.YEAR_RANGE, MovieFilterType.valueOf("YEAR_RANGE"));
        assertEquals(MovieFilterType.MIN_RATING, MovieFilterType.valueOf("MIN_RATING"));
        assertEquals(MovieFilterType.MAX_RATING, MovieFilterType.valueOf("MAX_RATING"));
        assertEquals(MovieFilterType.COMMENT_KEYWORD, MovieFilterType.valueOf("COMMENT_KEYWORD"));
        assertEquals(MovieFilterType.MIN_INAPP_RATING, MovieFilterType.valueOf("MIN_INAPP_RATING"));
    }

    @Test
    void testOrdinal() {
        assertEquals(0, MovieFilterType.TITLE_KEYWORD.ordinal());
        assertEquals(1, MovieFilterType.EXACT_TITLE.ordinal());
        assertEquals(2, MovieFilterType.DIRECTOR.ordinal());
        assertEquals(3, MovieFilterType.ACTOR.ordinal());
        assertEquals(4, MovieFilterType.GENRE.ordinal());
        assertEquals(5, MovieFilterType.YEAR.ordinal());
        assertEquals(6, MovieFilterType.YEAR_RANGE.ordinal());
        assertEquals(7, MovieFilterType.MIN_RATING.ordinal());
        assertEquals(8, MovieFilterType.MAX_RATING.ordinal());
        assertEquals(9, MovieFilterType.COMMENT_KEYWORD.ordinal());
        assertEquals(10, MovieFilterType.MIN_INAPP_RATING.ordinal());
    }

    @Test
    void testToString() {
        assertEquals("TITLE_KEYWORD", MovieFilterType.TITLE_KEYWORD.toString());
        assertEquals("EXACT_TITLE", MovieFilterType.EXACT_TITLE.toString());
        assertEquals("DIRECTOR", MovieFilterType.DIRECTOR.toString());
        assertEquals("ACTOR", MovieFilterType.ACTOR.toString());
        assertEquals("GENRE", MovieFilterType.GENRE.toString());
        assertEquals("YEAR", MovieFilterType.YEAR.toString());
        assertEquals("YEAR_RANGE", MovieFilterType.YEAR_RANGE.toString());
        assertEquals("MIN_RATING", MovieFilterType.MIN_RATING.toString());
        assertEquals("MAX_RATING", MovieFilterType.MAX_RATING.toString());
        assertEquals("COMMENT_KEYWORD", MovieFilterType.COMMENT_KEYWORD.toString());
        assertEquals("MIN_INAPP_RATING", MovieFilterType.MIN_INAPP_RATING.toString());
    }
}
