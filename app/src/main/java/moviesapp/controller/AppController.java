package moviesapp.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class AppController implements Initializable {

    public AnchorPane mainAnchorPane;
    public Pane leftPane;
    public Label appTitle;
    public Pane titleAndSearchPane;
    public Label title;
    public TextField searchBar;
    public Label years;
    public Label from;
    public Label to;
    public TextField singleOrMinYearField;
    public TextField maxYearField;
    public Pane yearsPane;
    public Pane genresPane;
    public Pane ratingPane;
    public Label genres;
    public Label rating;
    public Label atLeast;
    public TextField ratingField;
    public Pane buttonsPane;
    public Button go;
    public Button favorites;
    public ScrollPane rightPane;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        setLeftPane();
        setAppTitle();
        setTitleAndSearchPane();
        setTitle();
        setSearchBar();
        setYearsPane();
        setYears();
        setGenresPane();
        setGenres();
        setRatingPane();
        setRating();
        setButtonsPane();
        setFavorites();
        setGo();
        //setRightPane();
    }

    private void setLeftPane(){
        leftPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().multiply(0.3));
        leftPane.prefHeightProperty().bind(mainAnchorPane.heightProperty().multiply(1));
    }

    private void setAppTitle(){
        appTitle.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(appTitle.widthProperty().divide(2)));
        appTitle.layoutYProperty().bind(leftPane.heightProperty().divide(2).subtract(appTitle.heightProperty().divide(2)));
        appTitle.layoutYProperty().bind(leftPane.layoutYProperty().add(10));
    }

    private void setTitleAndSearchPane(){
        titleAndSearchPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(titleAndSearchPane.widthProperty().divide(2)));
        titleAndSearchPane.layoutYProperty().bind(appTitle.layoutYProperty().add(90));
        titleAndSearchPane.prefWidthProperty().bind(leftPane.widthProperty().multiply(0.9));
    }

    private void setTitle(){
        title.layoutYProperty().bind(titleAndSearchPane.heightProperty().divide(2).subtract(title.heightProperty().divide(2)));
    }

    private void setSearchBar(){
        searchBar.layoutXProperty().bind(title.layoutXProperty().add(60));
        searchBar.layoutYProperty().bind(title.layoutYProperty());
        searchBar.prefWidthProperty().bind(titleAndSearchPane.widthProperty().subtract(70));
    }

    private void setYearsPane(){
        yearsPane.layoutXProperty().bind(titleAndSearchPane.layoutXProperty());
        yearsPane.layoutYProperty().bind(titleAndSearchPane.layoutYProperty().add(60));
        yearsPane.prefWidthProperty().bind(titleAndSearchPane.prefWidthProperty());
    }

    private void setYears(){
        from.layoutXProperty().bind(years.layoutXProperty());
        from.layoutYProperty().bind(years.layoutYProperty().add(30));

        singleOrMinYearField.layoutXProperty().bind(searchBar.layoutXProperty());
        singleOrMinYearField.layoutYProperty().bind(from.layoutYProperty());
        singleOrMinYearField.setPromptText(String.valueOf(minAcceptableYearValue));
        singleOrMinYearField.setPrefWidth(55);

        to.layoutXProperty().bind(singleOrMinYearField.layoutXProperty().add(68));
        to.layoutYProperty().bind(from.layoutYProperty());

        maxYearField.layoutXProperty().bind(to.layoutXProperty().add(32));
        maxYearField.layoutYProperty().bind(to.layoutYProperty());
        maxYearField.setPromptText(String.valueOf(maxAcceptableYearValue));
        maxYearField.setPrefWidth(55);
    }

    private void setGenresPane(){
        genresPane.layoutXProperty().bind(yearsPane.layoutXProperty());
        genresPane.layoutYProperty().bind(yearsPane.layoutYProperty().add(90));
        genresPane.prefWidthProperty().bind(yearsPane.prefWidthProperty());
    }

    private void setGenres(){
        genres.layoutXProperty().bind(from.layoutXProperty());
    }

    private void setRatingPane(){
        ratingPane.layoutXProperty().bind(genresPane.layoutXProperty());
        ratingPane.layoutYProperty().bind(genresPane.layoutYProperty().add(150));
        ratingPane.prefWidthProperty().bind(yearsPane.prefWidthProperty());
    }

    private void setRating(){
        atLeast.layoutXProperty().bind(rating.layoutXProperty());
        atLeast.layoutYProperty().bind(rating.layoutYProperty().add(30));

        ratingField.layoutXProperty().bind(atLeast.layoutXProperty().add(90));
        ratingField.layoutYProperty().bind(atLeast.layoutYProperty());
        ratingField.setPrefWidth(58);
        ratingField.setPromptText("1 to 5");
    }

    private void setButtonsPane(){
        buttonsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(buttonsPane.widthProperty().divide(2)));
        buttonsPane.layoutYProperty().bind(ratingPane.layoutYProperty().add(90));
    }

    private void setFavorites(){
        favorites.setPrefHeight(26);
        favorites.setPrefWidth(140);
    }

    private void setGo(){
        go.layoutXProperty().bind(favorites.layoutXProperty().add(160));
        go.setPrefHeight(26);
        go.setPrefWidth(80);
    }

    private void setRightPane(){
        /*
        rightPane.layoutXProperty().unbind();
        rightPane.layoutXProperty().bind(leftPane.widthProperty());
         */
        rightPane.prefWidthProperty().bind(mainAnchorPane.prefWidthProperty().subtract(leftPane.prefWidthProperty()));
        rightPane.prefHeightProperty().bind(mainAnchorPane.prefHeightProperty());
    }
}


