package com.moviefeaster.service;

import com.moviefeaster.model.*;

import java.util.Map;

/**
 * Interface for MovieModel. Responsible for handling data fetching from api sources,
 * writing and reading movies data from local database, filtering and sorting movies data,
 * and output format.
 */
public interface MovieModelInterface {

    /**
     * Fetching movie data from api.
     */
    void fetchMovies();

    /**
     * Write the movie data stored in the model to the file.
     *
     * @param useProcessedMovie Whether to use processed movies or all movies
     * @param format The format to write the movies in
     */
    void writeFile(boolean useProcessedMovie, Format format);

    /**
     * Filter the movie that conforms with user's input.
     *
     * @param filtersStrategy The filtering strategy to apply
     */
    void searchByFilter(Map<MovieFilterType, Object> filtersStrategy);

    /**
     * Update comments with user-given comment.
     *
     * @param movieID ID of movie that the comments are given
     * @param comment comments
     */
    void updateComments(int movieID, String comment);

    /**
     * Update in-App rating with given rating.
     *
     * @param movieID ID of movie that the rating is given
     * @param rating rating from user
     */
    void updateRating(int movieID, double rating);

}
