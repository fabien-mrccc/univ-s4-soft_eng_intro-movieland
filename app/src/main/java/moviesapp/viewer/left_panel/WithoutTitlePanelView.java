package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class WithoutTitlePanelView {
    private final Pane leftPane;
    private final Label appTitle;
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
    private final Pane favoritesPane;
    private final Pane goPane;
    private final Button favoritesButton;
    private final Button goButton;
    private final ListView<String> genreListView;
    private final Button clearButton;

    public WithoutTitlePanelView(Pane leftPane, Label appTitle, Pane yearsPane, Label years, Label from, TextField singleOrMinYearField,
                                 Label to, TextField maxYearField, Pane genresPane, Label genres, Pane ratingPane, Label rating, Label atLeast,
                                 TextField ratingField, TextField searchBar, Pane favoritesPane, Button favoritesButton, Pane goPane, Button goButton, ListView<String> genreListView, Button clearButton) {
        this.leftPane = leftPane;
        this.appTitle = appTitle;
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
        this.favoritesButton = favoritesButton;
        this.goButton = goButton;
        this.genreListView = genreListView;
        this.goPane = goPane;
        this.favoritesPane = favoritesPane;
        this.clearButton = clearButton;
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
        setGoPane();
        setFavoritesPane();
        setFavoritesButton();
        setGoButton();
    }

    private void setYearsPane(){
        yearsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(yearsPane.widthProperty().divide(2)));
        yearsPane.layoutYProperty().bind(appTitle.layoutYProperty().add(170));
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
        genresPane.prefWidthProperty().bind(yearsPane.prefWidthProperty().divide(2).subtract(10));
    }

    private void setGenres(){
        genres.layoutXProperty().bind(from.layoutXProperty());
    }

    private void setGenreListView(){
        genreListView.layoutXProperty().bind(genres.layoutXProperty());
        genreListView.layoutYProperty().bind(genres.layoutYProperty().add(35));
        genreListView.prefWidthProperty().bind(genresPane.widthProperty());
        genreListView.setPrefHeight(178);
        genreListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
    }

    private void setRatingPane(){
        ratingPane.layoutXProperty().bind(genresPane.layoutXProperty().add(genresPane.widthProperty().add(20)));
        ratingPane.layoutYProperty().bind(genresPane.layoutYProperty());
        ratingPane.prefWidthProperty().bind(genresPane.prefWidthProperty());
    }

    private void setRating(){
        atLeast.layoutXProperty().bind(rating.layoutXProperty());
        atLeast.layoutYProperty().bind(rating.layoutYProperty().add(30));

        ratingField.layoutXProperty().bind(atLeast.layoutXProperty().add(89));
        ratingField.layoutYProperty().bind(atLeast.layoutYProperty().subtract(7));
        ratingField.setPrefWidth(75);
        ratingField.setPromptText("0 to 5");
    }

    private void setFavoritesPane(){
        favoritesPane.layoutXProperty().bind(goPane.layoutXProperty());
        favoritesPane.layoutYProperty().bind(goPane.layoutYProperty().add(54));
    }

    private void setFavoritesButton(){
        favoritesButton.setPrefHeight(26);
        favoritesButton.setPrefWidth(140);
    }

    private void setGoPane(){
        goPane.layoutXProperty().bind(ratingPane.layoutXProperty());
        goPane.layoutYProperty().bind(ratingPane.layoutYProperty().add(120));
    }

    private void setGoButton(){
        goButton.setPrefHeight(26);
        goButton.setPrefWidth(80);
    }
}
