package Service;

import Model.Genre;
import Model.Movie;
import Utils.NetUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * Utility class to parse movies from a JSON InputStream.
 */
public final class MovieParser {

    /** The deserialized and cleaned list of Movies. */
    private static List<Movie> movies = new ArrayList<>();

    /** The placeholder class for original JSON structure */
    private static List<MovieSummary> moviesSummary;

<<<<<<< HEAD
=======
    /** The number of movies we're trying to parse */
    private static int numberOfMovies = 600;

>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098
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
            e.printStackTrace();
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
<<<<<<< HEAD
     * Parses the top 50 movies JSON into a list of MovieSummary objects.
=======
     * Parses the top N movies JSON into a list of MovieSummary objects.
>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098
     */
    private static void parseMovies() {
        try (InputStream jsonStream = NetUtil.getTop50MoviesJson()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonStream);

<<<<<<< HEAD
            JsonNode results = root.path("results");

            moviesSummary = mapper.readerForListOf(MovieSummary.class).readValue(results);
=======
            if (root.isArray()) {
                // JSON is a raw array
                moviesSummary = mapper.readerForListOf(MovieSummary.class).readValue(root);
            } else {
                // JSON has a "results" wrapper
                JsonNode results = root.path("results");
                moviesSummary = mapper.readerForListOf(MovieSummary.class).readValue(results);
            }
>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098
        } catch (IOException e) {
            e.printStackTrace();
            moviesSummary = Collections.emptyList();
        }
    }

    /**
     * Transfer a movie type from the original JSON form to a conform movie type.
     * @throws IOException If the NetUtil unable to connect TMDB api to acquire a crew list by ID.
     */
    private static void cleanMovieSummary() throws IOException {
        for (MovieSummary movie : moviesSummary) {
            int MovieID = movie.getMovieID();
            String title = movie.getTitle();

            // Parsing the year from the date form
            String releaseDate = movie.getReleaseDate();
            LocalDate date = LocalDate.parse(releaseDate);
            int year = date.getYear();

            List<Genre> genre = convertGenreIds(movie.getGenreID());
            String overview = movie.getOverview();
            List<String> directors = new ArrayList<>();
            List<String> castings = new ArrayList<>();
            double rating = movie.getRating();
            String imgUrl = IMAGE_BASE_URL + movie.getPosterPath();

            // Map crew (directors and castings).
            InputStream crewJson = NetUtil.getCrewJsonStream(movie.getMovieID()); // movie ID
            if (crewJson != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(crewJson);

                JsonNode crewArray = root.get("crew");
                for (JsonNode member : crewArray) {
                    if ("Director".equals(member.get("job").asText())) {
                        directors.add(member.get("name").asText());
                    }
                }

                // Cast
                JsonNode castArray = root.get("cast");
                for (JsonNode actor : castArray) {
                    String actorName = actor.get("name").asText();
                    castings.add(actorName);
                }

            }
            movies.add(new Movie(MovieID, title, directors, year, rating, genre, overview, castings, imgUrl));
<<<<<<< HEAD
=======

            // Break if we've reached our target number of movies
            if (movies.size() >= numberOfMovies) {
                break;
            }
>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098
        }
    }

    /** Converts genre IDs into a list of Genre enums. */
    private static List<Genre> convertGenreIds(Set<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genreIds) {
            Genre g = Genre.fromId(id);
            if (g != null) genres.add(g);
        }
        return genres;
    }

    /** Inner class to map individual movie entries from TMDb API. */
    @JsonIgnoreProperties(ignoreUnknown = true)
<<<<<<< HEAD
    protected static class MovieSummary {
=======
    public static class MovieSummary {
>>>>>>> 7f2050b2caa3bcc0af826d42c144c0b19862b098

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


        /**
         * Gets the movie ID.
         *
         * @return the movie ID
         */
        public int getMovieID() {
            return movieID;
        }

        /**
         * Sets the movie ID.
         *
         * @param movieID the movie ID to set
         */
        public void setMovieID(int movieID) {
            this.movieID = movieID;
        }

        /**
         * Gets the title of the movie.
         *
         * @return the movie title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the title of the movie.
         *
         * @param title the movie title to set
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gets the overview of the movie.
         *
         * @return the movie overview
         */
        public String getOverview() {
            return overview;
        }

        /**
         * Sets the overview of the movie.
         *
         * @param overview the movie overview to set
         */
        public void setOverview(String overview) {
            this.overview = overview;
        }

        /**
         * Gets the release date of the movie.
         *
         * @return the release date as a string
         */
        public String getReleaseDate() {
            return releaseDate;
        }

        /**
         * Sets the release date of the movie.
         *
         * @param releaseDate the release date to set
         */
        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        /**
         * Gets the popularity rating of the movie.
         *
         * @return the popularity rating as a double
         */
        public double getRating() {
            return rating;
        }

        /**
         * Sets the popularity rating of the movie.
         *
         * @param rating the popularity rating to set
         */
        public void setRating(double rating) {
            this.rating = rating;
        }

        /**
         * Gets the genre IDs associated with the movie.
         *
         * @return a set of genre IDs
         */
        public Set<Integer> getGenreID() {
            return genreID;
        }

        /**
         * Sets the genre IDs associated with the movie.
         *
         * @param genreID a set of genre IDs to set
         */
        public void setGenreID(Set<Integer> genreID) {
            this.genreID = genreID;
        }

        /**
         * Gets the path to the movie poster.
         *
         * @return the poster path as a string
         */
        public String getPosterPath() {
            return posterPath;
        }

        /**
         * Sets the path to the movie poster.
         *
         * @param posterPath the poster path to set
         */
        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        @Override
        public String toString() {
            return "MovieSummary{" +
                    "movieID=" + movieID +
                    ", title='" + title + '\'' +
                    ", overview='" + overview + '\'' +
                    ", releaseDate=" + releaseDate + '\'' +
                    ", genreID=" + genreID +
                    ", posterPath='" + posterPath + '\'' +
                    '}';
        }
    }
}