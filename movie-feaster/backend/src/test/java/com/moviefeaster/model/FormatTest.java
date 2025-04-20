package com.moviefeaster.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormatTest {

    @Test
    void containsValues() {
        assertEquals(Format.JSON, Format.containsValues("json"));
        assertEquals(Format.JSON, Format.containsValues("JSON"));
        assertEquals(Format.XML, Format.containsValues("xml"));
        assertEquals(Format.CSV, Format.containsValues("CSV"));
        assertEquals(Format.PRETTY, Format.containsValues("pretty"));

        assertNull(Format.containsValues("invalid"));
        assertNull(Format.containsValues(""));
        assertNull(Format.containsValues(null));
    }

    @Test
    void values() {
        Format[] formats = Format.values();
        assertEquals(4, formats.length);

        List<Format> formatList = Arrays.asList(formats);
        assertTrue(formatList.contains(Format.JSON));
        assertTrue(formatList.contains(Format.XML));
        assertTrue(formatList.contains(Format.CSV));
        assertTrue(formatList.contains(Format.PRETTY));
    }

    @Test
    void valueOf() {
        assertEquals(Format.JSON, Format.valueOf("JSON"));
        assertEquals(Format.XML, Format.valueOf("XML"));
        assertEquals(Format.CSV, Format.valueOf("CSV"));
        assertEquals(Format.PRETTY, Format.valueOf("PRETTY"));

        assertThrows(IllegalArgumentException.class, () -> Format.valueOf("invalid"));
        assertThrows(NullPointerException.class, () -> Format.valueOf(null));
    }
}
