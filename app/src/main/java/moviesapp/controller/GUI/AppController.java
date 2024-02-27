package moviesapp.controller.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import moviesapp.controller.command_line.CLController;
import moviesapp.model.api.Genres;
import moviesapp.model.api.TheMovieDbAPI;
import moviesapp.model.json.JsonReader;
import moviesapp.model.movies.Movie;
import moviesapp.viewer.left_panel.LeftPanelView;
import moviesapp.viewer.left_panel.WithTitlePanelView;
import moviesapp.viewer.left_panel.WithoutTitlePanelView;
import moviesapp.viewer.right_panel.DetailsMode;
import moviesapp.viewer.right_panel.ImagePanelView;
import moviesapp.viewer.right_panel.RightPanelView;

import java.net.URL;
import java.util.ResourceBundle;

import static moviesapp.model.json.JsonReader.apiFilePath;

public class AppController implements Initializable {

    private WithTitlePanelView withTitlePanelViewComponent;
    private WithoutTitlePanelView withoutTitlePanelViewComponent;
    private ImagePanelView imagePanelViewComponent;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        genreSelectorSetUp();
        turnOnSearchWithTitleMode();
        setGUIComponents();
        appTitleButtonClicked();
    }

    private void genreSelectorSetUp(){
        Genres.fillGENRE_NAME_ID_MAP();
        ObservableList<String> genres = FXCollections.observableArrayList(Genres.instance.genreList());
        genreListView.setItems(genres);
        genreListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setGUIComponents(){
        new LeftPanelView(mainAnchorPane, leftPane, appTitleButton, selectModePane, withTitleButton, withoutTitleButton);

        withTitlePanelViewComponent = new WithTitlePanelView(leftPane, appTitleButton, titleAndSearchPane, title, searchBar, yearPane, yearLabel, yearField,
                favoritesWithTitlePane, favoritesWithTitleButton, goWithTitlePane, goWithTitleButton);

        withoutTitlePanelViewComponent = new WithoutTitlePanelView(leftPane, appTitleButton, yearsPane, years, from, singleOrMinYearField,
                to, maxYearField, genresPane, genres, ratingPane, rating, atLeast, ratingField, searchBar, genreListView, buttonsWithoutTitlePane,
                favoritesWithoutTitleButton, goWithoutTitleButton);

        new RightPanelView(leftPane, mainAnchorPane, rightStackPane, rightScrollPane);

        imagePanelViewComponent = new ImagePanelView(gridPane, rightScrollPane);
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
    private void searchCatcherNoTitle(){
        withoutTitlePanelViewComponent.searchCatcherNoTitle();
        updateImagePanelView();
    }

    @FXML
    private void searchCatcherWithTitle(){
        withTitlePanelViewComponent.searchCatcherWithTitle();
        updateImagePanelView();
    }

    private void updateImagePanelView(){
        JsonReader jsonReader = new JsonReader(apiFilePath);
        imagePanelViewComponent.distributeImages(jsonReader.findAllMovies());
    }

    public static void handleClickOnImage(Movie movie) {
        new DetailsMode(movie);
    }
    public void appTitleButtonClicked(){
        new TheMovieDbAPI().popularMovies("1");
        CLController.jsonReaderUpdate();
        updateImagePanelView();
    }








    /////////////////////////////////////////////////////////// Begin FXML Identifiers
    public AnchorPane mainAnchorPane;
    public Pane leftPane;
    public Button appTitleButton;
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

    /////////////////////////////////////////////////////////// End FXML Identifiers
}
