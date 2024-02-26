package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import moviesapp.viewer.buttons.ClearButton;

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
    private final Pane favoritesPane;
    private final Button favoritesButton;
    private final Pane goPane;
    private final Button goButton;
    private final Button clearButton;

    public WithTitlePanelView(Pane leftPane, Label appTitle, Pane titleAndSearchPane, Label title,
                              TextField searchBar, Pane yearPane, Label year, TextField yearField,
                              Pane favoritesPane, Button favoritesButton, Pane goPane, Button goButton, Button clearButton) {
        this.leftPane = leftPane;
        this.appTitle = appTitle;
        this.titleAndSearchPane = titleAndSearchPane;
        this.title = title;
        this.searchBar = searchBar;
        this.yearPane = yearPane;
        this.year = year;
        this.yearField = yearField;
        this.favoritesPane = favoritesPane;
        this.favoritesButton = favoritesButton;
        this.goPane = goPane;
        this.goButton = goButton;
        this.clearButton = clearButton;

        setupView();
    }

    private void setupView() {
        setTitleAndSearchPane();
        setTitle();
        setSearchBar();
        setYearPane();
        setYear();
        setFavoritesPane();
        setFavoritesButton();
        setGoPane();
        setGoButton();
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

    private void setFavoritesPane(){
        favoritesPane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        favoritesPane.layoutYProperty().bind(yearPane.layoutYProperty().add(60));
    }

    private void setFavoritesButton(){
        favoritesButton.setPrefHeight(26);
        favoritesButton.setPrefWidth(140);
    }

    private void setGoPane(){
        goPane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        goPane.layoutYProperty().bind(yearPane.layoutYProperty().add(6));
    }

    private void setGoButton(){
        goButton.setPrefHeight(26);
        goButton.setPrefWidth(80);
    }
}
