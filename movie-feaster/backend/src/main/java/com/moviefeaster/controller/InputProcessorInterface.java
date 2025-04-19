package com.moviefeaster.controller;

import com.moviefeaster.model.*;

/**
 * Interface for parsing and validating user input from the view
 * before it is used in the controller or model.
 */
public interface InputProcessorInterface {

    /**
     * Parses the movie name input. Returns null if the input is blank.
     *
     * @param input The raw input from the user.
     * @return Trimmed movie name or null.
     */
    String optionalParseTitle(String input);

    /**
     * Parses the director name input. Returns null if the input is blank.
     *
     * @param input The raw input from the user.
     * @return Trimmed director name or null.
     */
    String optionalParseDirector(String input);

    /**
     * Parses the cast name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed cast name or null.
     */
    String optionalParseCast(String input);

    /**
     * Parses and validates the year input.
     * Returns null if blank; throws IllegalArgumentException if invalid.
     *
     * @param input The raw year input from the user.
     * @return Parsed year as Integer, or null.
     * @throws IllegalArgumentException if year is not a number or out of valid range.
     */
    Integer optionalParseYear(String input);

    /**
     * Parses the type/genre input. Returns null if blank.
     *
     * @param input The raw input from the user.
     * @return Trimmed and lowercased type Genre or null.
     */
    Genre optionalParseGenre(String input);
}
