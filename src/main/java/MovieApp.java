import Service.MovieModel;
import Service.MovieModelInterface;

public class MovieApp {
    public static void main(String[] args) {
        MovieModel model = MovieModelInterface.getInstance();
    }
}

