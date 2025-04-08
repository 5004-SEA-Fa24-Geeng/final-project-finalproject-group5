package Model.Net;

import Model.Genre;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to convert json inputStream to movie object.
 */
public final class MovieParser {
    /** */
    private InputStream jsonString = NetUtil.getTop50MoviesJson();

    private static List<Genre> convertGenreIds(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genreIds) {
            Genre g = Genre.fromId(id);
            if (g != null) genres.add(g);
        }
        return genres;
    }
}
