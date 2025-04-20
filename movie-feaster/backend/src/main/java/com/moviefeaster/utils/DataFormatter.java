package com.moviefeaster.utils;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import javax.annotation.Nonnull;
import java.util.logging.Logger;

import com.moviefeaster.model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * A class to format movie data in different ways.
 */
public final class DataFormatter {

    /** Logger instance for the DataFormatter class. */
    private static final Logger LOGGER = Logger.getLogger(DataFormatter.class.getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private DataFormatter() {
        // empty
    }

    /**
     * Format a single movie record as a human-readable string.
     *
     * @param movie the movie to format
     * @return formatted string of the movie data
     */
    public static String formatSingleMovie(final @Nonnull Movie movie) {
        final StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("\nTitle: ").append(movie.getTitle()).append("\n");
        descriptionBuilder.append("Year: ").append(movie.getYear()).append("\n");
        descriptionBuilder.append("Rating: ").append(movie.getRating()).append("\n");

        // Format directors
        descriptionBuilder.append("Directors: ");
        if (movie.getDirectors().isEmpty()) {
            descriptionBuilder.append("Unknown\n");
        } else {
            descriptionBuilder.append(String.join(", ", movie.getDirectors())).append("\n");
        }

        // Format genres
        descriptionBuilder.append("Genres: ");
        if (movie.getGenres().isEmpty()) {
            descriptionBuilder.append("Unknown\n");
        } else {
            final StringBuilder genreBuilder = new StringBuilder();
            boolean isFirstGenre = true;
            for (final Genre genre : movie.getGenres()) {
                if (!isFirstGenre) {
                    genreBuilder.append(", ");
                }
                isFirstGenre = false;
                genreBuilder.append(genre.toString());
            }
            descriptionBuilder.append(genreBuilder).append("\n");
        }

        // Format castings
        descriptionBuilder.append("Cast: ");
        if (movie.getCastings().isEmpty()) {
            descriptionBuilder.append("Unknown\n");
        } else {
            descriptionBuilder.append(String.join(", ", movie.getCastings())).append("\n");
        }

        // Format comments
        if (!movie.getComments().isEmpty()) {
            descriptionBuilder.append("Comments:\n");
            for (final String comment : movie.getComments()) {
                descriptionBuilder.append("  - ").append(comment).append("\n");
            }
        }

        descriptionBuilder.append("App Rating: ").append(movie.getInAppRating())
                .append(" (Total ratings: ").append(movie.getInAppRating()).append(")\n");

        return descriptionBuilder.toString();
    }

    /**
     * Format a collection of movies as a human-readable string.
     *
     * @param movies the movies to format
     * @return formatted string of the movie list
     */
    public static String formatMovieList(final Collection<Movie> movies) {
        final StringBuilder movieListBuilder = new StringBuilder();
        for (final Movie movie : movies) {
            movieListBuilder.append(formatSingleMovie(movie));
            movieListBuilder.append("-------------------\n");
        }
        return movieListBuilder.toString();
    }

    private static void writeXmlData(final Collection<Movie> movies, final OutputStream outputStream) {
        try {
            final XmlMapper mapper = new XmlMapper();
            final MovieXMLWrapper wrapper = new MovieXMLWrapper(movies);
            mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, wrapper);
        } catch (final Exception e) {
            LOGGER.severe("Error writing XML data: " + e.getMessage());
        }
    }

    private static void writeJsonData(final Collection<Movie> movies, final OutputStream outputStream) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, movies);
        } catch (final Exception e) {
            LOGGER.severe("Error writing JSON data: " + e.getMessage());
        }
    }

    private static void writeCsvData(final Collection<Movie> movies, final OutputStream outputStream) {
        final PrintStream printStream = new PrintStream(outputStream);
        printStream.println("Title,Year,Rating,Directors,Genres,Castings,Comments,InAppRating");

        for (final Movie movie : movies) {
            printStream.printf("%s,%d,%.1f,%s,%s,%s,%s,%.1f%n",
                    formatCsvField(movie.getTitle()),
                    movie.getYear(),
                    movie.getRating(),
                    formatCsvField(joinDirectors(movie)),
                    formatCsvField(joinGenres(movie)),
                    formatCsvField(joinCastings(movie)),
                    formatCsvField(joinComments(movie)),
                    movie.getInAppRating());
        }
    }

    private static String joinDirectors(final Movie movie) {
        return String.join("; ", movie.getDirectors());
    }

    private static String joinGenres(final Movie movie) {
        final StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        for (final Genre genre : movie.getGenres()) {
            if (!isFirst) {
                result.append("; ");
            }
            isFirst = false;
            result.append(genre.toString());
        }
        return result.toString();
    }

    private static String joinCastings(final Movie movie) {
        return String.join("; ", movie.getCastings());
    }

    private static String joinComments(final Movie movie) {
        return String.join("; ", movie.getComments());
    }

    private static String formatCsvField(final String input) {
        String field = input;
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            final String escaped = field.replace("\"", "\"\"");
            return "\"" + escaped + "\"";
        }
        return field;
    }

    /**
     * Write the movie data in the specified format.
     *
     * @param movies the movies to write
     * @param format the format to write the movies in
     * @param outputStream the output stream to write to
     */
    public static void write(final @Nonnull Collection<Movie> movies,
                             final @Nonnull Format format,
                             final @Nonnull OutputStream outputStream) {
        switch (format) {
            case XML:
                writeXmlData(movies, outputStream);
                break;
            case JSON:
                writeJsonData(movies, outputStream);
                break;
            case CSV:
                writeCsvData(movies, outputStream);
                break;
            default:
                final String formattedMovies = formatMovieList(movies);
                try (PrintStream printStream = new PrintStream(outputStream)) {
                    printStream.print(formattedMovies);
                } catch (final Exception e) {
                    LOGGER.severe("Error writing formatted data: " + e.getMessage());
                }
                break;
        }
    }
}
