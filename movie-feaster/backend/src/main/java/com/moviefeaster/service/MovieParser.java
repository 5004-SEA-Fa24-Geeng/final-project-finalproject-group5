package com.moviefeaster.service;

import com.moviefeaster.model.*;
import com.moviefeaster.utils.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * Utility class to parse movies from a JSON InputStream.
 */
@Service
public final class MovieParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieParser.class);

    /** The deserialized and cleaned list of Movies. */
    private static final List<Movie> movies = new ArrayList<>();

    /** The placeholder class for original JSON structure */
    private static List<MovieSummary> moviesSummary;

    /** The number of movies we're trying to parse */
    private static final int NUMBER_OF_MOVIES = 200;

    /** The root image URL to TMDB poster. */
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    /** Private constructor to prevent instantiation. */
    private MovieParser() {}

    /** Get the TOP 50 movie list of the month from TMDB api. */
    public static List<Movie> getMoviesFromApi() {
        try {
            parseMovies();
            cleanMovieSummary();
        } catch (IOException e) {
            LOGGER.error("Failed to parse movies from API", e);
        }
        return movies;
    }

    /**
     * Getter for the parsed movies with original form.
     */
    public static Collection<MovieSummary> getParsedMoviesSummary() {
        parseMovies();
        return moviesSummary == null ? Collections.emptyList() : moviesSummary;
    }

    /**
     * Parses the top N movies JSON into a list of MovieSummary objects.
     */
    private static void parseMovies() {
        try (InputStream jsonStream = NetUtil.getTop50MoviesJson()) {
            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode root = mapper.readTree(jsonStream);

            if (root.isArray()) {
                moviesSummary = mapper.readerForListOf(MovieSummary.class).readValue(root);
            } else {
                final JsonNode results = root.path("results");
                moviesSummary = mapper.readerForListOf(MovieSummary.class).readValue(results);
            }
        } catch (IOException e) {
            LOGGER.error("Error while parsing movie summary", e);
            moviesSummary = Collections.emptyList();
        }
    }

    /**
     * Transfer a movie type from the original JSON form to a conform movie type.
     * @throws IOException If the NetUtil unable to connect TMDB api to acquire a crew list by ID.
     */
    private static void cleanMovieSummary() throws IOException {
        for (final MovieSummary movie : moviesSummary) {
            final int movieId = movie.getMovieID();
            final String title = movie.getTitle();

            final String releaseDate = movie.getReleaseDate();
            final LocalDate date = LocalDate.parse(releaseDate);
            final int year = date.getYear();

            final List<Genre> genre = convertGenreIds(movie.getGenreID());
            final String overview = movie.getOverview();
            final List<String> directors = new ArrayList<>();
            final List<String> castings = new ArrayList<>();
            final double rating = movie.getRating();
            final String imgUrl = IMAGE_BASE_URL + movie.getPosterPath();

            try (InputStream crewJson = NetUtil.getCrewJsonStream(movie.getMovieID())) {
                if (crewJson != null) {
                    final ObjectMapper mapper = new ObjectMapper();
                    final JsonNode root = mapper.readTree(crewJson);

                    final JsonNode crewArray = root.get("crew");
                    for (final JsonNode member : crewArray) {
                        if ("Director".equals(member.get("job").asText())) {
                            directors.add(member.get("name").asText());
                        }
                    }

                    final JsonNode castArray = root.get("cast");
                    for (final JsonNode actor : castArray) {
                        final String actorName = actor.get("name").asText();
                        castings.add(actorName);
                    }
                }
            }

            movies.add(new Movie(movieId, title, directors, year, rating, genre, overview, castings, imgUrl));

            if (movies.size() >= NUMBER_OF_MOVIES) {
                break;
            }
        }
    }

    /** Converts genre IDs into a list of Genre enums. */
    private static List<Genre> convertGenreIds(final Set<Integer> genreIds) {
        final List<Genre> genres = new ArrayList<>();
        for (final Integer id : genreIds) {
            final Genre genre = Genre.fromId(id);
            if (genre != null) {
                genres.add(genre);
            }
        }
        return genres;
    }

    /** Inner class to map individual movie entries from TMDb API. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieSummary {

        @JsonProperty("id")
        private int movieID;

        @JsonProperty("title")
        private String title;

        @JsonProperty("overview")
        private String overview;

        @JsonProperty("release_date")
        private String releaseDate;

        @JsonProperty("popularity")
        private double rating;

        @JsonProperty("genre_ids")
        private Set<Integer> genreID;

        @JsonProperty("poster_path")
        private String posterPath;

        public int getMovieID() {
            return movieID;
        }

        public void setMovieID(final int movieID) {
            this.movieID = movieID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(final String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(final String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(final double rating) {
            this.rating = rating;
        }

        public Set<Integer> getGenreID() {
            return genreID;
        }

        public void setGenreID(final Set<Integer> genreID) {
            this.genreID = genreID;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(final String posterPath) {
            this.posterPath = posterPath;
        }

        @Override
        public String toString() {
            return "MovieSummary{" +
                    "movieID=" + movieID +
                    ", title='" + title + '\'' +
                    ", overview='" + overview + '\'' +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", genreID=" + genreID +
                    ", posterPath='" + posterPath + '\'' +
                    '}';
        }
    }
}
