package com.moviefeaster.controller;

import com.moviefeaster.model.*;
import org.springframework.stereotype.Service;

/**
 * InputProcessor is responsible for parsing and validating user input
 * received from the view. All parsing methods are optional, meaning
 * that blank input will return null. Invalid formats will raise exceptions.
 */
@Service
public class InputProcessor implements InputProcessorInterface {

    /**
     * Private constructor.
     */
    InputProcessor() {
    }

    /**
     * Parses the movie name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed name or null.
     */
    @Override
    public String optionalParseTitle(final String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return input.trim();
    }

    /**
     * Parses the director name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed director name or null.
     */
    @Override
    public String optionalParseDirector(final String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return input.trim();
    }

    /**
     * Parses the cast name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed cast name or null.
     */
    @Override
    public String optionalParseCast(final String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return input.trim();
    }

    /**
     * Parses the year input. Returns null if input is blank.
     * Throws IllegalArgumentException if input is not a valid year.
     *
     * @param input The raw user input.
     * @return Parsed year as Integer or null.
     * @throws IllegalArgumentException If year is not numeric or out of range.
     */
    @Override
    public Integer optionalParseYear(final String input) {
        Integer parsedYear = null;
        if (input != null && !input.isBlank()) {
            try {
                final int year = Integer.parseInt(input.trim());
                if (year < 1800 || year > 2025) {
                    throw new IllegalArgumentException("Year must be between 1800 and 2025.");
                }
                parsedYear = year;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Year must be a number.", e);
            }
        }
        return parsedYear;
    }

    /**
     * Parses the type/genre input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed and matched Genre or null.
     */
    @Override
    public Genre optionalParseGenre(final String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        return Genre.fromName(input.trim());
    }
}
