package Model;

public class MovieModel implements MovieModelInterface{
    /** Singleton instance */
    private static MovieModel instance;

    /**
     * Private constructor to prevent direct instantiation
     */
    private MovieModel() {
    }

    /**
     * Gets the singleton instance of the MovieModel.
     * It prevents model being instantiated more than once.
     * @return MovieModel
     */
    public static MovieModel getInstance() {
        if (instance == null) {
            instance = new MovieModel();
        }
        return instance;
    }
}
