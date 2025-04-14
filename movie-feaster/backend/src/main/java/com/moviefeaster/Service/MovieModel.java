package com.moviefeaster.Service;

import com.moviefeaster.Utils.*;
import com.moviefeaster.Model.*;
import org.springframework.stereotype.Service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MovieModel implements MovieModelInterface {

    /** Movie's list */
    private List<Movie> movies;
    /** Filtered movies' list */
    private List<Movie> processedMovies;
    /** Default sorting type */
    private MovieSorterType defaultMovieSorterType;

    /**
     * Public constructor to prevent direct instantiation
     */
    public MovieModel() {
        this.movies = new ArrayList<>();
        this.processedMovies = new ArrayList<>();
        this.defaultMovieSorterType = MovieSorterType.TITLE_ASC;
        fetchMovies(); // Optionally auto-fetch at startup
        this.processedMovies = this.movies;
    }

    /** Fetch movie's data from TMDB api and ingest into movie's field.
     * Set the processed movies to an empty list.
     */
    @Override
    public void fetchMovies() {
        this.movies = MovieParser.getMoviesFromApi();
    }

    /** Get the original movies that pull from TMDB api. */
    public List<Movie> getMovies() {
        return this.movies;
    }

    /** Get a single movie by ID. */
    public Movie getMovieById(int id) {
        for (Movie movie : this.movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    /** Get the processed movies that applied sorter and filter. */
    public List<Movie> getProcessedMovies() {
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
     */
    @Override
    public void searchByFilter(Map<MovieFilterType, Object> filtersStrategy) {
        if (filtersStrategy == null) {
            return;
        }

        List<Movie> moviesToFilter = this.movies;

        this.processedMovies = MovieFilterFacilitator.filter(moviesToFilter, filtersStrategy);
        sortMovieList(this.defaultMovieSorterType);
    }

    /**
     * Sorts the movie list by sorting strategy with an option to use original data or processed data.
     *
     * @param sortType the sorting strategy to be applied to the movie list
     */
    public void sortMovieList(MovieSorterType sortType) {
        if (sortType == null) {
            return;
        }

        List<Movie> moviesToSort = this.processedMovies;
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
     * Set the default sorting type for the movie list.
     * @param defaultMovieSorterType default sorting type.
     */
    public void setDefaultMovieSorterType(MovieSorterType defaultMovieSorterType) {
        this.defaultMovieSorterType = defaultMovieSorterType;
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

}
