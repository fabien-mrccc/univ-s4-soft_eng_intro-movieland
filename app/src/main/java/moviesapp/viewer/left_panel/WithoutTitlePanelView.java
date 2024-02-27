package moviesapp.viewer.left_panel;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import moviesapp.model.api.Genres;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.api.UrlRequestBuilder;
import moviesapp.viewer.buttons.WithoutTitleButtons;

import java.util.List;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class WithoutTitlePanelView {
    private final Pane leftPane;
    private final Button appTitleButton;
    private final Pane yearsPane;
    private final Label years;
    private final Label from;
    private final TextField singleOrMinYearField;
    private final Label to;
    private final TextField maxYearField;
    private final Pane genresPane;
    private final Label genres;
    private final Pane ratingPane;
    private final Label rating;
    private final Label atLeast;
    private final TextField ratingField;
    private final TextField searchBar;
    private final ListView<String> genreListView;
    protected final TheMovieDbAPI apiObject = new TheMovieDbAPI();

    public WithoutTitlePanelView(Pane leftPane, Button appTitleButton, Pane yearsPane, Label years, Label from, TextField singleOrMinYearField,
                                 Label to, TextField maxYearField, Pane genresPane, Label genres, Pane ratingPane, Label rating, Label atLeast,
                                 TextField ratingField, TextField searchBar, ListView<String> genreListView, Pane buttonsWithoutTitlePane,
                                Button favoritesWithoutTitleButton, Button goWithoutTitleButton) {
        this.leftPane = leftPane;
        this.appTitleButton = appTitleButton;
        this.yearsPane = yearsPane;
        this.years = years;
        this.from = from;
        this.singleOrMinYearField = singleOrMinYearField;
        this.to = to;
        this.maxYearField = maxYearField;
        this.genresPane = genresPane;
        this.genres = genres;
        this.ratingPane = ratingPane;
        this.rating = rating;
        this.atLeast = atLeast;
        this.ratingField = ratingField;
        this.searchBar = searchBar;
        this.genreListView = genreListView;

        new WithoutTitleButtons(buttonsWithoutTitlePane, leftPane, ratingPane, favoritesWithoutTitleButton, goWithoutTitleButton);


        setupView();
    }

    private void setupView() {
        setYearsPane();
        setYears();
        setGenresPane();
        setGenres();
        setGenreListView();
        setRatingPane();
        setRating();
    }

    private void setYearsPane(){
        yearsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(yearsPane.widthProperty().divide(2)));
        yearsPane.layoutYProperty().bind(appTitleButton.layoutYProperty().add(170));
        yearsPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setYears(){
        from.layoutXProperty().bind(years.layoutXProperty());
        from.layoutYProperty().bind(years.layoutYProperty().add(40));

        singleOrMinYearField.layoutXProperty().bind(searchBar.layoutXProperty());
        singleOrMinYearField.layoutYProperty().bind(from.layoutYProperty().subtract(7));
        singleOrMinYearField.setPromptText(String.valueOf(minAcceptableYearValue));
        singleOrMinYearField.setPrefWidth(70);

        to.layoutXProperty().bind(singleOrMinYearField.layoutXProperty().add(83));
        to.layoutYProperty().bind(from.layoutYProperty());

        maxYearField.layoutXProperty().bind(to.layoutXProperty().add(32));
        maxYearField.layoutYProperty().bind(to.layoutYProperty().subtract(7));
        maxYearField.setPromptText(String.valueOf(maxAcceptableYearValue));
        maxYearField.setPrefWidth(70);
    }

    private void setGenresPane(){
        genresPane.layoutXProperty().bind(yearsPane.layoutXProperty());
        genresPane.layoutYProperty().bind(yearsPane.layoutYProperty().add(100));
        genresPane.prefWidthProperty().bind(yearsPane.prefWidthProperty());
    }

    private void setGenres(){
        genres.layoutXProperty().bind(from.layoutXProperty());
    }

    private void setGenreListView(){
        genreListView.layoutYProperty().bind(genres.layoutYProperty().add(30));
        genreListView.layoutXProperty().bind(genres.layoutXProperty().add(100));
        genreListView.setPrefWidth(150);
        genreListView.prefHeightProperty().bind(leftPane.heightProperty().multiply(0.35));
        genreListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    private void setRatingPane(){
        ratingPane.layoutXProperty().bind(genresPane.layoutXProperty());
        ratingPane.layoutYProperty().bind(genresPane.layoutYProperty().add(leftPane.heightProperty().multiply(0.43)));
        ratingPane.prefWidthProperty().bind(yearsPane.prefWidthProperty());
    }

    private void setRating(){
        atLeast.layoutXProperty().bind(rating.layoutXProperty());
        atLeast.layoutYProperty().bind(rating.layoutYProperty().add(30));

        ratingField.layoutXProperty().bind(atLeast.layoutXProperty().add(89));
        ratingField.layoutYProperty().bind(atLeast.layoutYProperty().subtract(7));
        ratingField.setPrefWidth(75);
        ratingField.setPromptText("0 to 5");
    }

    public void searchCatcherNoTitle(){
        singleOrMinYearField.setStyle("");
        maxYearField.setStyle("");
        String title = "";
        ObservableList<String> selectedGenres = genreListView.getSelectionModel().getSelectedItems();
        String yearFrom = singleOrMinYearField.getText().trim();
        String yearTo = maxYearField.getText().trim();
        String rating = ratingField.getText().trim();
        List<String> selectedGenresId = Genres.genresToGenreIds(selectedGenres);

        if(!yearTest(yearFrom, yearTo)){
            return;
        }

        searchHandling(title, selectedGenresId, yearFrom, yearTo, rating);
    }

    private void searchHandling(String title, List<String> selectedGenresId, String yearFrom, String yearTo, String rating) {
        UrlRequestBuilder.searchMode = "2";
        apiObject.searchMovies(title, yearFrom, yearTo, selectedGenresId, rating, "1");
    }

    /**
     * Test if the years pass all the test then affect a color depending on what happens
     * @param yearFrom starting year
     * @param yearTo ending year
     * @return true if the test are passed false otherwise
     */
    private boolean yearTest(String yearFrom, String yearTo){
        boolean validYearFrom = isValidYear(yearFrom);
        boolean validYearTo = isValidYear(yearTo);

        if (!validYearFrom || !validYearTo || !validYearsOrder(yearFrom, yearTo)) {
            alertYear();

            if (!validYearFrom) {
                singleOrMinYearField.setStyle("-fx-background-color: red;");
            }
            if (!validYearTo) {
                maxYearField.setStyle("-fx-background-color: red;");
            }
            return false;
        }
        return true;
    }

    /**
     * Test if the years are valid meaning either empty or 4 numbers
     * @param year tested year
     * @return true if the year pass false otherwise
     */
    private boolean isValidYear(String year) {
        return year.isEmpty() || year.matches("\\d{4}");
    }

    /**
     * Show an alert page if explaining why the years are not valid
     */
    private void alertYear(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid years. Year must be a 4 numbers and the year in the left field should be less than the year in the right field.");
        alert.showAndWait();
    }

    /**
     * Test if year from is smaller than year to
     * @param yearFrom starting year
     * @param yearTo ending year
     * @return true if the order is right
     */
    private boolean validYearsOrder(String yearFrom, String yearTo){
        if (!yearFrom.isEmpty() && !yearTo.isEmpty()) {
            int from = Integer.parseInt(yearFrom);
            int to = Integer.parseInt(yearTo);
            return from <= to;
        }
        return true;
    }
}
