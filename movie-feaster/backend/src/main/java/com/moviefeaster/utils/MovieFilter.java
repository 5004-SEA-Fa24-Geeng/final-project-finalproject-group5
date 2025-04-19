package com.moviefeaster.utils;

import com.moviefeaster.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/**
 * Movie filter class, providing intuitive static filtering methods
 */
public class MovieFilter {
    // Private constructor to prevent instantiation
    private MovieFilter() {
        // empty
    }

    /**
     * Filter movies with titles containing a specific keyword
     *
     * @param movies  the list of movies to filter
     * @param keyword the title keyword
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByTitle(final List<Movie> movies, final String keyword) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || keyword == null) {
            return results;
        }

        final String lowerCaseKeyword = keyword.toLowerCase(Locale.ROOT);
        for (final Movie movie : movies) {
            if (movie.getTitle().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword)) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filter movies with an exact title match (case insensitive)
     *
     * @param movies the list of movies to filter
     * @param title  the exact movie title
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByExactTitle(final List<Movie> movies, final String title) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || title == null) {
            return results;
        }

        for (final Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filter movies directed by a specific director
     *
     * @param movies       the list of movies to filter
     * @param directorName the director name
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByDirector(final List<Movie> movies, final String directorName) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || directorName == null) {
            return results;
        }

        final String directorNameLower = directorName.toLowerCase(Locale.ROOT);
        for (final Movie movie : movies) {
            for (final String director : movie.getDirectors()) {
                if (director.toLowerCase(Locale.ROOT).contains(directorNameLower)) {
                    results.add(movie);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * Filter movies from a specific year
     *
     * @param movies the list of movies to filter
     * @param year   the release year
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByYear(final List<Movie> movies, final int year) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null) {
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
     * Filter movies within a specific year range
     *
     * @param movies    the list of movies to filter
     * @param startYear the start year (inclusive)
     * @param endYear   the end year (inclusive)
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByYearRange(final List<Movie> movies,
                                                final int startYear,
                                                final int endYear) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null) {
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
     * Filter movies with a rating greater than or equal to a specified value
     *
     * @param movies    the list of movies to filter
     * @param minRating the minimum rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMinRating(final List<Movie> movies, final double minRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null) {
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
     * Filter movies with a rating less than or equal to a specified value
     *
     * @param movies    the list of movies to filter
     * @param maxRating the maximum rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMaxRating(final List<Movie> movies, final double maxRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null) {
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
     * Filter movies of a specific genre
     *
     * @param movies the list of movies to filter
     * @param genre  the movie genre
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByGenre(final List<Movie> movies, final String genre) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || genre == null) {
            return results;
        }

        final String genreLower = genre.toLowerCase(Locale.ROOT);
        for (final Movie movie : movies) {
            for (final Genre g : movie.getGenres()) {
                if (g.toString().toLowerCase(Locale.ROOT).contains(genreLower)) {
                    results.add(movie);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * Filter movies featuring a specific actor
     *
     * @param movies    the list of movies to filter
     * @param actorName the actor name
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByActor(final List<Movie> movies, final String actorName) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null || actorName == null) {
            return results;
        }

        final String actorNameLower = actorName.toLowerCase(Locale.ROOT);
        for (final Movie movie : movies) {
            for (final String actor : movie.getCastings()) {
                if (actor.toLowerCase(Locale.ROOT).contains(actorNameLower)) {
                    results.add(movie);
                    break;
                }
            }
        }
        return results;
    }

    /**
     * Filter movies with comments containing a specific keyword
     *
     * @param movies  the list of movies to filter
     * @param keyword the comment keyword
     * @return list of movies that match the condition
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
     * Filter movies with an in-app rating greater than or equal to a specified value
     *
     * @param movies    the list of movies to filter
     * @param minRating the minimum in-app rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMinInAppRating(final List<Movie> movies, final double minRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies == null) {
            return results;
        }

        for (final Movie movie : movies) {
            if (movie.getInAppRating() >= minRating) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Combine multiple filter results (intersection)
     *
     * @param list1 the first filter result
     * @param list2 the second filter result
     * @return the intersection of the two lists
     */
    public static List<Movie> combineAnd(final List<Movie> list1, final List<Movie> list2) {
        final List<Movie> results = new ArrayList<>();
        if (list1 == null || list2 == null) {
            return results;
        }

        final HashSet<Movie> set = new HashSet<>(list2);
        for (final Movie movie : list1) {
            if (set.contains(movie)) {
                results.add(movie);
            }
        }
        return results;
    }
}
