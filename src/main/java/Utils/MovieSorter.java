package Utils;

import Model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Movie sorter class, providing static methods to sort movie lists in different ways
 */
public class MovieSorter {

    // Private constructor to prevent instantiation
    private MovieSorter() {
    }


    /**
     * Sort movies by title (A-Z)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByTitle(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getTitle));
        return sortedMovies;
    }


    /**
     * Sort movies by title in descending order (Z-A)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByTitleDescending(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getTitle).reversed());
        return sortedMovies;
    }


    /**
     * Sort movies by rating (high to low)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByRating(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getRating).reversed());
        return sortedMovies;
    }


    /**
     * Sort movies by rating in ascending order (low to high)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByRatingAscending(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getRating));
        return sortedMovies;
    }


    /**
     * Sort movies by year (newest to oldest)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByYear(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getYear).reversed());
        return sortedMovies;
    }


    /**
     * Sort movies by year in ascending order (oldest to newest)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByYearAscending(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Collections.sort(sortedMovies, Comparator.comparing(Movie::getYear));
        return sortedMovies;
    }


    /**
     * Sort movies by in-app rating (high to low)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByInAppRating(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        sortedMovies.sort((m1, m2) -> {
            double rating1 = m1.getInAppRating();
            double rating2 = m2.getInAppRating();
            return Double.compare(rating2, rating1); // High to low
        });
        return sortedMovies;
    }


    /**
     * Sort movies by in-app rating in ascending order (low to high)
     *
     * @param movies the list of movies to sort
     * @return sorted list of movies
     */
    public static List<Movie> sortByInAppRatingAscending(List<Movie> movies) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> sortedMovies = new ArrayList<>(movies);
        sortedMovies.sort((m1, m2) -> {
            double rating1 = m1.getInAppRating();
            double rating2 = m2.getInAppRating();
            return Double.compare(rating1, rating2); // Low to high
        });
        return sortedMovies;
    }



    /**
     * Get the top N movies from a sorted list
     *
     * @param movies the sorted list of movies
     * @param n the number of movies to return
     * @return the top N movies
     */
    public static List<Movie> getTopN(List<Movie> movies, int n) {
        if (movies == null || n <= 0) {
            return new ArrayList<>();
        }

        if (n >= movies.size()) {
            return new ArrayList<>(movies);
        }

        return new ArrayList<>(movies.subList(0, n));
    }
}
