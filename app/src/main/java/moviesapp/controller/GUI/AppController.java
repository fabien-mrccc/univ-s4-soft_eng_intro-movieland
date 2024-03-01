package moviesapp.controller.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import moviesapp.model.api.Genres;
import moviesapp.model.api.SearchCriteria;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.exceptions.*;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movies;
import moviesapp.viewer.buttons.ClearButton;
import moviesapp.model.movies.Movie;
import moviesapp.viewer.left_panel.LeftPanelView;
import moviesapp.viewer.left_panel.WithTitlePanelView;
import moviesapp.viewer.left_panel.WithoutTitlePanelView;
import moviesapp.viewer.right_panel.DetailsMode;
import moviesapp.viewer.right_panel.ImagePanelView;
import moviesapp.viewer.right_panel.RightPanelView;

import java.net.URL;
import java.util.ResourceBundle;

import static moviesapp.model.api.Genres.fillGENRE_NAME_ID_MAP;
import static moviesapp.model.api.TheMovieDbAPI.*;
import static moviesapp.model.json.JsonReader.*;
import static moviesapp.model.json.JsonWriter.FAVORITES_WRITER;
import static moviesapp.model.movies.Favorites.asMovies;

public class AppController implements Initializable {

    private WithTitlePanelView withTitlePanelViewComponent;
    private WithoutTitlePanelView withoutTitlePanelViewComponent;
    private static ImagePanelView imagePanelViewComponent;
    private static DetailsMode currentDetailsWindow;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        genreSelectorSetUp();
        turnOnSearchWithTitleMode();
        setGUIComponents();
        appTitleButtonClicked();
    }

    /**
     * Sets up the genre selector.
     */
    private void genreSelectorSetUp() {
        fillGENRE_NAME_ID_MAP();
        ObservableList<String> genres = FXCollections.observableArrayList(Genres.instance.genreList());
        genreListView.setItems(genres);
        genreListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Sets up the GUI components.
     */
    private void setGUIComponents() {
        new LeftPanelView(mainAnchorPane, leftPane, appTitlePane, appTitleButton, selectModePane, withTitleButton, withoutTitleButton);

        withTitlePanelViewComponent = new WithTitlePanelView
                (leftPane, appTitleButton, titleAndSearchPane, title, searchBar, yearPane, yearLabel,yearField,
                favoritesWithTitlePane, favoritesWithTitleButton, goWithTitlePane, goWithTitleButton);

        withoutTitlePanelViewComponent = new WithoutTitlePanelView
                (leftPane, appTitleButton, yearsPane, years, from, minYearField,
                to, maxYearField, genresPane, genres, ratingPane, rating, atLeast,
                ratingField, searchBar, favoritesWithoutTitlePane, favoritesWithoutTitleButton,
                goWithoutTitlePane, goWithoutTitleButton, genreListView);

        new RightPanelView(leftPane, mainAnchorPane, rightStackPane, rightScrollPane);

        imagePanelViewComponent = new ImagePanelView(gridPane, rightScrollPane);

        new ClearButton(clearWithTitlePane, clearWithTitleButton, leftPane, withTitlePane);
        new ClearButton(clearWithoutTitlePane, clearWithoutTitleButton, leftPane, withoutTitlePane);

        clearWithTitleButton.setVisible(false);
        clearWithoutTitleButton.setVisible(false);
    }

    /**
     * Turns on the search with title mode.
     */
    @FXML
    private void turnOnSearchWithTitleMode() {
        withTitlePane.setVisible(true);
        withoutTitlePane.setVisible(false);
        clearWithoutTitleButton.setVisible(false);
    }

    /**
     * Turns on the search without title mode.
     */
    @FXML
    private void turnOnSearchWithoutTitleMode() {
        withTitlePane.setVisible(false);
        withoutTitlePane.setVisible(true);
        clearWithTitleButton.setVisible(false);
    }

    /**
     * Handles the search event with title.
     */
    @FXML
    private void searchCatcherWithTitle() {

        SearchCriteria criteria = withTitlePanelViewComponent.searchCatcherWithTitle();
        boolean inputSuccess = true;
        try {
            checkYears(criteria.minYear, criteria.maxYear);
        }
        catch (IntervalException | NotAPositiveIntegerException | NotValidYearsException e) {
            alterInput(yearField, e);
            inputSuccess = false;
        }

        if (inputSuccess) {
            try {
                searchMoviesWithCriteria(criteria);
            }
            catch (SelectModeException ignored) {
            }
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
    }

    /**
     * Modifies a TextField's appearance to indicate invalid input and displays an error alert.
     *
     * @param field The TextField with invalid input.
     * @param e The Exception representing the error.
     */
    private void alterInput(TextField field, Exception e) {
        field.setStyle(getAlertStyle());
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Handles the search event without title.
     */
    @FXML
    private void searchCatcherWithoutTitle() {

        SearchCriteria criteria = withoutTitlePanelViewComponent.searchCatcherWithoutTitle();
        boolean inputSuccess = true;
        try {
            checkYears(criteria.minYear, criteria.maxYear);
        }
        catch (IntervalException | NotAPositiveIntegerException | NotValidYearsException e) {
            alterTwoInputs(minYearField, maxYearField, e);
            inputSuccess = false;
        }
        try {
            checkMinVoteAverage(criteria.minVoteAverage);
        }
        catch (IntervalException | NotAPositiveIntegerException e) {
            alterInput(ratingField, e);
            inputSuccess = false;
        }
        if (inputSuccess) {
            try {
                searchMoviesWithCriteria(criteria);
            }
            catch (SelectModeException ignored) {
            }
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
    }

    /**
     * Modifies two TextField object appearance to indicate invalid inputs and displays an error alert.
     *
     * @param e The Exception representing the error.
     */
    private void alterTwoInputs(TextField field1, TextField field2, Exception e) {
        field1.setStyle(getAlertStyle());
        alterInput(field2, e);
    }

    /**
     * Returns the CSS style string for customizing the appearance of an alert.
     *
     * @return The CSS style string for an alert's appearance.
     */
    private String getAlertStyle() {
        return "-fx-background-color: #d7982f; -fx-prompt-text-fill: #0f0d13;";
    }

    /**
     * Updates the image panel view with favorite movies and makes the clear button visible. (with title view)
     */
    @FXML
    private void favoritesWithTitleButtonClicked() {
        updateImagePanelView(asMovies());
        clearWithTitleButton.setVisible(true);
    }

    /**
     * Updates the image panel view with favorite movies and makes the clear button visible. (without title view)
     */
    @FXML
    private void favoritesWithoutTitleButtonClicked() {
        updateImagePanelView(asMovies());
        clearWithoutTitleButton.setVisible(true);
    }

    @FXML
    public void previousPage() {
        try {
            TheMovieDbAPI.switchToPreviousPage();
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
        catch (NoPreviousPageException ignored) {
            System.err.println("ERR");
        }
    }

    @FXML
    public void nextPage() {
        try {
            TheMovieDbAPI.switchToNextPage();
            updateImagePanelView(SEARCH_READER.findAllMovies());
        }
        catch (NoNextPageException ignored) {
            System.err.println("ERR");
        }
    }

    public static void updateImagePanelView(Movies movies){
        imagePanelViewComponent.distributeImages(movies);
    }

    public static void handleClickOnImage(Movie movie) {
        currentDetailsWindow = new DetailsMode(movie);
    }

    public void appTitleButtonClicked(){
        TheMovieDbAPI.popularMoviesFirstPage();
        updateImagePanelView(SEARCH_READER.findAllMovies());
    }


    /**
     * remove the movie from favorites when the remove button is clicked
     * @param movie the movie of which we want the details
     */
    public static void removeButtonClicked(Movie movie){
        Favorites.remove(movie);
        applyFavoritesModifications();
    }

    /**
     * add the movie to favorites when the add button is clicked
     * @param movie the movie of which we want the details
     */
    public static void addButtonClicked(Movie movie){
        Favorites.add(movie);
        applyFavoritesModifications();
    }

    private static void applyFavoritesModifications(){
        updateImagePanelView(asMovies());
        currentDetailsWindow.globalStage.close();
        FAVORITES_WRITER.saveFavorites(asMovies());
    }


    /////////////////////////////////////////////////////////// HEAD FXML Identifiers
    public AnchorPane mainAnchorPane;
    public Pane leftPane;
    public Button appTitleButton;
    public Pane titleAndSearchPane;
    public Label title;
    public TextField searchBar;
    public Label years;
    public Label from;
    public Label to;
    public TextField minYearField;
    public TextField maxYearField;
    public Pane yearsPane;
    public Pane genresPane;
    public Pane ratingPane;
    public Label genres;
    public Label rating;
    public Label atLeast;
    public TextField ratingField;
    public Button goWithoutTitleButton;
    public Button favoritesWithoutTitleButton;
    public ScrollPane rightScrollPane;
    public StackPane rightStackPane;
    public Pane yearPane;
    public Label yearLabel;
    public TextField yearField;
    public Pane selectModePane;
    public Button withTitleButton;
    public Button withoutTitleButton;
    public Pane favoritesWithTitlePane;
    public Pane goWithTitlePane;
    public Button favoritesWithTitleButton;
    public Button goWithTitleButton;
    public Pane withTitlePane;
    public Pane withoutTitlePane;
    public ListView<String> genreListView;
    public GridPane gridPane;
    public Pane goWithoutTitlePane;
    public Pane favoritesWithoutTitlePane;
    public Pane clearWithTitlePane;
    public Pane clearWithoutTitlePane;
    public Button clearWithTitleButton;
    public Button clearWithoutTitleButton;
    public Pane appTitlePane;

    /////////////////////////////////////////////////////////// END FXML Identifiers
}
