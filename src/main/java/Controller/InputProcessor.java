package Controller;

import Model.Genre;

/**
 * InputProcessor is responsible for parsing and validating user input
 * received from the view. All parsing methods are optional, meaning
 * that blank input will return null. Invalid formats will raise exceptions.
 */
public class InputProcessor implements InputProcessorInterface {

    /**
     * Parses the movie name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed name or null.
     */
    @Override
    public String optionalParseName(String input) {
        return (input == null || input.isBlank()) ? null : input.trim();
    }

    /**
     * Parses the director name input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed director name or null.
     */
    @Override
    public String optionalParseDirector(String input) {
        return (input == null || input.isBlank()) ? null : input.trim();
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
    public Integer optionalParseYear(String input) {
        if (input == null || input.isBlank()) return null;
        try {
            int year = Integer.parseInt(input.trim());
            if (year < 1800 || year > 2025)
                throw new IllegalArgumentException("Year must be between 1800 and 2025.");
            return year;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year must be a number.");
        }
    }

    /**
     * Parses the type/genre input. Returns null if input is blank.
     *
     * @param input The raw user input.
     * @return Trimmed and lowercased type or null.
     */
    @Override
    public Genre optionalParseType(String input) {
        return Genre.fromName(input.trim());
    }
}
