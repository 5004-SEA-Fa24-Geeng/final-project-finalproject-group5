package com.moviefeaster.Service;

import com.moviefeaster.Utils.*;
import com.moviefeaster.Model.*;
import com.moviefeaster.Service.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieModel implements MovieModelInterface {

    /** Singleton instance */
    private static MovieModel instance;
    /** Movie's list */
    private List<Movie> movies;
    /** Filtered movies' list */
    private List<Movie> processedMovies;

    /**
     * Private constructor to prevent direct instantiation
     */
    private MovieModel() {
    }

    /** Fetch movie's data from TMDB api and ingest into movie's field.
     * Set the processed movies to an empty list.
     */
    @Override
    public void fetchMovies() {
        this.movies = MovieParser.getMoviesFromApi();
        this.processedMovies = new ArrayList<>();
    }

    /** Get the original movies that pull from TMDB api. */
    public List<Movie> getMovies() {
        return this.movies;
    }

    /** Get the processed movies that applied sorter and filter. */
    public List<Movie> getProcessedMoviesMovies() {
        return this.processedMovies;
    }

    /** Write movie's record to the local file with an option to use original data from TMDB api or processed data.
     * @param format the format of the file
     * @param useProcessedMovie if true, use processed data, otherwise, use original data.
     */
    @Override
    public void writeFile(boolean useProcessedMovie, Format format) {
        List<Movie> moviesToWrite = useProcessedMovie ? this.processedMovies : this.movies;

        try (OutputStream out = new FileOutputStream("output." + format.toString().toLowerCase())) {
            DataFormatter.write(moviesToWrite, format, out);
            System.out.println("Movie data written successfully in " + format + " format.");
        } catch (IOException e) {
            System.err.println("Failed to write movie data: " + e.getMessage());
        }
    }

    /**
     * Filtered the movie list by filter strategy.
     * @param filtersStrategy filtered strategies that with filter type and corresponding values.
     * @return Filtered movies list.
     */
    @Override
    public List<Movie> searchByMultipleFilters(Map<MovieFilterType, Object> filtersStrategy) {
        if (filtersStrategy == null) {
            return this.movies;
        }

        List<Movie> filteredMovies = MovieFilterFacilitator.filter(this.movies, filtersStrategy);
        return filteredMovies;
    }

    /**
     * Filtered the movie list by filter strategy with an option to use a movie list that is filtered or the original
     * data that pull from TMDB api.
     * @param newFilter whether to use a movie list that is filtered or the original data that pull from TMDB api.
     * @param filtersStrategy  filtersStrategy filtered strategies that with filter type and corresponding values.
     * @return Filtered movies list.
     */
    public List<Movie> searchByMultipleFilters(boolean newFilter, Map<MovieFilterType, Object> filtersStrategy) {
        if (filtersStrategy == null) {
            return this.movies;
        }

        List<Movie> moviesToFilter;

        if (newFilter) {
            moviesToFilter = this.movies;
        } else {
            moviesToFilter = this.processedMovies;
        }

        return MovieFilterFacilitator.filter(moviesToFilter, filtersStrategy);
    }

    /**
     * Sorts the movie list by sorting strategy.
     *
     * @param sortType     the sorting strategy to be applied to the movie list
     * @return a new list of movies sorted according to the given strategy
     */
    public List<Movie> sortMovieList(MovieSorterType sortType) {
        // If no sort type, return original data.
        if (sortType == null) {
            return this.movies;
        }

        List<Movie> moviesToSort = this.movies;
        List<Movie> sortedMovies = switch (sortType) {
            case TITLE_ASC -> MovieSorter.sortByTitle(moviesToSort);
            case TITLE_DESC -> MovieSorter.sortByTitleDescending(moviesToSort);
            case YEAR_ASC -> MovieSorter.sortByYearAscending(moviesToSort);
            case YEAR_DESC -> MovieSorter.sortByYear(moviesToSort);
            case RATING_ASC -> MovieSorter.sortByRatingAscending(moviesToSort);
            case RATING_DESC -> MovieSorter.sortByRating(moviesToSort);
            case INAPP_RATING_ASC -> MovieSorter.sortByInAppRatingAscending(moviesToSort);
            case INAPP_RATING_DESC -> MovieSorter.sortByInAppRating(moviesToSort);
        };

        this.processedMovies = sortedMovies;
        return sortedMovies;
    }

    /**
     * Sorts the movie list by sorting strategy with an option to use original data or processed data.
     *
     * @param sortType     the sorting strategy to be applied to the movie list
     * @param useFiltered  if true, sort the filtered (processed) list; otherwise, sort the original movie list
     * @return a movie list that applied the sorting strategy.
     */
    public List<Movie> sortMovieList(boolean useFiltered, MovieSorterType sortType) {
        // If no sort type, return original data.
        if (sortType == null) {
            return this.movies;
        }

        List<Movie> moviesToSort = useFiltered ? this.processedMovies : this.movies;

        List<Movie> sortedMovies = switch (sortType) {
            case TITLE_ASC -> MovieSorter.sortByTitle(moviesToSort);
            case TITLE_DESC -> MovieSorter.sortByTitleDescending(moviesToSort);
            case YEAR_ASC -> MovieSorter.sortByYearAscending(moviesToSort);
            case YEAR_DESC -> MovieSorter.sortByYear(moviesToSort);
            case RATING_ASC -> MovieSorter.sortByRatingAscending(moviesToSort);
            case RATING_DESC -> MovieSorter.sortByRating(moviesToSort);
            case INAPP_RATING_ASC -> MovieSorter.sortByInAppRatingAscending(moviesToSort);
            case INAPP_RATING_DESC -> MovieSorter.sortByInAppRating(moviesToSort);
        };

        this.processedMovies = sortedMovies;
        return sortedMovies;
    }

    /** Update the inApp comment
     * @param movieID movie's ID
     * @param comment user's comment
     */
    @Override
    public void UpdateComments(int movieID, String comment) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieID) {
                movie.addComment(comment);
                return;
            }
        }
    }

    /** Update the inApp rating
     * @param movieID movie's ID
     * @param rating user's rating
     */
    @Override
    public void UpdateRating(int movieID, double rating) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieID) {
                movie.addInAppRating(rating);
                return;
            }
        }
    }

    /**
     * Gets the singleton instance of the MovieModel.
     * It prevents model being instantiated more than once.
     * @return MovieModel
     */
    public static MovieModel getInstance() {
        if (instance == null) {
            instance = new MovieModel();
            instance.fetchMovies();
        }
        return instance;
    }
}
