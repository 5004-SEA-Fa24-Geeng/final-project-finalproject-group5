package com.moviefeaster.utils;

import com.moviefeaster.model.Movie;
import com.moviefeaster.model.MovieFilterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Facilitates filtering of movies based on various criteria.
 */
public final class MovieFilterFacilitator {

    /**
     * Private constructor preventing instantiation.
     */
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
        List<Movie> result = new ArrayList<>(movies);

        for (final Map.Entry<MovieFilterType, Object> entry : filters.entrySet()) {
            final MovieFilterType type = entry.getKey();
            final Object value = entry.getValue();

            List<Movie> filteredMovies = result;

            filteredMovies = switch (type) {
                case TITLE_KEYWORD -> MovieFilter.filterByTitle(filteredMovies, (String) value);
                case EXACT_TITLE -> MovieFilter.filterByExactTitle(filteredMovies, (String) value);
                case DIRECTOR -> MovieFilter.filterByDirector(filteredMovies, (String) value);
                case ACTOR -> MovieFilter.filterByActor(filteredMovies, (String) value);
                case GENRE -> MovieFilter.filterByGenre(filteredMovies, (String) value);
                case YEAR -> MovieFilter.filterByYear(filteredMovies, (Integer) value);
                case YEAR_RANGE -> {
                    final int[] range = (int[]) value;
                    final int startYear = range[0];
                    final int endYear = range[1];
                    final int expectedLength = 2;
                    if (range.length == expectedLength) {
                        yield MovieFilter.filterByYearRange(filteredMovies, startYear, endYear);
                    }
                    yield filteredMovies;
                }
                case MIN_RATING -> MovieFilter.filterByMinRating(filteredMovies, (double) value);
                case MAX_RATING -> MovieFilter.filterByMaxRating(filteredMovies, (double) value);
                case COMMENT_KEYWORD -> MovieFilter.filterByCommentKeyword(filteredMovies, (String) value);
                case MIN_INAPP_RATING -> MovieFilter.filterByMinInAppRating(filteredMovies, (Double) value);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };

            result = filteredMovies;
        }

        return result;
    }
}
