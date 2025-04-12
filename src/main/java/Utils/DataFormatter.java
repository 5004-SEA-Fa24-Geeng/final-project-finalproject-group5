package Utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import javax.annotation.Nonnull;

import Model.Format;
import Model.Movie;
import Model.Genre;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * A class to format movie data in different ways.
 */
public final class DataFormatter {

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
    public static String formatSingleMovie(@Nonnull Movie movie) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTitle: ").append(movie.getTitle()).append("\n");
        sb.append("Year: ").append(movie.getYear()).append("\n");
        sb.append("Rating: ").append(movie.getRating()).append("\n");
        sb.append("Overview: ").append(movie.getOverview()).append("\n");

        // Format directors
        sb.append("Directors: ");
        if (movie.getDirectors().isEmpty()) {
            sb.append("Unknown\n");
        } else {
            sb.append(String.join(", ", movie.getDirectors())).append("\n");
        }

        // Format genres
        sb.append("Genres: ");
        if (movie.getGenres().isEmpty()) {
            sb.append("Unknown\n");
        } else {
            StringBuilder genreBuilder = new StringBuilder();
            boolean first = true;
            for (Genre genre : movie.getGenres()) {
                if (!first) {
                    genreBuilder.append(", ");
                }
                first = false;
                genreBuilder.append(genre.toString());
            }
            sb.append(genreBuilder.toString()).append("\n");
        }

        // Format castings
        sb.append("Cast: ");
        if (movie.getCastings().isEmpty()) {
            sb.append("Unknown\n");
        } else {
            sb.append(String.join(", ", movie.getCastings())).append("\n");
        }

        // Format comments
        if (!movie.getComments().isEmpty()) {
            sb.append("Comments:\n");
            for (String comment : movie.getComments()) {
                sb.append("  - ").append(comment).append("\n");
            }
        }

        // Format app rating
        double averageRating = movie.getInAppRating();
        sb.append("App Rating: ").append(String.format("%.1f", averageRating)).append("\n");


        // Format image URL
        if (movie.getImgUrl() != null && !movie.getImgUrl().isEmpty()) {
            sb.append("Poster: ").append(movie.getImgUrl()).append("\n");
        }



        return sb.toString();  // Return the formatted movie data as a string
    }


    /**
     * Format a collection of movies as a human-readable string.
     *
     * @param movies the movies to format
     * @return formatted string of the movie list
     */
    public static String formatMovieList(Collection<Movie> movies) {
        StringBuilder sb = new StringBuilder();
        for (Movie movie : movies) {
            sb.append(formatSingleMovie(movie));  // Use formatSingleMovie for each movie
            sb.append("-------------------\n");
        }
        return sb.toString();  // Return the formatted movie list as a string
    }


    /**
     * Write the movie data as XML.
     * Uses XmlMapper to wrap movies into XMLWrapper objects.
     *
     * @param movies the movies to write
     * @param out the output stream to write to
     */
    private static void writeXmlData(Collection<Movie> movies, OutputStream out) {
        try {
            XmlMapper mapper = new XmlMapper();
            MovieXMLWrapper wrapper = new MovieXMLWrapper(movies);
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, wrapper);
        } catch (Exception e) {
            System.err.println("Error writing XML data: " + e.getMessage());
        }
    }


    /**
     * Write the movie data as JSON.
     * Uses ObjectMapper to serialize movies directly to JSON.
     *
     * @param movies the movies to write
     * @param out the output stream to write to
     */
    private static void writeJsonData(Collection<Movie> movies, OutputStream out) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, movies);
        } catch (Exception e) {
            System.err.println("Error writing JSON data: " + e.getMessage());
        }
    }


    /**
     * Write the movie data as CSV.
     *
     * @param movies the movies to write
     * @param out the output stream to write to
     */
    private static void writeCsvData(Collection<Movie> movies, OutputStream out) {
        PrintStream pout = new PrintStream(out);

        // Write header
        pout.println("Title,Year,Rating,Overview,Directors,Genres,Castings,Comments,InAppRating,ImgUrl");

        for (Movie movie : movies) {
            pout.printf("%s,%d,%.1f,%s,%s,%s,%s,%s,%.1f,%s%n",
                    formatCsvField(movie.getTitle()),
                    movie.getYear(),
                    movie.getRating(),
                    formatCsvField(movie.getOverview()),
                    formatCsvField(joinDirectors(movie)),
                    formatCsvField(joinGenres(movie)),
                    formatCsvField(joinCastings(movie)),
                    formatCsvField(joinComments(movie)),
                    movie.getInAppRating(),
                    formatCsvField(movie.getImgUrl()));
        }
    }

    /**
     * Join directors into a semicolon-separated string.
     */
    private static String joinDirectors(Movie movie) {
        return String.join("; ", movie.getDirectors());
    }

    /**
     * Join genres into a semicolon-separated string.
     */
    private static String joinGenres(Movie movie) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Genre genre : movie.getGenres()) {
            if (!first) {
                result.append("; ");
            }
            first = false;
            result.append(genre.toString());
        }
        return result.toString();
    }

    /**
     * Join castings into a semicolon-separated string.
     */
    private static String joinCastings(Movie movie) {
        return String.join("; ", movie.getCastings());
    }

    /**
     * Join comments into a semicolon-separated string.
     */
    private static String joinComments(Movie movie) {
        return String.join("; ", movie.getComments());
    }

    /**
     * Format CSV fields and handle special characters.
     *
     * @param field the CSV field to format
     * @return formatted CSV field
     */
    private static String formatCsvField(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            return "\"" + field + "\"";
        }
        return field;
    }

    /**
     * Write the movie data in the specified format.
     *
     * @param movies the movies to write
     * @param format the format to write the movies in
     * @param out the output stream to write to
     */
    public static void write(@Nonnull Collection<Movie> movies, @Nonnull Format format,
                             @Nonnull OutputStream out) {
        switch (format) {
            case XML:
                writeXmlData(movies, out);
                break;
            case JSON:
                writeJsonData(movies, out);
                break;
            case CSV:
                writeCsvData(movies, out);
                break;
            default:
                String formattedMovies = formatMovieList(movies);
                try (PrintStream ps = new PrintStream(out)) {
                    ps.print(formattedMovies);
                } catch (Exception e) {
                    System.err.println("Error writing formatted data: " + e.getMessage());
                }
                break;
        }
    }
}


