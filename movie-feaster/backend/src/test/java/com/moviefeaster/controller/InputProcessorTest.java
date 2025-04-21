package com.moviefeaster.controller;

import com.moviefeaster.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InputProcessor.
 */
class InputProcessorTest {

    /** Private InputProcessor. */
    private InputProcessor inputProcessor;

    /**
     * Sets up test data before each test.
     */
    @BeforeEach
    void setUp() {
        // Use reflection to instantiate InputProcessor with private constructor
        try {
            java.lang.reflect.Constructor<InputProcessor> constructor =
                    InputProcessor.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            inputProcessor = constructor.newInstance();
        } catch (Exception e) {
            fail("Failed to instantiate InputProcessor: " + e.getMessage());
        }
    }

    /**
     * Tests that optionalParseTitle correctly processes valid input by returning a trimmed string.
     */
    @Test
    void testOptionalParseTitleWithValidInput() {
        assertEquals("The Godfather", inputProcessor.optionalParseTitle("  The Godfather  "));
    }

    /**
     * Tests that optionalParseTitle correctly handles null input by returning null.
     */
    @Test
    void testOptionalParseTitleWithNullInput() {
        assertNull(inputProcessor.optionalParseTitle(null));
    }

    /**
     * Tests that optionalParseTitle correctly handles blank input by returning null.
     */
    @Test
    void testOptionalParseTitleWithBlankInput() {
        assertNull(inputProcessor.optionalParseTitle("   "));
    }

    /**
     * Tests that optionalParseDirector correctly processes valid input by returning a trimmed string.
     */
    @Test
    void testOptionalParseDirectorWithValidInput() {
        assertEquals("Christopher Nolan", inputProcessor.optionalParseDirector("  Christopher Nolan  "));
    }

    /**
     * Tests that optionalParseDirector correctly handles null input by returning null.
     */
    @Test
    void testOptionalParseDirectorWithNullInput() {
        assertNull(inputProcessor.optionalParseDirector(null));
    }

    /**
     * Tests that optionalParseDirector correctly handles blank input by returning null.
     */
    @Test
    void testOptionalParseDirectorWithBlankInput() {
        assertNull(inputProcessor.optionalParseDirector("   "));
    }

    /**
     * Tests that optionalParseCast correctly processes valid input by returning a trimmed string.
     */
    @Test
    void testOptionalParseCastWithValidInput() {
        assertEquals("Tom Hanks", inputProcessor.optionalParseCast("  Tom Hanks  "));
    }

    /**
     * Tests that optionalParseCast correctly handles null input by returning null.
     */
    @Test
    void testOptionalParseCastWithNullInput() {
        assertNull(inputProcessor.optionalParseCast(null));
    }

    /**
     * Tests that optionalParseCast correctly handles blank input by returning null.
     */
    @Test
    void testOptionalParseCastWithBlankInput() {
        assertNull(inputProcessor.optionalParseCast("   "));
    }

    /**
     * Tests that optionalParseYear correctly processes valid numeric input by returning an Integer.
     */
    @Test
    void testOptionalParseYearWithValidInput() {
        assertEquals(Integer.valueOf(2010), inputProcessor.optionalParseYear("2010"));
    }

    /**
     * Tests that optionalParseYear correctly processes input with whitespace by trimming and returning an Integer.
     */
    @Test
    void testOptionalParseYearWithPaddedInput() {
        assertEquals(Integer.valueOf(1995), inputProcessor.optionalParseYear("  1995  "));
    }

    /**
     * Tests that optionalParseYear correctly handles null input by returning null.
     */
    @Test
    void testOptionalParseYearWithNullInput() {
        assertNull(inputProcessor.optionalParseYear(null));
    }

    /**
     * Tests that optionalParseYear correctly handles blank input by returning null.
     */
    @Test
    void testOptionalParseYearWithBlankInput() {
        assertNull(inputProcessor.optionalParseYear("   "));
    }

    /**
     * Tests that optionalParseYear correctly throws an exception when given non-numeric input.
     */
    @Test
    void testOptionalParseYearWithNonNumericInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inputProcessor.optionalParseYear("not-a-year"));
        assertEquals("Year must be a number.", exception.getMessage());
    }

    /**
     * Tests that optionalParseYear correctly throws an exception when given years outside the valid range.
     *
     * @param invalidYear A year value outside the valid range (1800-2025)
     */
    @ParameterizedTest
    @ValueSource(strings = {"1799", "2026"})
    void testOptionalParseYearWithOutOfRangeYear(String invalidYear) {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                inputProcessor.optionalParseYear(invalidYear));
        assertEquals("Year must be between 1800 and 2025.", exception.getMessage());
    }

    /**
     * Tests that optionalParseGenre correctly processes valid input by returning a matching Genre enum.
     */
    @Test
    void testOptionalParseGenreWithValidInput() {
        // This test depends on the actual implementation of Genre.fromName
        // Assuming "ACTION" is a valid genre enum value
        assertEquals(Genre.fromName("ACTION"), inputProcessor.optionalParseGenre("  ACTION  "));
    }

    /**
     * Tests that optionalParseGenre correctly handles null input by returning null.
     */
    @Test
    void testOptionalParseGenreWithNullInput() {
        assertNull(inputProcessor.optionalParseGenre(null));
    }

    /**
     * Tests that optionalParseGenre correctly handles blank input by returning null.
     */
    @Test
    void testOptionalParseGenreWithBlankInput() {
        assertNull(inputProcessor.optionalParseGenre("   "));
    }
}
