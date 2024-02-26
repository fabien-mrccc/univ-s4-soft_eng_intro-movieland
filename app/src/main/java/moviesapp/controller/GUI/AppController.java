package moviesapp.controller.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import moviesapp.controller.command_line.CLController;
import moviesapp.model.json.JsonReader;
import moviesapp.viewer.buttons.ClearButton;
import moviesapp.viewer.left_panel.LeftPanelView;
import moviesapp.viewer.left_panel.WithTitlePanelView;
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

        new WithTitlePanelView(leftPane, appTitle, titleAndSearchPane, title, searchBar, yearPane, yearLabel, yearField,
                favoritesWithTitlePane, favoritesButtonWithTitle, goWithTitlePane, goButtonWithTitle, clearWithTitleButton);

        new WithoutTitlePanelView(leftPane, appTitle, yearsPane, years, from, singleOrMinYearField,
                to, maxYearField, genresPane, genres, ratingPane, rating, atLeast,
                ratingField, searchBar, favoritesWithoutTitlePane, favoritesWithoutTitleButton, goWithoutTitlePane, goWithoutTitleButton, genreListView, clearWithoutTitleButton);

        new RightPanelView(leftPane, mainAnchorPane, rightStackPane, rightScrollPane);

        new ImagePanelView(gridPane, rightScrollPane, new JsonReader(CLController.apiFilePath).findAllMovies());

        new ClearButton(clearWithTitlePane, clearWithTitleButton, leftPane, withTitlePane);
        new ClearButton(clearWithoutTitlePane, clearWithoutTitleButton, leftPane, withoutTitlePane);
        clearWithTitleButton.setVisible(false);
        clearWithoutTitleButton.setVisible(false);
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

    @FXML
    public void favoritesWithoutTitleButtonClicked(){
        clearWithoutTitleButton.setVisible(true);
    }

    @FXML
    public void favoritesWithTitleButtonClicked(){
        clearWithTitleButton.setVisible(true);
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
    public Pane goWithoutTitlePane;
    public Button goWithoutTitleButton;
    public Pane favoritesWithoutTitlePane;
    public Button favoritesWithoutTitleButton;
    public Pane clearWithTitlePane;
    public Pane clearWithoutTitlePane;
    public Button clearWithTitleButton;
    public Button clearWithoutTitleButton;

    /////////////////////////////////////////////////////////// End FXML Identifiers
}
