package com.moviefeaster.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link MovieSorterType} enum.
 *
 * This class contains unit tests to verify the functionality of the MovieSorterType enum,
 * including testing the getValue() method and the fromValue() method for both valid and
 * invalid inputs.
 */
public class MovieSorterTypeTest {

    /**
     * Tests that the getValue() method returns the correct string representation for each enum constant.
     *
     * This test verifies that each enum constant in MovieSorterType has the expected string value
     * as defined in the enum constructor.
     */
    @Test
    public void testGetValue() {
        // Test each enum constant's getValue() method
        assertEquals("title_asc", MovieSorterType.TITLE_ASC.getValue());
        assertEquals("title_desc", MovieSorterType.TITLE_DESC.getValue());
        assertEquals("year_asc", MovieSorterType.YEAR_ASC.getValue());
        assertEquals("year_desc", MovieSorterType.YEAR_DESC.getValue());
        assertEquals("rating_asc", MovieSorterType.RATING_ASC.getValue());
        assertEquals("rating_desc", MovieSorterType.RATING_DESC.getValue());
        assertEquals("inapp_rating_asc", MovieSorterType.INAPP_RATING_ASC.getValue());
        assertEquals("inapp_rating_desc", MovieSorterType.INAPP_RATING_DESC.getValue());
    }

    /**
     * Tests that the fromValue() method correctly converts string representations to their
     * corresponding enum constants.
     *
     * This test verifies that the fromValue() method works correctly for all valid string values,
     * returning the expected enum constant.
     */
    @Test
    public void testFromValue() {
        // Test successful conversion from string to enum constant
        assertEquals(MovieSorterType.TITLE_ASC, MovieSorterType.fromValue("title_asc"));
        assertEquals(MovieSorterType.TITLE_DESC, MovieSorterType.fromValue("title_desc"));
        assertEquals(MovieSorterType.YEAR_ASC, MovieSorterType.fromValue("year_asc"));
        assertEquals(MovieSorterType.YEAR_DESC, MovieSorterType.fromValue("year_desc"));
        assertEquals(MovieSorterType.RATING_ASC, MovieSorterType.fromValue("rating_asc"));
        assertEquals(MovieSorterType.RATING_DESC, MovieSorterType.fromValue("rating_desc"));
        assertEquals(MovieSorterType.INAPP_RATING_ASC, MovieSorterType.fromValue("inapp_rating_asc"));
        assertEquals(MovieSorterType.INAPP_RATING_DESC, MovieSorterType.fromValue("inapp_rating_desc"));
    }

    /**
     * Tests that the fromValue() method correctly handles case-insensitive string inputs.
     *
     * This test verifies that the fromValue() method works correctly with mixed-case strings,
     * since the implementation uses equalsIgnoreCase() for comparison.
     */
    @Test
    public void testFromValueCaseInsensitive() {
        assertEquals(MovieSorterType.TITLE_ASC, MovieSorterType.fromValue("TITLE_ASC"));
        assertEquals(MovieSorterType.RATING_DESC, MovieSorterType.fromValue("Rating_Desc"));
        assertEquals(MovieSorterType.YEAR_ASC, MovieSorterType.fromValue("YeAr_AsC"));
    }

    /**
     * Tests that the fromValue() method returns null for invalid string inputs.
     *
     * This test verifies that the fromValue() method returns null when provided with string values
     * that do not correspond to any MovieSorterType enum constant.
     */
    @Test
    public void testFromValueInvalid() {
        // Test invalid values return null
        assertNull(MovieSorterType.fromValue("invalid_value"));
        assertNull(MovieSorterType.fromValue(""));
        assertNull(MovieSorterType.fromValue(null));
    }

    /**
     * Tests that all available enum constants can be retrieved using the values() method.
     *
     * This test verifies that the values() method returns all expected enum constants
     * and that the expected number of constants exists.
     */
    @Test
    public void testValues() {
        MovieSorterType[] types = MovieSorterType.values();

        // Verify we have the expected number of enum constants
        assertEquals(8, types.length);

        // Verify all expected constants are present
        boolean hasTitleAsc = false;
        boolean hasRatingDesc = false;

        for (MovieSorterType type : types) {
            if (type == MovieSorterType.TITLE_ASC) {
                hasTitleAsc = true;
            } else if (type == MovieSorterType.RATING_DESC) {
                hasRatingDesc = true;
            }
        }

        assertTrue(hasTitleAsc, "MovieSorterType.TITLE_ASC should be present in values()");
        assertTrue(hasRatingDesc, "MovieSorterType.RATING_DESC should be present in values()");
    }
}
