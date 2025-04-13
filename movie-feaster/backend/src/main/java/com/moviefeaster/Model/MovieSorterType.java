package com.moviefeaster.Model;

/**
 * Enum representing available movie sorting strategies.
 */
public enum MovieSorterType {
    TITLE_ASC("title_asc"),
    TITLE_DESC("title_desc"),
    YEAR_ASC("year_asc"),
    YEAR_DESC("year_desc"),
    RATING_ASC("rating_asc"),
    RATING_DESC("rating_desc"),
    INAPP_RATING_ASC("inapp_rating_asc"),
    INAPP_RATING_DESC("inapp_rating_desc");

    private final String value;

    MovieSorterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Get the enum constant from the string value.
     *
     * @param value the string representation like "title_asc"
     * @return matching MovieSorterType or null if not found
     */
    public static MovieSorterType fromValue(String value) {
        for (MovieSorterType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}