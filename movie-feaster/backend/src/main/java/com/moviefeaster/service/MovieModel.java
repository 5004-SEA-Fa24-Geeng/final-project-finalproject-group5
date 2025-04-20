package com.moviefeaster.service;

import com.moviefeaster.utils.*;
import com.moviefeaster.model.*;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class that implements MovieModelInterface to provide movie filtering,
 * sorting, and persistence functionalities.
 */
@Service
public class MovieModel implements MovieModelInterface {

    /** Logger instance for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieModel.class);

    /** List of all movies. */
    private List<Movie> movies;

    /** List of filtered movies. */
    private List<Movie> processedMovies;

    /** Default sorting type for movies. */
    private MovieSorterType defaultSortType;

    /**
     * Public constructor initializes movie lists and default sort type.
     */
    public MovieModel() {
        this.movies = new ArrayList<>();
        this.processedMovies = new ArrayList<>();
        this.defaultSortType = MovieSorterType.TITLE_ASC;
        fetchMovies();
    }

    /**
     * Fetch movie's data from TMDB API and ingest into a movie list.
     */
    @Override
    public void fetchMovies() {
        this.movies = MovieParser.getMoviesFromApi();
    }

    /**
     * Get the original movies fetched from the TMDB API.
     *
     * @return list of original movies
     */
    public List<Movie> getMovies() {
        return this.movies;
    }

    /**
     * Retrieve a single movie by ID.
     *
     * @param movieId the movie ID
     * @return movie with matching ID or null if not found
     */
    public Movie getMovieById(final int movieId) {
        Movie result = null;
        for (final Movie movie : this.movies) {
            if (movie.getMovieId() == movieId) {
                result = movie;
                break;
            }
        }
        return result;
    }

    /**
     * Get the filtered and/or sorted list of movies.
     *
     * @return list of processed movies
     */
    public List<Movie> getProcessedMovies() {
        return this.processedMovies;
    }

    /**
     * Write movies to a local file in specified format.
     *
     * @param useProcessedMovie whether to use a processed or original list
     * @param format            output file format
     */
    @Override
    public void writeFile(final boolean useProcessedMovie, final Format format) {
        final List<Movie> moviesToWrite = useProcessedMovie ? this.processedMovies : this.movies;

        try (OutputStream out = new FileOutputStream("output." + format.toString().toLowerCase(Locale.ROOT))) {
            DataFormatter.write(moviesToWrite, format, out);
            LOGGER.info("Movie data written successfully in {} format.", format);
        } catch (IOException e) {
            LOGGER.error("Failed to write movie data: {}", e.getMessage());
        }
    }

    /**
     * Apply filters to the movie list.
     *
     * @param filtersStrategy map of filter types to values
     */
    @Override
    public void searchByFilter(final Map<MovieFilterType, Object> filtersStrategy) {
        if (filtersStrategy == null || filtersStrategy.isEmpty()) {
            this.processedMovies = this.movies;
            sortMovieList(this.defaultSortType);
            return;
        }

        final List<Movie> moviesToFilter = this.movies;
        this.processedMovies = MovieFilterFacilitator.filter(moviesToFilter, filtersStrategy);
        sortMovieList(this.defaultSortType);
    }
    /**
     * Sort the current processed movie list by the specified sort type.
     *
     * @param sortType sorting strategy to apply
     */
    public void sortMovieList(final MovieSorterType sortType) {
        if (sortType == null) {
            return;
        }

        final List<Movie> moviesToSort = this.processedMovies;
        this.processedMovies = switch (sortType) {
            case TITLE_ASC -> MovieSorter.sortByTitle(moviesToSort);
            case TITLE_DESC -> MovieSorter.sortByTitleDescending(moviesToSort);
            case YEAR_ASC -> MovieSorter.sortByYearAscending(moviesToSort);
            case YEAR_DESC -> MovieSorter.sortByYear(moviesToSort);
            case RATING_ASC -> MovieSorter.sortByRatingAscending(moviesToSort);
            case RATING_DESC -> MovieSorter.sortByRating(moviesToSort);
            case INAPP_RATING_ASC -> MovieSorter.sortByInAppRatingAscending(moviesToSort);
            case INAPP_RATING_DESC -> MovieSorter.sortByInAppRating(moviesToSort);
        };
    }

    /**
     * Set the default sorting strategy.
     *
     * @param defaultSortType default sorting type to use
     */
    public void setDefaultSortType(final MovieSorterType defaultSortType) {
        this.defaultSortType = defaultSortType;
    }

    /**
     * Add a user comment to a specific movie.
     *
     * @param movieId the movie's ID
     * @param comment the user's comment
     */
    @Override
    public void updateComments(final int movieId, final String comment) {
        for (final Movie movie : this.movies) {
            if (movie.getMovieId() == movieId) {
                movie.addComment(comment);
                break;
            }
        }
    }

    /**
     * Add a user in-app rating to a specific movie.
     *
     * @param movieId the movie's ID
     * @param rating  the user's rating
     */
    @Override
    public void updateRating(final int movieId, final double rating) {
        for (final Movie movie : this.movies) {
            if (movie.getMovieId() == movieId) {
                movie.addInAppRating(rating);
                break;
            }
        }
    }
}
