package Model.Net;

import Model.Genre;
import Model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Utility class to parse movies from a JSON InputStream.
 */
public final class MovieParser {

    /** The deserialized list of Movies. */
    private static List<Movie> movies;

    /** Private constructor to prevent instantiation. */
    private MovieParser() {}

    /**
     * Parses the top 50 movies JSON into a list of Movie objects.
     */
    public static void parseMovies() {
        try (InputStream jsonStream = NetUtil.getTop50MoviesJson()) {
            ObjectMapper mapper = new ObjectMapper();
            TMDbResponse response = mapper.readValue(jsonStream, TMDbResponse.class);
            List<MovieSummary> summaries = response.getResults();

            movies = new ArrayList<>();
            for (MovieSummary summary : summaries) {
                movies.add(convertToMovie(summary));
            }
        } catch (IOException e) {
            e.printStackTrace();
            movies = Collections.emptyList();
        }
    }

    /**
     * Getter for the parsed movies.
     */
    public static Collection<Movie> getParsedMovies() {
        parseMovies();
        return movies == null ? Collections.emptyList() : movies;
    }

    private static Movie convertToMovie(MovieSummary summary) {
        String title = summary.title;
        List<String> directors = new ArrayList<>(); // TMDb summary doesn’t include this
        int year = 1000;
        float rating = 1.1f;
        List<Genre> genres = convertGenreIds(new ArrayList<>(summary.genreID));
        List<String> castings = new ArrayList<>(); // Not available in summary
        String imgUrl = (summary.posterPath != null)
                ? "https://image.tmdb.org/t/p/w500" + summary.posterPath
                : "";

        return new Movie(title, directors, year, rating, genres, castings, imgUrl);
    }

    /** Converts genre IDs into a list of Genre enums. */
    private static List<Genre> convertGenreIds(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genreIds) {
            Genre g = Genre.fromId(id);
            if (g != null) genres.add(g);
        }
        return genres;
    }

    /** Wrapper class for TMDb API response. */
    public static class TMDbResponse {
        @JsonProperty("results")
        private List<MovieSummary> results;

        public List<MovieSummary> getResults() {
            return results;
        }

        public void setResults(List<MovieSummary> results) {
            this.results = results;
        }
    }

    /** Inner class to map individual movie entries from TMDb API. */
    public static class MovieSummary {

        @JsonProperty("id")
        private int movieID;

        @JsonProperty("title")
        private String title;

        @JsonProperty("overview")
        private String overview;

        @JsonProperty("genre_ids")
        private Set<Integer> genreID;

        @JsonProperty("poster_path")
        private String posterPath;

        @Override
        public String toString() {
            return "MovieSummary{" +
                    "movieID=" + movieID +
                    ", title='" + title + '\'' +
                    ", overview='" + overview + '\'' +
                    ", genreID=" + genreID +
                    ", posterPath='" + posterPath + '\'' +
                    '}';
        }
    }
}