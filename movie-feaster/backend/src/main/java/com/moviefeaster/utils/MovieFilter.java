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

    public static List<Movie> filterByTitle(final List<Movie> movies, final String keyword) {
        final List<Movie> results = new ArrayList<>();
        String lowerCaseKeyword;

        if (movies == null || keyword == null) {
            lowerCaseKeyword = null;
        } else {
            lowerCaseKeyword = keyword.toLowerCase(Locale.ROOT);
        }

        if (lowerCaseKeyword != null) {
            for (final Movie movie : movies) {
                if (movie.getTitle().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword)) {
                    results.add(movie);
                }
            }
        }

        return results;
    }

    public static List<Movie> filterByExactTitle(final List<Movie> movies, final String title) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null && title != null) {
            for (final Movie movie : movies) {
                if (movie.getTitle().equalsIgnoreCase(title)) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    public static List<Movie> filterByDirector(final List<Movie> movies, final String directorName) {
        final List<Movie> results = new ArrayList<>();
        String directorNameLower;

        if (movies == null || directorName == null) {
            directorNameLower = null;
        } else {
            directorNameLower = directorName.toLowerCase(Locale.ROOT);
        }

        if (directorNameLower != null) {
            for (final Movie movie : movies) {
                for (final String director : movie.getDirectors()) {
                    if (director.toLowerCase(Locale.ROOT).contains(directorNameLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    public static List<Movie> filterByYear(final List<Movie> movies, final int year) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null) {
            for (final Movie movie : movies) {
                if (movie.getYear() == year) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    public static List<Movie> filterByYearRange(final List<Movie> movies,
                                                final int startYear,
                                                final int endYear) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null) {
            for (final Movie movie : movies) {
                final int year = movie.getYear();
                if (year >= startYear && year <= endYear) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    public static List<Movie> filterByMinRating(final List<Movie> movies, final double minRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null) {
            for (final Movie movie : movies) {
                if (movie.getRating() >= minRating) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    public static List<Movie> filterByMaxRating(final List<Movie> movies, final double maxRating) {
        final List<Movie> results = new ArrayList<>();
        if (movies != null) {
            for (final Movie movie : movies) {
                if (movie.getRating() <= maxRating) {
                    results.add(movie);
                }
            }
        }
        return results;
    }

    public static List<Movie> filterByGenre(final List<Movie> movies, final String genre) {
        final List<Movie> results = new ArrayList<>();
        String genreLower;

        if (movies == null || genre == null) {
            genreLower = null;
        } else {
            genreLower = genre.toLowerCase(Locale.ROOT);
        }

        if (genreLower != null) {
            for (final Movie movie : movies) {
                for (final Genre g : movie.getGenres()) {
                    if (g.toString().toLowerCase(Locale.ROOT).contains(genreLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    public static List<Movie> filterByActor(final List<Movie> movies, final String actorName) {
        final List<Movie> results = new ArrayList<>();
        String actorNameLower;

        if (movies == null || actorName == null) {
            actorNameLower = null;
        } else {
            actorNameLower = actorName.toLowerCase(Locale.ROOT);
        }

        if (actorNameLower != null) {
            for (final Movie movie : movies) {
                for (final String actor : movie.getCastings()) {
                    if (actor.toLowerCase(Locale.ROOT).contains(actorNameLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

    public static List<Movie> filterByCommentKeyword(final List<Movie> movies, final String keyword) {
        final List<Movie> results = new ArrayList<>();
        String keywordLower;

        if (movies == null || keyword == null) {
            keywordLower = null;
        } else {
            keywordLower = keyword.toLowerCase(Locale.ROOT);
        }

        if (keywordLower != null) {
            for (final Movie movie : movies) {
                for (final String comment : movie.getComments()) {
                    if (comment.toLowerCase(Locale.ROOT).contains(keywordLower)) {
                        results.add(movie);
                        break;
                    }
                }
            }
        }

        return results;
    }

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