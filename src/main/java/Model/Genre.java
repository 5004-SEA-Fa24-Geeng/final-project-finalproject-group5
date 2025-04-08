package Model;

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

    private final int id;

    Genre(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Genre fromId(int id) {
        for (Genre genre : values()) {
            if (genre.id == id) {
                return genre;
            }
        }
        return null;
    }

    public static Genre fromName(String name) {
        for (Genre genre : values()) {
            if (genre.name().equals(name)) {
                return genre;
            }
        }
    }
}
