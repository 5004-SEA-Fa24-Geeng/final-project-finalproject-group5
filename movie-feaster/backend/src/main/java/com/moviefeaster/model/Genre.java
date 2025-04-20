package com.moviefeaster.model;

/**
 * Enum to represent genre. It conforms with the genre ID that movie data provided by api.
 */
public enum Genre {
    /** Action genre with ID 28. */
    ACTION(28),
    
    /** Adventure genre with ID 12. */
    ADVENTURE(12),
    
    /** Animation genre with ID 16. */
    ANIMATION(16),
    
    /** Comedy genre with ID 35. */
    COMEDY(35),
    
    /** Crime genre with ID 80. */
    CRIME(80),
    
    /** Documentary genre with ID 99. */
    DOCUMENTARY(99),
    
    /** Drama genre with ID 18. */
    DRAMA(18),
    
    /** Family genre with ID 10751. */
    FAMILY(10751),
    
    /** Fantasy genre with ID 14. */
    FANTASY(14),
    
    /** History genre with ID 36. */
    HISTORY(36),
    
    /** Horror genre with ID 27. */
    HORROR(27),
    
    /** Music genre with ID 10402. */
    MUSIC(10402),
    
    /** Mystery genre with ID 9648. */
    MYSTERY(9648),
    
    /** Romance genre with ID 10749. */
    ROMANCE(10749),
    
    /** Science Fiction genre with ID 878. */
    SCIENCE_FICTION(878),
    
    /** TV Movie genre with ID 10770. */
    TV_MOVIE(10770),
    
    /** Thriller genre with ID 53. */
    THRILLER(53),
    
    /** War genre with ID 10752. */
    WAR(10752),
    
    /** Western genre with ID 37. */
    WESTERN(37);

    /** The unique TMDb genre ID associated with this genre. */
    private final int genreId;

    /**
     * Constructs an enum constant with the given genre ID.
     * The ID conforms with TMDB database genre ID.
     *
     * @param id the TMDb genre ID associated with the genre
     */
    Genre(final int id) {
        this.genreId = id;
    }

    /**
     * Returns the TMDb genre ID associated with this enum constant.
     *
     * @return the integer ID of the genre
     */
    public int getGenreId() {
        return genreId;
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
            if (genre.genreId == genreId) {
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
