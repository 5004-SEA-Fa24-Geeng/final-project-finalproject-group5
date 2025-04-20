package com.moviefeaster.model;

/**
 * Enum representing available movie sorting strategies.
 */
public enum MovieSorterType {
    /** Sort movies by title in ascending order. */
    TITLE_ASC("title_asc"),
    
    /** Sort movies by title in descending order. */
    TITLE_DESC("title_desc"),
    
    /** Sort movies by release year in ascending order. */
    YEAR_ASC("year_asc"),
    
    /** Sort movies by release year in descending order. */
    YEAR_DESC("year_desc"),
    
    /** Sort movies by rating in ascending order. */
    RATING_ASC("rating_asc"),
    
    /** Sort movies by rating in descending order. */
    RATING_DESC("rating_desc"),
    
    /** Sort movies by in-app rating in ascending order. */
    INAPP_RATING_ASC("inapp_rating_asc"),
    
    /** Sort movies by in-app rating in descending order. */
    INAPP_RATING_DESC("inapp_rating_desc");

    /**
     * The string representation of the sorting type.
     */
    private final String value;

    /**
     * Constructs an enum constant with the associated string value.
     *
     * @param value the string identifier for the sorting type
     */
    MovieSorterType(final String value) {
        this.value = value;
    }

    /**
     * Returns the string value associated with this sorting type.
     *
     * @return the string representation (e.g., "rating_desc")
     */
    public String getValue() {
        return value;
    }

    /**
     * Get the enum constant from the string value.
     *
     * @param value the string representation like "title_asc"
     * @return matching MovieSorterType or null if not found
     */
    public static MovieSorterType fromValue(final String value) {
        MovieSorterType result = null;
        for (final MovieSorterType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                result = type;
                break;
            }
        }
        return result;
    }
}
