package com.moviefeaster.utils;

import com.moviefeaster.model.Movie;
import com.moviefeaster.model.MovieFilterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Facilitates filtering of movies based on various criteria.
 */
public class MovieFilterFacilitator {

    /** Private constructor preventing instantiation */
    private MovieFilterFacilitator() {
        // preventing instantiation
    }

    /**
     * Apply multiple filters to the list of movies using a map of filter types and their values.
     *
     * @param movies  the original list of movies
     * @param filters map containing filter types and their corresponding values
     * @return list of movies that match the applied filters
     */
    public static List<Movie> filter(final List<Movie> movies, final Map<MovieFilterType, Object> filters) {
        List<Movie> result = new ArrayList<>(movies); // Cannot be final because reassigned below

        for (final Map.Entry<MovieFilterType, Object> entry : filters.entrySet()) {
            final MovieFilterType type = entry.getKey();
            final Object value = entry.getValue();

            switch (type) {
                case TITLE_KEYWORD -> result = MovieFilter.filterByTitle(result, (String) value);
                case EXACT_TITLE -> result = MovieFilter.filterByExactTitle(result, (String) value);
                case DIRECTOR -> result = MovieFilter.filterByDirector(result, (String) value);
                case ACTOR -> result = MovieFilter.filterByActor(result, (String) value);
                case GENRE -> result = MovieFilter.filterByGenre(result, (String) value);
                case YEAR -> result = MovieFilter.filterByYear(result, (Integer) value);
                case YEAR_RANGE -> {
                    final int[] range = (int[]) value; // expect array of two elements
                    if (range.length == 2) {
                        result = MovieFilter.filterByYearRange(result, range[0], range[1]);
                    }
                }
                case MIN_RATING -> result = MovieFilter.filterByMinRating(result, (double) value);
                case MAX_RATING -> result = MovieFilter.filterByMaxRating(result, (double) value);
                case COMMENT_KEYWORD -> result = MovieFilter.filterByCommentKeyword(result, (String) value);
                case MIN_INAPP_RATING -> result = MovieFilter.filterByMinInAppRating(result, (Double) value);
            }
        }

        return result;
    }
}
