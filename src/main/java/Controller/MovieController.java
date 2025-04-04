package Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * MovieController handles all user interactions coming from the view.
 * It coordinates input validation via InputProcessor and delegates
 * data operations to the MovieModel. It also controls the flow between
 * search results and detailed views.
 */
public class MovieController implements MovieControllerInterface {
    private MovieModel model;
    private MovieView view;
    private InputProcessor inputProcessor;
    private List<Movie> currentResults;

    /**
     * Constructs a new MovieController with the given model and view.
     *
     * @param model The data model responsible for movie storage and filtering.
     * @param view  The view that provides the user interface.
     */
    public MovieController(MovieModel model, MovieView view) {
        this.model = model;
        this.view = view;
        this.inputProcessor = new InputProcessor();
        this.currentResults = new ArrayList<>();
        setupViewListeners();
    }

    /**
     * Registers event listeners from the view to controller handlers.
     */
    private void setupViewListeners() {
        view.setSearchHandler((name, director, year, type) ->
                handleMultiFilterSearch(name, director, year, type)
        );
        view.setMovieSelectionHandler(this::handleMovieSelected);
        view.setBackHandler(this::handleBackPressed);
    }

    /**
     * Handles a multi-filter search request. Each field is optional.
     * Input is parsed and validated using InputProcessor.
     *
     * @param nameInput     The raw name input from the view.
     * @param directorInput The raw director input from the view.
     * @param yearInput     The raw year input from the view.
     * @param typeInput     The raw type input from the view.
     */
    @Override
    public void handleMultiFilterSearch(String nameInput, String directorInput, String yearInput, String typeInput) {
        try {
            String name = inputProcessor.optionalParseName(nameInput);
            String director = inputProcessor.optionalParseDirector(directorInput);
            Integer year = inputProcessor.optionalParseYear(yearInput);
            String type = inputProcessor.optionalParseType(typeInput);

            List<Movie> results = model.searchByMultipleFilters(name, director, year, type);
            currentResults = results;
            view.displayMovieSelectionDropdown(results);
        } catch (Exception e) {
            view.displayError("Search failed: " + e.getMessage());
        }
    }

    /**
     * Handles the user's selection of a movie from the search result list.
     *
     * @param selectedMovie The movie selected by the user.
     */
    @Override
    public void handleMovieSelected(Movie selectedMovie) {
        if (selectedMovie == null) {
            view.displayError("No movie selected.");
        } else {
            view.displayMovieProfile(selectedMovie);
        }
    }

    /**
     * Handles the back button action. Returns the user to the movie selection view.
     */
    @Override
    public void handleBackPressed() {
        view.displayMovieSelectionDropdown(currentResults);
    }
}
