package com.moviefeaster.Service;

import com.moviefeaster.Model.*;
import com.moviefeaster.Model.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieModel implements MovieModelInterface {

    /** Movie's list */
    private List<Movie> movies;

    /**
     * Public constructor for Java bean convention
     */
    public MovieModel() {
        this.movies = new ArrayList<>();
        fetchMovies(); // Optionally auto-fetch at startup
    }

    @Override
    public void fetchMovies() {
        this.movies = MovieParser.getMoviesFromApi();
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    @Override
    public void writeFile(Format format) {
        System.out.println(format);
        return;
    }

    @Override
    public List<Movie> searchByMultipleFilters(String title, String director, String parsedCast, int year, Genre genre) {
        // Testing
        return getMovies();
    }

    @Override
    public void UpdateComments(int movieID, String comment) {
        for (Movie movie : this.movies) {
            if (movie.getId() == movieID) {
                movie.addComment(comment);
                return;
            }
        }
    }

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
