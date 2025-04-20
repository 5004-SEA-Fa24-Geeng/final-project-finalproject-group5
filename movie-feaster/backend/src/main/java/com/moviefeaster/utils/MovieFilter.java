package com.moviefeaster.utils;

import com.moviefeaster.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * MovieFilter provides static methods for filtering a list of movies
 * based on various criteria such as title, director, genre, and rating.
 */
public final class MovieFilter {
    // Private constructor to prevent instantiation
    private MovieFilter() {
        // empty
    }

    /**
     * Filters movies by title containing the keyword (case-insensitive).
     *
     * @param movies  the list of movies to filter
     * @param keyword the title keyword to search for
     * @return list of movies whose title contains the keyword
     */
    public static List<Movie> filterByTitle(final List<Movie> movies, final String keyword) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || keyword == null || keyword.isBlank()) {
            return results;
        }

        final String lowerCaseKeyword = keyword.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            if (movie.getTitle() != null && movie.getTitle().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword)) {
                results.add(movie);
            }
        }

        return results;
    }

    /**
     * Filters movies by exact title match (case-insensitive).
     *
     * @param movies the list of movies to filter
     * @param title  the title to match exactly
     * @return list of movies with the exact title
     */
    public static List<Movie> filterByExactTitle(final List<Movie> movies, final String title) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || title == null || title.isBlank()) {
            return results;
        }

        final String lowerCaseTitle = title.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            if (movie.getTitle() != null && movie.getTitle().toLowerCase(Locale.ROOT).equals(lowerCaseTitle)) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filters movies by director name containing the keyword (case-insensitive).
     *
     * @param movies       the list of movies to filter
     * @param directorName the director name keyword to search for
     * @return list of movies directed by someone matching the keyword
     */
    public static List<Movie> filterByDirector(final List<Movie> movies, final String directorName) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || directorName == null || directorName.isBlank()) {
            return results;
        }

        final String directorNameLower = directorName.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            if (movie.getDirectors() != null) {
                for (final String director : movie.getDirectors()) {
                    if (director != null && director.toLowerCase(Locale.ROOT).contains(directorNameLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    /**
     * Filters movies released in a specific year.
     *
     * @param movies the list of movies to filter
     * @param year   the target release year
     * @return list of movies released in the given year
     */
    public static List<Movie> filterByYear(final List<Movie> movies, final int year) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || year < 0) {
            return results;
        }

        for (final Movie movie : movies) {
            if (movie.getYear() == year) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filters movies released within a year range (inclusive).
     *
     * @param movies    the list of movies to filter
     * @param startYear the start of the year range
     * @param endYear   the end of the year range
     * @return list of movies released between startYear and endYear
     */
    public static List<Movie> filterByYearRange(final List<Movie> movies,
                                                final int startYear,
                                                final int endYear) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || startYear < 0 || endYear < 0 || startYear > endYear) {
            return results;
        }

        for (final Movie movie : movies) {
            final int year = movie.getYear();
            if (year >= startYear && year <= endYear) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filters movies with a rating greater than or equal to minRating.
     *
     * @param movies    the list of movies to filter
     * @param minRating the minimum rating threshold
     * @return list of movies with rating >= minRating
     */
    public static List<Movie> filterByMinRating(final List<Movie> movies, final double minRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || minRating < 0.0 || minRating > 10.0) {
            return results;
        }

        for (final Movie movie : movies) {
            if (movie.getRating() >= minRating) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filters movies with a rating less than or equal to maxRating.
     *
     * @param movies    the list of movies to filter
     * @param maxRating the maximum rating threshold
     * @return list of movies with rating <= maxRating
     */
    public static List<Movie> filterByMaxRating(final List<Movie> movies, final double maxRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || maxRating < 0.0 || maxRating > 10.0) {
            return results;
        }

        for (final Movie movie : movies) {
            if (movie.getRating() <= maxRating) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filters movies by genre keyword (case-insensitive).
     *
     * @param movies the list of movies to filter
     * @param genre  the genre keyword to search for
     * @return list of movies matching the genre keyword
     */
    public static List<Movie> filterByGenre(final List<Movie> movies, final String genre) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || genre == null || genre.isBlank()) {
            return results;
        }

        final String genreLower = genre.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            if (movie.getGenres() != null) {
                for (final Genre g : movie.getGenres()) {
                    if (g != null && g.toString().toLowerCase(Locale.ROOT).contains(genreLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    /**
     * Filters movies by actor name keyword (case-insensitive).
     *
     * @param movies    the list of movies to filter
     * @param actorName the actor name keyword to search for
     * @return list of movies featuring the actor
     */
    public static List<Movie> filterByActor(final List<Movie> movies, final String actorName) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || actorName == null || actorName.isBlank()) {
            return results;
        }

        final String actorNameLower = actorName.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            if (movie.getCastings() != null) {
                for (final String actor : movie.getCastings()) {
                    if (actor != null && actor.toLowerCase(Locale.ROOT).contains(actorNameLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    /**
     * Filters movies by comment keyword (case-insensitive).
     *
     * @param movies  the list of movies to filter
     * @param keyword the keyword to search for in comments
     * @return list of movies with matching comment keyword
     */
    public static List<Movie> filterByCommentKeyword(final List<Movie> movies, final String keyword) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || keyword == null) {
            return results;
        }

        final String keywordLower = keyword.toLowerCase(Locale.ROOT);

        for (final Movie movie : movies) {
            for (final String comment : movie.getComments()) {
                if (comment.toLowerCase(Locale.ROOT).contains(keywordLower)) {
                    results.add(movie);
                    break;
                }
            }
        }

        return results;
    }

    /**
     * Filters movies with an in-app rating greater than or equal to the specified threshold.
     *
     * @param movies    the list of movies to filter
     * @param minRating the minimum in-app rating
     * @return list of movies with in-app rating >= minRating
     */
    public static List<Movie> filterByMinInAppRating(final List<Movie> movies, final double minRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null) {
            for (final Movie movie : movies) {
                if (movie.getInAppRating() >= minRating) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    /**
     * Combines two movie lists and returns only movies that are present in both (intersection).
     *
     * @param list1 the first list of movies
     * @param list2 the second list of movies
     * @return list of movies common to both input lists
     */
    public static List<Movie> combineAnd(final List<Movie> list1, final List<Movie> list2) {
        final List<Movie> results = new ArrayList<>();
        if (list1 != null && list2 != null) {
            final HashSet<Movie> set = new HashSet<>(list2);
            for (final Movie movie : list1) {
                if (set.contains(movie)) {
                    results.add(movie);
                }
            }
        }
        return results;
    }
}
