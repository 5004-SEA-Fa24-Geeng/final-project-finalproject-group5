package com.moviefeaster.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store movies' detailed meta data.
 */
public class Movie {

    /** ID of the movie */
    private int movieId;

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
    private List<Double> inAppRating;

    /** URL movie's poster. */
    private String imgUrl;

    /**
     * Constructs a Movie instance with the given details with safeguard.
     * If the information is unable to be acquired from api. It will be constructed
     * with default value.
     *
     * @param movieId        ID of the movie
     * @param title          Title of the movie
     * @param directors      List of directors
     * @param year           Year the movie was released
     * @param rating         Rating of the movie
     * @param genres         List of genres
     * @param overview       Overview of movie
     * @param castings       List of cast members
     * @param imgUrl         URL of movie's poster
     */
    public Movie(final int movieId, final String title, final List<String> directors, final int year,
                 final double rating, final List<Genre> genres, final String overview,
                 final List<String> castings, final String imgUrl) {
        this.movieId = movieId;
        this.title = title != null && !title.isBlank() ? title : "Unknown Title";
        this.directors = directors != null ? directors : new ArrayList<>();
        this.year = year > 1800 ? year : 0;
        this.rating = rating >= 0.0 && rating <= 10_000.0 ? rating : 0.0;
        this.genres = genres != null ? genres : new ArrayList<>();
        this.overview = overview != null && !overview.isBlank() ? overview : "No Overview";
        this.castings = castings != null ? castings : new ArrayList<>();
        this.comments = new ArrayList<>();
        this.inAppRating = new ArrayList<>();
        this.imgUrl = imgUrl != null ? imgUrl : "";
    }

    /**
     * Returns the ID of the movie.
     *
     * @return the movie ID
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param movieId the movie ID to set
     */
    public void setMovieId(final int movieId) {
        this.movieId = movieId;
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
    public void setTitle(final String title) {
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
    public void setDirectors(final List<String> directors) {
        this.directors = directors != null ? directors : new ArrayList<>();
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
    public void setYear(final int year) {
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
    public void setRating(final double rating) {
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
    public void setGenres(final List<Genre> genres) {
        this.genres = genres != null ? genres : new ArrayList<>();
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
    public void setOverview(final String overview) {
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
    public void setCastings(final List<String> castings) {
        this.castings = castings != null ? castings : new ArrayList<>();
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
    public void setComments(final List<String> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    /**
     * Add a single comment to the user comments.
     *
     * @param comment comment to add.
     */
    public void addComment(final String comment) {
        this.comments.add(comment);
    }

    /**
     * Gets the list of in-app ratings.
     *
     * @return A list of in-app ratings.
     */
    public double getInAppRating() {
        double average = 0.0;
        if (!inAppRating.isEmpty()) {
            double sum = 0.0;
            for (final Double rating : inAppRating) {
                sum += rating;
            }
            average = sum / inAppRating.size();
        }
        return average;
    }

    /**
     * Resets the list of in-app ratings.
     *
     * @param ratings A list of in-app ratings. Null list will be replaced with an empty list.
     */
    public void setInAppRating(final List<Double> ratings) {
        this.inAppRating = ratings != null ? ratings : new ArrayList<>();
    }

    /**
     * Add single rating to the list of in-app ratings.
     *
     * @param rating Rating to add.
     */
    public void addInAppRating(final Double rating) {
        this.inAppRating.add(rating);
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
    public void setImgUrl(final String imgUrl) {
        this.imgUrl = imgUrl != null ? imgUrl : "";
    }

    @Override
    public String toString() {
        return "Movie{"
                + "movieId=" + movieId
                + ", title='" + title + '\''
                + ", directors=" + directors
                + ", year=" + year
                + ", rating=" + rating
                + ", genres=" + genres
                + ", castings=" + castings
                + ", comments=" + comments
                + ", inAppRating=" + inAppRating
                + ", updatedAverageInAppRating=" + getInAppRating()
                + ", imgUrl='" + imgUrl + '\''
                + '}';
    }
}
