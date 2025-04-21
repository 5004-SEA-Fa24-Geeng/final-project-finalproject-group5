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

    /** Logger instance for logging events and errors. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieParser.class);

    /** The deserialized and cleaned list of Movies. */
    private static final List<Movie> MOVIES = new ArrayList<>();

    /** The placeholder class for original JSON structure. */
    private static List<MovieSummary> moviesSummary;

    /** The number of movies we're trying to parse. */
    private static final int NUMBER_OF_MOVIES = 200;

    /** The root image URL to TMDB poster. */
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    /** Private constructor to prevent instantiation. */
    private MovieParser() { }

    /**
     * Get the TOP 50 movie list of the month from TMDB api.
     *
     * @return List of parsed movies
     */
    public static List<Movie> getMoviesFromApi() {
        try {
            parseMovies();
            cleanMovieSummary();
        } catch (IOException e) {
            LOGGER.error("Failed to parse movies from API", e);
        }
        return MOVIES;
    }

    /**
     * Getter for the parsed movies with original form.
     *
     * @return Collection of movie summaries
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
     *
     * @throws IOException If the NetUtil unable to connect TMDB api to acquire a crew list by ID
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

            Movie movieToAdd = new Movie.Builder()
                    .movieId(movieId)
                    .title(title)
                    .directors(directors)
                    .year(year)
                    .rating(rating)
                    .genres(genre)
                    .overview(overview)
                    .castings(castings)
                    .imgUrl(imgUrl)
                    .build();
            MOVIES.add(movieToAdd);

            if (MOVIES.size() >= NUMBER_OF_MOVIES) {
                break;
            }
        }
    }

    /**
     * Converts genre IDs into a list of Genre enums.
     *
     * @param genreIds Set of genre IDs to convert
     * @return List of corresponding Genre enums
     */
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

        /** The movie ID from TMDb API. */
        @JsonProperty("id")
        private int movieID;

        /** The title of the movie. */
        @JsonProperty("title")
        private String title;

        /** The overview/description of the movie. */
        @JsonProperty("overview")
        private String overview;

        /** The release date of the movie. */
        @JsonProperty("release_date")
        private String releaseDate;

        /** The popularity rating of the movie. */
        @JsonProperty("popularity")
        private double rating;

        /** The genre IDs associated with the movie. */
        @JsonProperty("genre_ids")
        private Set<Integer> genreID;

        /** The path to the movie's poster image. */
        @JsonProperty("poster_path")
        private String posterPath;

        /**
         * Gets the movie ID.
         * @return the movie ID
         */
        public int getMovieID() {
            return movieID;
        }

        /**
         * Sets the movie ID.
         * @param movieID the movie ID to set
         */
        public void setMovieID(final int movieID) {
            this.movieID = movieID;
        }

        /**
         * Gets the movie title.
         * @return the movie title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the movie title.
         * @param title the movie title to set
         */
        public void setTitle(final String title) {
            this.title = title;
        }

        /**
         * Gets the movie overview.
         * @return the movie overview
         */
        public String getOverview() {
            return overview;
        }

        /**
         * Sets the movie overview.
         * @param overview the movie overview to set
         */
        public void setOverview(final String overview) {
            this.overview = overview;
        }

        /**
         * Gets the release date.
         * @return the release date
         */
        public String getReleaseDate() {
            return releaseDate;
        }

        /**
         * Sets the release date.
         * @param releaseDate the release date to set
         */
        public void setReleaseDate(final String releaseDate) {
            this.releaseDate = releaseDate;
        }

        /**
         * Gets the movie rating.
         * @return the movie rating
         */
        public double getRating() {
            return rating;
        }

        /**
         * Sets the movie rating.
         * @param rating the movie rating to set
         */
        public void setRating(final double rating) {
            this.rating = rating;
        }

        /**
         * Gets the genre IDs.
         * @return the genre IDs
         */
        public Set<Integer> getGenreID() {
            return genreID;
        }

        /**
         * Sets the genre IDs.
         * @param genreID the genre IDs to set
         */
        public void setGenreID(final Set<Integer> genreID) {
            this.genreID = genreID;
        }

        /**
         * Gets the poster path.
         * @return the poster path
         */
        public String getPosterPath() {
            return posterPath;
        }

        /**
         * Sets the poster path.
         * @param posterPath the poster path to set
         */
        public void setPosterPath(final String posterPath) {
            this.posterPath = posterPath;
        }

        @Override
        public String toString() {
            return "MovieSummary{"
                    + "movieID=" + movieID
                    + ", title='" + title + '\''
                    + ", overview='" + overview + '\''
                    + ", releaseDate='" + releaseDate + '\''
                    + ", genreID=" + genreID
                    + ", posterPath='" + posterPath + '\''
                    + '}';
        }
    }
}
