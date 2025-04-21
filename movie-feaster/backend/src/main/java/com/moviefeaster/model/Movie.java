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

    /** List of genres the movie belongs to. */
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
     * Constructs a Movie instance using the values provided by the {@link Builder}.
     * Applies default values and validation to safeguard against missing or invalid data.
     *
     * @param builder the Builder instance containing movie data
     */
    private Movie(Builder builder) {
        this.movieId = builder.movieId;
        this.title = builder.title != null && !builder.title.isBlank() ? builder.title : "Unknown Title";
        this.directors = builder.directors != null ? builder.directors : new ArrayList<>();
        this.year = builder.year > 1800 ? builder.year : 0;
        this.rating = builder.rating >= 0.0 && builder.rating <= 10_000.0 ? builder.rating : 0.0;
        this.genres = builder.genres != null ? builder.genres : new ArrayList<>();
        this.overview = builder.overview != null && !builder.overview.isBlank() ? builder.overview : "No Overview";
        this.castings = builder.castings != null ? builder.castings : new ArrayList<>();
        this.imgUrl = builder.imgUrl != null ? builder.imgUrl : "";
        this.comments = new ArrayList<>();
        this.inAppRating = new ArrayList<>();
    }

    /**
     * Builder class for constructing instances of {@link Movie}.
     * Provides a fluent interface for setting fields individually before building the Movie.
     */
    public static class Builder {
        /** Unique identifier for the movie. */
        private int movieId;

        /** Title of the movie. */
        private String title;

        /** List of directors associated with the movie. */
        private List<String> directors;

        /** Release year of the movie. */
        private int year;

        /** Rating of the movie, expected to be between 0.0 and 10,000.0. */
        private double rating;

        /** List of genres the movie belongs to. */
        private List<Genre> genres;

        /** Overview or summary description of the movie. */
        private String overview;

        /** List of cast members appearing in the movie. */
        private List<String> castings;

        /** URL of the movie's poster image. */
        private String imgUrl;

        /**
         * Sets the movie ID.
         *
         * @param movieId the ID of the movie
         * @return the current Builder instance
         */
        public Builder movieId(int movieId) {
            this.movieId = movieId;
            return this;
        }

        /**
         * Sets the title of the movie.
         *
         * @param title the movie title
         * @return the current Builder instance
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the list of directors.
         *
         * @param directors list of director names
         * @return the current Builder instance
         */
        public Builder directors(List<String> directors) {
            this.directors = directors;
            return this;
        }

        /**
         * Sets the release year of the movie.
         *
         * @param year the release year
         * @return the current Builder instance
         */
        public Builder year(int year) {
            this.year = year;
            return this;
        }

        /**
         * Sets the rating of the movie.
         *
         * @param rating the rating score (expected between 0.0 and 10,000.0)
         * @return the current Builder instance
         */
        public Builder rating(double rating) {
            this.rating = rating;
            return this;
        }

        /**
         * Sets the list of genres.
         *
         * @param genres list of {@link Genre} objects
         * @return the current Builder instance
         */
        public Builder genres(List<Genre> genres) {
            this.genres = genres;
            return this;
        }

        /**
         * Sets the overview/summary of the movie.
         *
         * @param overview the movie's overview text
         * @return the current Builder instance
         */
        public Builder overview(String overview) {
            this.overview = overview;
            return this;
        }

        /**
         * Sets the list of cast members.
         *
         * @param castings list of actor/actress names
         * @return the current Builder instance
         */
        public Builder castings(List<String> castings) {
            this.castings = castings;
            return this;
        }

        /**
         * Sets the image URL for the movie poster.
         *
         * @param imgUrl the URL of the poster image
         * @return the current Builder instance
         */
        public Builder imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        /**
         * Builds and returns a {@link Movie} instance using the current state of the Builder.
         * Fields are validated and default values are applied where necessary.
         *
         * @return a fully constructed {@link Movie} object
         */
        public Movie build() {
            return new Movie(this);
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
