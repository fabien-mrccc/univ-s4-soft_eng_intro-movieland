package moviesapp.viewer.left_panel;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.api.UrlRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import moviesapp.viewer.buttons.FavoritesWithTitleButton;
import moviesapp.viewer.buttons.GoWithTitleButton;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class WithTitlePanelView {
    private final Pane leftPane;
    private final Label appTitle;
    private final Pane titleAndSearchPane;
    private final Label title;
    private final TextField searchBar;
    private final Pane yearPane;
    private final Label year;
    private final TextField yearField;
    private final FavoritesWithTitleButton favoritesButtonComponent;
    private final GoWithTitleButton goButtonComponent;
    protected final TheMovieDbAPI apiObject = new TheMovieDbAPI();


    public WithTitlePanelView(Pane leftPane, Label appTitle, Pane titleAndSearchPane, Label title, TextField searchBar,
                              Pane yearPane, Label year, TextField yearField, Pane favoritesPane, Button favoritesButton,
                              Pane goPane, Button goButton) {

        this.leftPane = leftPane;
        this.appTitle = appTitle;
        this.titleAndSearchPane = titleAndSearchPane;
        this.title = title;
        this.searchBar = searchBar;
        this.yearPane = yearPane;
        this.year = year;
        this.yearField = yearField;

        favoritesButtonComponent = new FavoritesWithTitleButton(favoritesPane, yearPane, favoritesButton);
        goButtonComponent = new GoWithTitleButton(goPane, yearPane, goButton);

        setupView();
    }

    private void setupView() {
        setTitleAndSearchPane();
        setTitle();
        setSearchBar();
        setYearPane();
        setYear();
    }

    private void setTitleAndSearchPane(){
        titleAndSearchPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(titleAndSearchPane.widthProperty().divide(2)));
        titleAndSearchPane.layoutYProperty().bind(appTitle.layoutYProperty().add(170));
        titleAndSearchPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setTitle(){
        title.layoutYProperty().bind(titleAndSearchPane.heightProperty().divide(2).subtract(title.heightProperty().divide(2)));
    }

    private void setSearchBar(){
        searchBar.layoutXProperty().bind(title.layoutXProperty().add(60));
        searchBar.layoutYProperty().bind(title.layoutYProperty().subtract(7));
        searchBar.prefWidthProperty().bind(titleAndSearchPane.widthProperty().subtract(70));
    }

    private void setYearPane(){
        yearPane.layoutXProperty().bind(titleAndSearchPane.layoutXProperty());
        yearPane.layoutYProperty().bind(titleAndSearchPane.layoutYProperty().add(80));
        yearPane.prefWidthProperty().bind(titleAndSearchPane.prefWidthProperty());
    }

    private void setYear(){
        year.layoutYProperty().bind(yearPane.heightProperty().divide(2).subtract(year.heightProperty().divide(2)));

        yearField.setPrefWidth(190);
        yearField.layoutXProperty().bind(searchBar.layoutXProperty());
        yearField.layoutYProperty().bind(year.layoutYProperty().subtract(7));
        yearField.setPromptText("from " + minAcceptableYearValue + " to " + maxAcceptableYearValue);
    }

    public void searchCatcherWithTitle(){
        yearField.setStyle("");
        String title = searchBar.getText().trim();
        String year = yearField.getText().trim();
        List<String> selectedGenresId = new ArrayList<>();

        if(!isValidYear(year)){
            yearField.setStyle("-fx-background-color: red;");
            alertYear();
            return;
        }

        searchHandling(title, year);
    }

    /**
     * show an alert page if explaining why the years are not valid
     */
    private void alertYear(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid years. Year must be a 4 numbers and the year in the left field should be less than the year in the right field.");
        alert.showAndWait();
    }

    /**
     * test if the years are valid meaning either empty or 4 numbers
     * @param year tested year
     * @return true if the year pass false otherwise
     */
    private boolean isValidYear(String year) {
        return year.isEmpty() || year.matches("\\d{4}");
    }

    private void searchHandling(String title, String year){
        UrlRequestBuilder.searchMode = "1";
        apiObject.searchMovies(title, year, "", null, null, "1");
    }
}
