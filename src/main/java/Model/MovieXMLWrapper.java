package Model;

import java.util.Collection;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * Movie XML wrapper to help serialize a collection of movies to XML using Jackson.
 * This class ensures that the generated XML uses the correct tag structure.
 */
@JacksonXmlRootElement(localName = "movies")
public class MovieXMLWrapper {

    /** Collection of movies */
    @JacksonXmlElementWrapper(useWrapping = false)
    private Collection<Movie> movie;

    /**
     * Default constructor for deserialization.
     */
    public MovieXMLWrapper() {
        // Required for Jackson deserialization
    }

    /**
     * Constructor.
     *
     * @param movies the collection of movies to wrap
     */
    public MovieXMLWrapper(Collection<Movie> movies) {
        this.movie = movies;
    }

    /**
     * Get the movie collection.
     *
     * @return the movie collection
     */
    public Collection<Movie> getMovie() {
        return movie;
    }

    /**
     * Set the movie collection.
     *
     * @param movie the movie collection to set
     */
    public void setMovie(Collection<Movie> movie) {
        this.movie = movie;
    }
}
