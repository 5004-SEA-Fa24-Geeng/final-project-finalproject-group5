package com.moviefeaster.Model;

import com.moviefeaster.Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store movies' detailed meta data.
 */
public class Movie {

    /** ID of the movie */
    private int id;

    /** Title of the movie */
    private String title;

    /** List of directors of the movie */
    private List<String> directors;

    /** Release year of the movie */
    private int year;

    /** Rating of the movie */
    private double rating;

    /** List of genres the movie belongs to */
    private List<Genre> genres;

    /** String of overview in movie */
    private String overview;

    /** List of actors or actresses in the movie */
    private List<String> castings;

    /** User-submitted comments about the movie */
    private List<String> comments;

    /** In-App rating information provided by users*/
    private List<Double> InAppRating;

    /** Relative path to movie's poster. For details, see below.
     * https://www.themoviedb.org/talk/62933de3df86a834e0a960ff?utm_source=chatgpt.com
     */
    private String imgUrl;


    /**
     * Constructs a Movie instance with the given details with safeguard.
     * If the information is unable to be acquired from api. It will be constructed
     * with default value.
     *
     * @param id             ID of the movie
     * @param title          Title of the movie
     * @param directors      List of directors
     * @param year           Year the movie was released
     * @param rating         Rating of the movie
     * @param genres         List of genres
     * @param overview       Overview of movie
     * @param castings       List of cast members
     * @param imgUrl         Relative path to movie's poster
     */
    public Movie(int id, String title, List<String> directors, int year, double rating,
                 List<Genre> genres, String overview, List<String> castings, String imgUrl) {
        this.id = id;
        this.title = (title != null && !title.isBlank()) ? title : "Unknown Title";
        this.directors = (directors != null) ? directors : new ArrayList<>();
        this.year = (year > 1800) ? year : 0;
        this.rating = (rating >= 0.0 && rating <= 10.0) ? rating : 0.0;
        this.genres = (genres != null) ? genres : new ArrayList<>();
        this.overview = overview != null && !overview.isBlank() ? overview : "No Overview";
        this.castings = (castings != null) ? castings : new ArrayList<>();
        this.comments = new ArrayList<>();
        this.InAppRating = new ArrayList<>();
        this.imgUrl = (imgUrl != null) ? imgUrl : "";
    }

    /**
     * Returns the ID of the movie.
     *
     * @return the movie ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id the movie ID to set
     */
    public void setId(int id) {
        this.id = id;
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
        if (year > 1800) {
            this.year = year;
        }
    }

    /**
     * Gets the rating of the movie.
     *
     * @return The movie rating.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating Rating between 0.0 and 10.0.
     */
    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 10.0) {
            this.rating = rating;
        }
    }


    /**
     * Gets the genres of the movie.
     *
     * @return A list of genres.
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * Reset the genres of the movie.
     *
     * @param genres A list of genres. Null value will be replaced with an empty list.
     */
    public void setGenres(List<Genre> genres) {
        this.genres = (genres != null) ? genres : new ArrayList<>();
    }

    /**
     * Returns the overview of the movie.
     *
     * @return a brief summary or description of the movie
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets the overview of the movie.
     *
     * @param overview a brief summary or description of the movie
     */
    public void setOverview(String overview) {
        this.overview = overview;
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
    public double getInAppRating() {
        if (InAppRating.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Double rating : InAppRating) {
            sum += rating;
        }
        return sum / InAppRating.size();
    }

    /**
     * Resets the list of in-app ratings.
     *
     * @param inAppRating A list of in-app ratings. Null list will be replaced with an empty list.
     */
    public void setInAppRating(List<Double> inAppRating) {
        this.InAppRating = (inAppRating != null) ? inAppRating : new ArrayList<>();
    }

    /**
     * Add single rating to the list of in-app ratings.
     *
     * @param rating Rating to add.
     */
    public void addInAppRating(Double rating) {
        this.InAppRating.add(rating);
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id + ", " +
                "title='" + title + '\'' +
                ", directors=" + directors +
                ", year=" + year +
                ", rating=" + rating +
                ", genres=" + genres +
                ", castings=" + castings +
                ", comments=" + comments +
                ", InAppRating=" + InAppRating +
                ", updatedAverageInAppRating=" + getInAppRating() +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

}
