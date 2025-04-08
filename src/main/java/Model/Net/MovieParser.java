package Model.Net;

import Model.Genre;

import java.util.ArrayList;
import java.util.List;

public class MovieParser {
    private static List<Genre> convertGenreIds(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genreIds) {
            Genre g = Genre.fromId(id);
            if (g != null) genres.add(g);
        }
        return genres;
    }
}
