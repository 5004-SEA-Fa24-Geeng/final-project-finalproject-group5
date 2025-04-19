package com.moviefeaster.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that pulls data from TMDB API.
 * See details from https://developer.themoviedb.org/reference/intro/getting-started
 */
@SuppressWarnings({"checkstyle:MethodLength", "checkstyle:MultipleStringLiterals"})
public final class NetUtil {

    /** Loads environment variables from the `.env` file located in the project root. */
    private static final Dotenv DOTENV = Dotenv.load(); // Renamed to ALL_CAPS

    /** Access Api keys from `.env` file. */
    private static final String API_TOKEN = DOTENV.get("TMDB_API_TOKEN");

    /** TMDB default api url. */
    private static final String API_URL = "https://api.themoviedb.org/3/discover/movie";

    /** Number of movies to pull from API. */
    private static final int MOVIE_RESULTS_AMOUNT = 200;

    /** OkHttpClient instance used for making HTTP requests to the TMDb API. */
    private static final OkHttpClient CLIENT = new OkHttpClient(); // Renamed to ALL_CAPS

    /** Private constructor preventing instantiation. */
    private NetUtil() {
        // Prevent instantiation
    }

    /**
     * Method to build URL to the TMDB database for the top movies with sorting by popularity in this month.
     *
     * @param page there are 20 movies per page in TMDB API.
     * @return URL for the month's top movies.
     */
    private static String buildUrl(final int page) {
        return String.format(
                "%s?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc&page=%d",
                API_URL,
                page
        );
    }

    /**
     * Get the top 50 movies (with popularity) of this month's as Input stream from TMDB API.
     *
     * @return top 50 movies JSON string as input stream
     */
    public static InputStream getTop50MoviesJson() {
        final ObjectMapper mapper = new ObjectMapper();
        final ArrayNode allResults = mapper.createArrayNode();
        final int totalPages = (int) Math.ceil(MOVIE_RESULTS_AMOUNT / 20.0);

        try {
            for (int page = 1; page <= totalPages; page++) {
                final String url = buildUrl(page);

                final Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + API_TOKEN)
                        .build();

                try (Response response = CLIENT.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        final JsonNode root = mapper.readTree(response.body().string());
                        final JsonNode results = root.path("results");
                        if (results.isArray()) {
                            allResults.addAll((ArrayNode) results);
                        }
                    } else {
                        System.err.println("Error fetching movies (Page " + page + "): HTTP status " + response.code());
                    }
                }
            }

            final byte[] jsonBytes = mapper.writeValueAsBytes(allResults);
            return new ByteArrayInputStream(jsonBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return InputStream.nullInputStream();
        }
    }

    /**
     * Fetches crew information for a specific movie by ID.
     *
     * @param movieId the TMDB movie ID
     * @return the crew data as InputStream
     */
    public static InputStream getCrewJsonStream(final int movieId) {
        final String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits";
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_TOKEN)
                .build();

        try {
            final Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                System.err.println("Failed to fetch crew for movie ID " + movieId + ": " + response.code());
                return null;
            }

            return response.body().byteStream();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}