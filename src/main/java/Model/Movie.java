package Model;

import com.github.javaparser.utils.Pair;
import org.checkerframework.checker.index.qual.GTENegativeOne;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store movies' detailed meta data.
 */
public class Movie {

    /** Title of the movie */
    private String title;

    /** List of directors of the movie */
    private List<String> directors;

    /** Release year of the movie */
    private int year;

    /** Rating of the movie */
    private float rating;

    /** List of genres the movie belongs to */
    private List<Genere> genres;

    /** List of actors or actresses in the movie */
    private List<String> castings;

    /** User-submitted comments about the movie */
    private List<String> comments;

    /** In-App rating information provided by users*/
    private List<Float> InAppRating;

    /** The updated average rating calculated by mean of accumulated amount of the ratings
     * and the accumulated amount of the rating. This field prevent redundant list iteration.
     * With <updatedAverageRating, accumulated amount of the rating> structure.
     */
    private Pair<Float, Integer> updatedAverageInAppRating;

    /** Relative path to movie's poster. For details, see below.
     * https://www.themoviedb.org/talk/62933de3df86a834e0a960ff?utm_source=chatgpt.com
     */
    private String imgUrl;


    /**
     * Constructs a Movie instance with the given details with safeguard.
     * If the information is unable to acquire from api. It will be constructed
     * with default value.
     *
     * @param title          Title of the movie
     * @param directors      List of directors
     * @param year           Year the movie was released
     * @param rating         Rating of the movie
     * @param genres         List of genres
     * @param castings       List of cast members
     * @param imgUrl         Relative path to movie's poster
     */
    public Movie(String title, List<String> directors, int year, float rating,
                 List<Genere> genres, List<String> castings, String imgUrl) {
        this.title = (title != null && !title.isBlank()) ? title : "Unknown Title";
        this.directors = (directors != null) ? directors : new ArrayList<>();
        this.year = (year > 1800) ? year : 0; // TODO: check if this is logical?
        this.rating = (rating >= 0.0f && rating <= 10.0f) ? rating : 0.0f;
        this.genres = (genres != null) ? genres : new ArrayList<>();
        this.castings = (castings != null) ? castings : new ArrayList<>();
        this.comments = new ArrayList<>();
        this.InAppRating = new ArrayList<>();
        this.updatedAverageInAppRating = new Pair<>(0.0f, 0);
        this.imgUrl = (imgUrl != null) ? imgUrl : "";
    }

    /**
     * Gets the title of the movie.
     *
     * @return The movie title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The movie title. Must not be blank.
     */
    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    /**
     * Gets the list of directors.
     *
     * @return A list of directors.
     */
    public List<String> getDirectors() {
        return directors;
    }

    /**
     * Reset the list of directors.
     *
     * @param directors A list of directors. Null list will be replaced with an empty list.
     */
    public void setDirectors(List<String> directors) {
        this.directors = (directors != null) ? directors : new ArrayList<>();
    }

    /**
     * Gets the release year of the movie.
     *
     * @return The release year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the release year of the movie.
     *
     * @param year Release year (must be > 1800 to be accepted).
     */
    public void setYear(int year) {
        // TODO: check if the constraint is logical.
        if (year > 1800) {
            this.year = year;
        }
    }

    /**
     * Gets the rating of the movie.
     *
     * @return The movie rating.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating Rating between 0.0 and 10.0.
     */
    public void setRating(float rating) {
        if (rating >= 0.0f && rating <= 10.0f) {
            this.rating = rating;
        }
    }


    /**
     * Gets the genres of the movie.
     *
     * @return A list of genres.
     */
    public List<Genere> getGenres() {
        return genres;
    }

    /**
     * Reset the genres of the movie.
     *
     * @param genres A list of genres. Null value will be replaced with an empty list.
     */
    public void setGenres(List<Genere> genres) {
        this.genres = (genres != null) ? genres : new ArrayList<>();
    }

    /**
     * Gets the cast members of the movie.
     *
     * @return A list of cast members.
     */
    public List<String> getCastings() {
        return castings;
    }

    /**
     * Resets the cast members of the movie.
     *
     * @param castings A list of cast members. Null list will be replaced with an empty list.
     */
    public void setCastings(List<String> castings) {
        this.castings = (castings != null) ? castings : new ArrayList<>();
    }

    /**
     * Gets user-submitted comments.
     *
     * @return A list of comments.
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * Reset the list of user comments.
     *
     * @param comments A list of comments. Null list will be replaced with an empty list.
     */
    public void setComments(List<String> comments) {
        this.comments = (comments != null) ? comments : new ArrayList<>();
    }

    /**
     * Add a single comment to the user comments.
     * @param comment comment to add.
     */
    public void addComment(String comment) {
        this.comments.add(comment);
    }

    /**
     * Gets the list of in-app ratings.
     *
     * @return A list of in-app ratings.
     */
    public List<Float> getInAppRating() {
        return InAppRating;
    }

    /**
     * Resets the list of in-app ratings.
     *
     * @param inAppRating A list of in-app ratings. Null list will be replaced with an empty list.
     */
    public void setInAppRating(List<Float> inAppRating) {
        this.InAppRating = (inAppRating != null) ? inAppRating : new ArrayList<>();
    }

    /**
     * Add single rating to the list of in-app ratings.
     *
     * @param rating Rating to add.
     */
    public void addInAppRating(float rating) {
        this.InAppRating.add(rating);
    }

    /**
     * Gets the updated average in-app rating and the total rating count.
     *
     * @return A pair containing the average rating and the count.
     */
    public Pair<Float, Integer> getUpdatedAverageInAppRating() {
        return updatedAverageInAppRating;
    }

    /**
     * Sets the updated average in-app rating.
     *
     * @param updatedAverageInAppRating A pair of average rating and count. Null pair will be replaced with default.
     */
    public void setUpdatedAverageInAppRating(Pair<Float, Integer> updatedAverageInAppRating) {
        this.updatedAverageInAppRating = (updatedAverageInAppRating != null)
                ? updatedAverageInAppRating : new Pair<>(0.0f, 0);
    }

    /**
     * Gets the image URL (poster path) of the movie.
     *
     * @return The image URL.
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * Sets the image URL (poster path) of the movie.
     *
     * @param imgUrl The image URL. Null value will be replaced with an empty string.
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = (imgUrl != null) ? imgUrl : "";
    }

}
