package com.moviefeaster.model;

/**
 * Enum represents the input and output file format.
 */
public enum Format {
    /** Different formatting options. */
    JSON, XML, CSV, PRETTY;

    /**
     * Helper function to check if a value is in the list of formats.
     *
     * @param value the value to check
     * @return the format if found, null otherwise
     */
    public static Format containsValues(final String value) {
        Format foundFormat = null;
        for (final Format format : Format.values()) {
            if (format.toString().equalsIgnoreCase(value)) {
                foundFormat = format;
                break;
            }
        }
        return foundFormat;
    }
}
