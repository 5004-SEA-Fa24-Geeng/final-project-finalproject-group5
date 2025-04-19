package com.moviefeaster.model;

/**
 * Enum to represent genre. It conforms with the genre ID that movie data provided by api.
 */
public enum Genre {
    ACTION(28),
    ADVENTURE(12),
    ANIMATION(16),
    COMEDY(35),
    CRIME(80),
    DOCUMENTARY(99),
    DRAMA(18),
    FAMILY(10751),
    FANTASY(14),
    HISTORY(36),
    HORROR(27),
    MUSIC(10402),
    MYSTERY(9648),
    ROMANCE(10749),
    SCIENCE_FICTION(878),
    TV_MOVIE(10770),
    THRILLER(53),
    WAR(10752),
    WESTERN(37);

    /** The unique TMDb genre ID associated with this genre. */
    private final int id;

    /**
     * Constructs an enum constant with the given genre ID.
     * The ID conforms with TMDB database genre ID.
     *
     * @param id the TMDb genre ID associated with the genre.
     */
    Genre(final int id) {
        this.id = id;
    }

    /**
     * Returns the TMDb genre ID associated with this enum constant.
     *
     * @return the integer ID of the genre
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the corresponding enum based on the provided genre ID.
     *
     * @param genreId the TMDb genre ID
     * @return the corresponding enum, or null if no match is found
     */
    public static Genre fromId(final int genreId) {
        Genre result = null;
        for (final Genre genre : values()) {
            if (genre.id == genreId) {
                result = genre;
                break;
            }
        }
        return result;
    }

    /**
     * Retrieves the corresponding enum based on the name of the genre.
     * This comparison is case-sensitive and must match the enum name exactly.
     *
     * @param name the name of the genre (e.g., "ACTION", "DRAMA")
     * @return the corresponding enum, or null if no match is found.
     */
    public static Genre fromName(final String name) {
        Genre result = null;
        for (final Genre genre : values()) {
            if (genre.name().equals(name)) {
                result = genre;
                break;
            }
        }
        return result;
    }
}
