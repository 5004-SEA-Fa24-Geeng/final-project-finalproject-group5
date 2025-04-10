package Model.Net;

import Model.Genre;
import Model.Movie;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Class to convert json inputStream to movie object.
 */
public final class MovieParser extends JsonDeserializer<Movie> {

    /** Original Json string from api.*/
    private InputStream jsonStream = NetUtil.getTop50MoviesJson();

    /** PlaceHolder for original json string */
    private List<MovieSummery> summery;

    /** The deserialized list of Movies. */
    private static List<Movie> movies;

    /** Private constructor preventing instantiation */
    private MovieParser() {
        // Prevent instantiation
    }

    /** Getter for the parsed movies with Movie type */
    public static Collection<Movie> getParsedMovies() {
        return movies;
    }

    private void mapJsonStringIntoSummeryClass () {
        ObjectMapper mapper = new ObjectMapper();
        List<Movie> movies = mapper.readValue(jsonArrayString, new TypeReference<List<MovieSummery>>() {});
    }

    @Override
    public Movie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return null;
    }

    /** Function to map Genre ID into Genre type
     * @param genreIds Genre ID that needed to be transformed to Genre type.
     * @return Genre that conforms with ID.
     */
    private static List<Genre> convertGenreIds(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genreIds) {
            Genre g = Genre.fromId(id);
            if (g != null) genres.add(g);
        }
        return genres;
    }

    /** A placeholder class that hold the original data that pull from api */
    @JsonDeserialize(using = MovieParser.class)
    private class MovieSummery {
        /** Movie ID */
        private int movieID;

        /** Movie title */
        private String title;

        /** Movie overview */
        private String overview;

        /** Genre ID */
        private Set<Integer> genreID;

        /** Relative Poster path */
        private String posterPath;

        /** Constructor     of movie summery class */
        public MovieSummery(int movieID, String title, String overview, Set<Integer> genreID, String posterPath) {
            this.movieID = movieID;
            this.title = title;
            this.overview = overview;
            this.genreID = genreID;
            this.posterPath = posterPath;
        }
    }
}
