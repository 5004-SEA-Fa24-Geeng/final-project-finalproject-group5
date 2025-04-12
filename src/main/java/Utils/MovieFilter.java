package Utils;

import Model.Genre;
import Model.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Movie filter class, providing intuitive static filtering methods
 */
public class MovieFilter {
    // Private constructor to prevent instantiation
    private MovieFilter() {
        //empty
    }


    /**
     * Filter movies with titles containing a specific keyword
     *
     * @param movies the list of movies to filter
     * @param keyword the title keyword
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByTitle(List<Movie> movies, String keyword) {
        if (movies == null || keyword == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseKeyword = keyword.toLowerCase();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(lowerCaseKeyword)) {
                results.add(movie);
            }
        }
        return results;
    }


    /**
     * Filter movies with an exact title match (case insensitive)
     *
     * @param movies the list of movies to filter
     * @param title the exact movie title
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByExactTitle(List<Movie> movies, String title) {
        if (movies == null || title == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseTitle = title.toLowerCase();

        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().equals(lowerCaseTitle)) {
                results.add(movie);
            }
        }
        return results;
    }


    /**
     * Filter movies directed by a specific director
     *
     * @param movies the list of movies to filter
     * @param directorName the director name
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByDirector(List<Movie> movies, String directorName) {
        if (movies == null || directorName == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseDirectorName = directorName.toLowerCase();

        for (Movie movie : movies) {
            for (String director : movie.getDirectors()) {
                if (director.toLowerCase().contains(lowerCaseDirectorName)) {
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
     * @param year the release year
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByYear(List<Movie> movies, int year) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getYear() == year) {
                results.add(movie);
            }
        }
        return results;
    }


    /**
     * Filter movies within a specific year range
     *
     * @param movies the list of movies to filter
     * @param startYear the start year (inclusive)
     * @param endYear the end year (inclusive)
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByYearRange(List<Movie> movies, int startYear, int endYear) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
            int year = movie.getYear();
            if (year >= startYear && year <= endYear) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filter movies with a rating greater than or equal to a specified value
     *
     * @param movies the list of movies to filter
     * @param minRating the minimum rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMinRating(List<Movie> movies, float minRating) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
            if (movie.getRating() >= minRating) {
                results.add(movie);
            }
        }
        return results;
    }

    /**
     * Filter movies with a rating less than or equal to a specified value
     *
     * @param movies the list of movies to filter
     * @param maxRating the maximum rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMaxRating(List<Movie> movies, float maxRating) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
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
     * @param genre the movie genre
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByGenre(List<Movie> movies, String genre) {
        if (movies == null || genre == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseGenre = genre.toLowerCase(); // Pre-process genre to lowercase

        for (Movie movie : movies) {
            for (Genre g : movie.getGenres()) {
                if (g.toString().toLowerCase().contains(lowerCaseGenre)) {
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
     * @param movies the list of movies to filter
     * @param actorName the actor name
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByActor(List<Movie> movies, String actorName) {
        if (movies == null || actorName == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseActorName = actorName.toLowerCase();

        for (Movie movie : movies) {
            for (String actor : movie.getCastings()) {
                if (actor.toLowerCase().contains(lowerCaseActorName)) {
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
     * @param movies the list of movies to filter
     * @param keyword the comment keyword
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByCommentKeyword(List<Movie> movies, String keyword) {
        if (movies == null || keyword == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        String lowerCaseKeyword = keyword.toLowerCase();
        for (Movie movie : movies) {
            for (String comment : movie.getComments()) {
                if (comment.toLowerCase().contains(lowerCaseKeyword)) {
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
     * @param movies the list of movies to filter
     * @param minRating the minimum in-app rating
     * @return list of movies that match the condition
     */
    public static List<Movie> filterByMinInAppRating(List<Movie> movies, double minRating) {
        if (movies == null) {
            return new ArrayList<>();
        }
        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
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
    public static List<Movie> combineAnd(List<Movie> list1, List<Movie> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }

        List<Movie> results = new ArrayList<>();
        HashSet<Movie> set = new HashSet<>(list2);

        for (Movie movie : list1) {
            if (set.contains(movie)) {
                results.add(movie);
            }
        }
        return results;
    }

}
