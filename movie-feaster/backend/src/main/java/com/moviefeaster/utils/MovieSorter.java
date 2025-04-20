package com.moviefeaster.utils;
import com.moviefeaster.model.Movie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Movie sorter class, providing static methods to sort movie lists in different ways.
 */
public final class MovieSorter {

    // Private constructor to prevent instantiation
    private MovieSorter() {
    }

    /**
     * Sort movies by title (A-Z).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByTitle(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            sortedMovies.sort(Comparator.comparing(movie -> movie.getTitle().toLowerCase()));
        }
        return sortedMovies;
    }

    /**
     * Sort movies by title in descending order (Z-A).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByTitleDescending(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, Comparator.comparing(Movie::getTitle,
                    String.CASE_INSENSITIVE_ORDER).reversed());
        }
        return sortedMovies;
    }

    /**
     * Sort movies by rating (high to low).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByRating(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, Comparator.comparing(Movie::getRating).reversed());
        }
        return sortedMovies;
    }

    /**
     * Sort movies by rating in ascending order (low to high).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByRatingAscending(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, Comparator.comparing(Movie::getRating));
        }
        return sortedMovies;
    }

    /**
     * Sort movies by year (newest to oldest).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByYear(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, Comparator.comparing(Movie::getYear).reversed());
        }
        return sortedMovies;
    }

    /**
     * Sort movies by year in ascending order (oldest to newest).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByYearAscending(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, Comparator.comparing(Movie::getYear));
        }
        return sortedMovies;
    }

    /**
     * Sort movies by in-app rating (high to low).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByInAppRating(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            sortedMovies.sort((m1, m2) -> {
                final double rating1 = m1.getInAppRating();
                final double rating2 = m2.getInAppRating();
                return Double.compare(rating2, rating1); // High to low
            });
        }
        return sortedMovies;
    }

    /**
     * Sort movies by in-app rating in ascending order (low to high).
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByInAppRatingAscending(final List<Movie> movies) {
        List<Movie> sortedMovies;
        if (movies == null) {
            sortedMovies = new ArrayList<>();
        } else {
            sortedMovies = new ArrayList<>(movies);
            sortedMovies.sort((m1, m2) -> {
                final double rating1 = m1.getInAppRating();
                final double rating2 = m2.getInAppRating();
                return Double.compare(rating1, rating2); // Low to high
            });
        }
        return sortedMovies;
    }

    /**
     * Get the top N movies from a sorted list.
     *
     * @param movies the sorted list of movies
     * @param numberOfMovies the number of movies to return
     * @return the top N movies
     */
    public static List<Movie> getTopN(final List<Movie> movies, final int numberOfMovies) {
        List<Movie> result;
        if (movies == null || numberOfMovies <= 0) {
            result = new ArrayList<>();
        } else if (numberOfMovies >= movies.size()) {
            result = new ArrayList<>(movies);
        } else {
            result = new ArrayList<>(movies.subList(0, numberOfMovies));
        }
        return result;
    }
}
