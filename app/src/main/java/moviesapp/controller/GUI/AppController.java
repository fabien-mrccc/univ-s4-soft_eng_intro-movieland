package moviesapp.controller.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;
import moviesapp.viewer.left_panel.LeftPanelView;
import moviesapp.viewer.left_panel.WithTitlePanelVIew;
import moviesapp.viewer.left_panel.WithoutTitlePanelView;
import moviesapp.viewer.right_panel.ImagePanelView;
import moviesapp.viewer.right_panel.RightPanelView;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        turnOnSearchWithTitleMode();
        setGUIComponents();
    }

    private void setGUIComponents(){
        new LeftPanelView(mainAnchorPane, leftPane, appTitle, selectModePane, withTitleButton, withoutTitleButton);

        new WithTitlePanelVIew(leftPane, appTitle, titleAndSearchPane, title, searchBar, yearPane, yearLabel, yearField,
                favoritesWithTitlePane, favoritesButtonWithTitle, goWithTitlePane, goButtonWithTitle);

        new WithoutTitlePanelView(leftPane, appTitle, yearsPane, years, from, singleOrMinYearField,
                to, maxYearField, genresPane, genres, ratingPane, rating, atLeast,
                ratingField, searchBar, buttonsWithoutTitlePane, favoritesButtonWithoutTitle, goButtonWithoutTitle, genreListView);

        new RightPanelView(leftPane, mainAnchorPane, rightStackPane, rightScrollPane);

        new ImagePanelView(gridPane, rightScrollPane, Favorites.instance.getFavorites());
    }

    @FXML
    private void turnOnSearchWithTitleMode(){
        withTitlePane.setVisible(true);
        withTitlePane.setDisable(false);
        withoutTitlePane.setVisible(false);
        withoutTitlePane.setDisable(true);
    }

    @FXML
    private void turnOnSearchWithoutTitleMode(){
        withTitlePane.setVisible(false);
        withTitlePane.setDisable(true);
        withoutTitlePane.setVisible(true);
        withoutTitlePane.setDisable(false);
    }

    public static void handleClickOnImage(Movie movie) {
    }

    /////////////////////////////////////////////////////////// Begin FXML Identifiers
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
    public Pane buttonsWithoutTitlePane;
    public Button goButtonWithoutTitle;
    public Button favoritesButtonWithoutTitle;
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
    public Button favoritesButtonWithTitle;
    public Button goButtonWithTitle;
    public Pane withTitlePane;
    public Pane withoutTitlePane;
    public ListView<String> genreListView;
    public GridPane gridPane;

    /////////////////////////////////////////////////////////// End FXML Identifiers
}
