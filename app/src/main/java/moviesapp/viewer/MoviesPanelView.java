package moviesapp.viewer;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import moviesapp.model.api.UrlRequestBuilder;
import moviesapp.model.movies.Favorites;
import moviesapp.model.movies.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesPanelView {

    @FXML public Stage globalStage;
    @FXML public Button addButton;

    @FXML public Button removeButton;
    @FXML public AnchorPane movieDetailsAnchorPane;
    @FXML public ImageView imageView;
    @FXML public Label titleLabelAndYear;
    @FXML public Label usefulInformationLabel;
    @FXML public Label popularityLabel;
    @FXML public Label voteAverageLabel;
    @FXML public Label overviewLabel;
    @FXML public TextFlow textFlow;

    private void addButtonClicked(Movie movie){
        Favorites.instance.add(movie);
        //TODO: update favorites
    }

}
