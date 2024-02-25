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

    public void showDetails(Movie movie){
        globalStage = new Stage();
        globalStage.setMinHeight(500);
        globalStage.setMinWidth(900);
        initMovieDetailsAnchorPane();

        initImageView();
        Image movieImage = new Image(UrlRequestBuilder.imageBaseURL + UrlRequestBuilder.imageSize + movie.posterPath());
        imageView.setImage(movieImage);

        initTitleLabelAndYear(movie);
        initUsefulInformationLabel(movie);
        initPopularityLabel(movie);
        initVoteAverageLabel(movie);
        initOverviewLabel();
        initTextFlow(movie);

        if(Favorites.instance.contains(movie)){
            initRemoveButton(movie);
            movieDetailsAnchorPane.getChildren().add(removeButton);
        }
        else{
            initAddButton(movie);
            movieDetailsAnchorPane.getChildren().add(addButton);
        }

        movieDetailsAnchorPane.getChildren().addAll(imageView,titleLabelAndYear,usefulInformationLabel,popularityLabel,voteAverageLabel,overviewLabel,textFlow);

        movieDetailsAnchorPane.setVisible(true);
        Scene globalScene = new Scene(movieDetailsAnchorPane, 900, 500);
        globalStage.setTitle("details");
        globalStage.setScene(globalScene);
        globalStage.show();
    }


    /**
     * when the details button is clicked, it shows the details of the movie
     */
    public void detailsButtonClicked(){
        List<String> genres = new ArrayList<>();
        genres.add("action");
        Movie movie = new Movie(false, "",genres, "609681", "en",
                "The Marvels", "Carol Danvers, aka Captain Marvel, has reclaimed her identity from the tyrannical Kree and taken revenge on the Supreme Intelligence. But unintended consequences see Carol shouldering the burden of a destabilized universe. When her duties send her to an anomalous wormhole linked to a Kree revolutionary, her powers become entangled with that of Jersey City super-fan Kamala Khan, aka Ms. Marvel, and Carol’s estranged niece, now S.A.B.E.R. astronaut Captain Monica Rambeau. Together, this unlikely trio must team up and learn to work in concert to save the universe.", 4.0, "/9GBhzXMFjgcZ3FdR9w3bUMMTps5.jpg",
                "2023-11-28", "Marvels", false, 4.0, 2000);
        showDetails(movie);
    }

    /**
     * initialise movieDetailsAnchorPane
     */
    private void initMovieDetailsAnchorPane(){
        movieDetailsAnchorPane = new AnchorPane();
        movieDetailsAnchorPane.prefHeight(500);
        movieDetailsAnchorPane.prefWidth(900);
        movieDetailsAnchorPane.setId("movieDetailsAnchorPane");
    }

    /**
     * initialise imageView
     */
    private void initImageView(){
        imageView = new ImageView();
        imageView.setLayoutX(20);
        imageView.setLayoutY(20);
    }

    /**
     * initialise the title and the release year of the movie
     * @param movie the movie of which we want the details
     */
    private void initTitleLabelAndYear(Movie movie){
        titleLabelAndYear = new Label(movie.title() + "(" + movie.getReleaseYear() + ")");
        titleLabelAndYear.setLayoutX(250);
        titleLabelAndYear.setLayoutY(0);
        titleLabelAndYear.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size: 70px; -fx-font-weight: 15;");
    }

    /**
     * initialise usefulInformationLabel
     * @param movie the movie of which we want the details
     */
    private void initUsefulInformationLabel(Movie movie){
        usefulInformationLabel = new Label(movie.releaseDate() + " (" + movie.originalLanguage() + ") " + movie.genresToString());
        usefulInformationLabel.setLayoutX(250);
        usefulInformationLabel.setLayoutY(80);
        usefulInformationLabel.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size: 20;");
    }

    /**
     * initialise popularityLabel
     * @param movie the movie of which we want the details
     */
    private void initPopularityLabel(Movie movie){
        popularityLabel = new Label("Popularity: "+ movie.popularity());
        popularityLabel.setLayoutX(250);
        popularityLabel.setLayoutY(120);
        popularityLabel.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size:30px; -fx-font-weight: 25;");
    }

    /**
     * initialise voteAverageLabel
     * @param movie the movie of which we want the details
     */
    private void initVoteAverageLabel(Movie movie){
        voteAverageLabel = new Label("Vote Average: "+ movie.minVoteAverage());
        voteAverageLabel.setLayoutX(250);
        voteAverageLabel.setLayoutY(160);
        voteAverageLabel.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size:30px; -fx-font-weight: 25;");
    }

    /**
     * initialise overviewLabel
     */
    private void initOverviewLabel(){
        overviewLabel = new Label("Overview:");
        overviewLabel.setLayoutX(250);
        overviewLabel.setLayoutY(210);
        overviewLabel.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size:35px; -fx-font-weight: 25;");
    }

    /**
     * initialise textFlow
     * @param movie the movie of which we want the details
     */
    private void initTextFlow(Movie movie){
        textFlow = new TextFlow();
        Text overviewContentText = new Text(movie.overviewToString(72));
        textFlow.setLayoutX(250);
        textFlow.setLayoutY(250);
        textFlow.prefWidth(60);
        textFlow.prefHeight(20);
        overviewContentText.setStyle("-fx-font-family: 'Arial Dark'; -fx-font-size:20px; -fx-font-weight: 10;");
        textFlow.getChildren().add(overviewContentText);
    }

    /**
     * initialise the remove button
     * @param movie the movie of which we want the details
     */
    private void initRemoveButton(Movie movie){
        removeButton = new Button("remove");
        removeButton.setLayoutX(20);
        removeButton.setLayoutY(390);
        removeButton.setPrefWidth(220);
        removeButton.setPrefHeight(40);
        removeButton.setOnAction(event -> removeButtonClicked(movie));
    }

    /**
     * remove the movie from favorites when the remove button is clicked
     * @param movie the movie of which we want the details
     */
    private void removeButtonClicked(Movie movie){
        Favorites.instance.remove(movie);
        //TODO: update favorites
    }

    /**
     * initialise the add button
     * @param movie the movie of which we want the details
     */
    private void initAddButton(Movie movie){
        addButton = new Button("add");
        addButton.setLayoutX(20);
        addButton.setLayoutY(390);
        addButton.setPrefWidth(220);
        addButton.setPrefHeight(40);
        addButton.setOnAction(event -> addButtonClicked(movie) );
    }

    /**
     * add the movie to favorites when the add button is clicked
     * @param movie the movie of which we want the details
     */
    private void addButtonClicked(Movie movie){
        Favorites.instance.add(movie);
        //TODO: update favorites
    }

}
