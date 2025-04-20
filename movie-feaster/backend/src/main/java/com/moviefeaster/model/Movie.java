package com.moviefeaster.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store movies' detailed meta data.
 */
public final class Movie {

    /** ID of the movie. */
    private int movieId;

    /** Title of the movie. */
    private String title;

    /** List of directors of the movie. */
    private List<String> directors;

    /** Release year of the movie. */
    private int year;

    /** Rating of the movie. */
    private double rating;

    /** The List of genres the movie belongs to. */
    private List<Genre> genres;

    /** String of overview in movie. */
    private String overview;

    /** List of actors or actresses in the movie. */
    private List<String> castings;

    /** User-submitted comments about the movie. */
    private List<String> comments;

    /** In-App rating information provided by users. */
    private List<Double> inAppRating;

    /** URL movie's poster. */
    private String imgUrl;

    /**
     * Private constructor for Movie class.
     * Use MovieBuilder to create instances.
     */
    private Movie() {
        this.comments = new ArrayList<>();
        this.inAppRating = new ArrayList<>();
    }

    /**
     * Builder class for creating Movie instances.
     */
    public static class MovieBuilder {

        /**
         * Internal Movie instance being built by this builder.
         * Values are progressively assigned through chained builder methods.
         */
        private final Movie movie;

        /**
         * Creates a new MovieBuilder instance.
         */
        public MovieBuilder() {
            this.movie = new Movie();
        }

        /**
         * Sets the movie ID.
         *
         * @param movieId the movie ID
         * @return this builder
         */
        public MovieBuilder movieId(final int movieId) {
            this.movie.movieId = movieId;
            return this;
        }

        /**
         * Sets the movie title.
         *
         * @param title the movie title
         * @return this builder
         */
        public MovieBuilder title(final String title) {
            this.movie.title = title != null && !title.isBlank() ? title : "Unknown Title";
            return this;
        }

        /**
         * Sets the directors.
         *
         * @param directors the list of directors
         * @return this builder
         */
        public MovieBuilder directors(final List<String> directors) {
            this.movie.directors = directors != null ? directors : new ArrayList<>();
            return this;
        }

        /**
         * Sets the release year.
         *
         * @param year the release year
         * @return this builder
         */
        public MovieBuilder year(final int year) {
            this.movie.year = year > 1800 ? year : 0;
            return this;
        }

        /**
         * Sets the rating.
         *
         * @param rating the movie rating
         * @return this builder
         */
        public MovieBuilder rating(final double rating) {
            this.movie.rating = rating >= 0.0 && rating <= 10_000.0 ? rating : 0.0;
            return this;
        }

        /**
         * Sets the genres.
         *
         * @param genres the list of genres
         * @return this builder
         */
        public MovieBuilder genres(final List<Genre> genres) {
            this.movie.genres = genres != null ? genres : new ArrayList<>();
            return this;
        }

        /**
         * Sets the overview.
         *
         * @param overview the movie overview
         * @return this builder
         */
        public MovieBuilder overview(final String overview) {
            this.movie.overview = overview != null && !overview.isBlank() ? overview : "No Overview";
            return this;
        }

        /**
         * Sets the castings.
         *
         * @param castings the list of cast members
         * @return this builder
         */
        public MovieBuilder castings(final List<String> castings) {
            this.movie.castings = castings != null ? castings : new ArrayList<>();
            return this;
        }

        /**
         * Sets the image URL.
         *
         * @param imgUrl the image URL
         * @return this builder
         */
        public MovieBuilder imgUrl(final String imgUrl) {
            this.movie.imgUrl = imgUrl != null ? imgUrl : "";
            return this;
        }

        /**
         * Builds and returns the Movie instance.
         *
         * @return the built Movie instance
         */
        public Movie build() {
            return this.movie;
        }
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
        final int minYear = 1800;
        if (year > minYear) {
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
