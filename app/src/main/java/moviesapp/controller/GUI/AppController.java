package moviesapp.controller.GUI;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class AppController implements Initializable {

    /////////////////////////////////////////////////////////// FXML Identifiers
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
    public ScrollPane rightScrollPane;
    public AnchorPane imagePane;
    public StackPane rightStackPane;
    public VBox vBox;
    /////////////////////////////////////////////////////////// End FXML Identifiers


    /////////////////////////////////////////////////////////// Responsive Manager attributes
    private final int rightScrollPanePadding = 50;
    private double totalImageWidth = 0;
    private int numberOfImagesPerRow = 0;
    private final int numberOfImages = 19; //TODO: update with real number of images;
    private int numberOfUnprintedImages = numberOfImages;

    /////////////////////////////////////////////////////////// End Responsive Manager attributes


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        responsiveManager();
    }

    /////////////////////////////////////////////////////////// Responsive Manager Section

    private void responsiveManager(){
        setMainAnchorPane();

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
        setFavoritesButton();
        setGo();

        setRightStackPane();
        setRightScrollPane();

        setImagePane();

        setVBox();

        setHBoxes();
    }

    private void setMainAnchorPane(){
        mainAnchorPane.setMinWidth(1400);
        mainAnchorPane.setMinHeight(800);
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
        ratingField.setPromptText("0 to 5");
    }

    private void setButtonsPane(){
        buttonsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(buttonsPane.widthProperty().divide(2)));
        buttonsPane.layoutYProperty().bind(ratingPane.layoutYProperty().add(90));
    }

    private void setFavoritesButton(){
        favorites.setPrefHeight(26);
        favorites.setPrefWidth(140);
    }

    private void setGo(){
        go.layoutXProperty().bind(favorites.layoutXProperty().add(160));
        go.setPrefHeight(26);
        go.setPrefWidth(80);
    }

    private void setRightStackPane(){
        leftPane.widthProperty().addListener((observable, oldValue, newValue) -> rightStackPane.layoutXProperty().setValue(newValue));

        rightStackPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().subtract(leftPane.widthProperty()));
        rightStackPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setRightScrollPane(){
        double sidePaddingMul = 1;
        rightScrollPane.setPadding(new Insets(rightScrollPanePadding, rightScrollPanePadding * sidePaddingMul, rightScrollPanePadding, rightScrollPanePadding * sidePaddingMul));
        rightScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void setImagePane() {
        imagePane.prefWidthProperty().bind(rightScrollPane.widthProperty().subtract(rightScrollPanePadding * 2));
        imagePane.prefHeightProperty().bind(rightScrollPane.heightProperty().subtract(rightScrollPanePadding * 2));
    }

    private void setVBox() {
        double vBoxSpacing = 50;
        vBox.setSpacing(vBoxSpacing);
        vBox.prefWidthProperty().bind(imagePane.prefWidthProperty());
        vBox.prefHeightProperty().bind(imagePane.prefHeightProperty());
    }

    private void setHBoxes() {

        imagePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            initHBoxCriteria(newValue.doubleValue());

            while (numberOfUnprintedImages > 0){
                setHBox();
            }
        });
    }

    private void initHBoxCriteria(double imagePaneWidth){

        int imageViewWidth = 200;
        int minHBoxSpacing = 15;
        double imageViewWidthWithMinHBoxSpacing = imageViewWidth + minHBoxSpacing;

        while(totalImageWidth < imagePaneWidth){
            numberOfImagesPerRow++;
            totalImageWidth = numberOfImagesPerRow * imageViewWidthWithMinHBoxSpacing;
        }
        numberOfImagesPerRow--;
        totalImageWidth -= imageViewWidth;
    }

    private void setHBox(){
        HBox hBox = new HBox();

        Image image = new Image("https://image.tmdb.org/t/p/w200/ldfCF9RhR40mppkzmftxapaHeTo.jpg");

        for(int i = 0; i < numberOfImagesPerRow; i++){
            if(numberOfUnprintedImages > 0){
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                hBox.getChildren().add(imageView);
                numberOfUnprintedImages--;
            } else {
                ImageView placeholder = new ImageView();
                placeholder.setImage(image);
                placeholder.setVisible(false);
                hBox.getChildren().add(placeholder);
            }

            if (i < numberOfImagesPerRow - 1) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hBox.getChildren().add(spacer);
            }
        }

        vBox.getChildren().add(hBox);
    }

    /////////////////////////////////////////////////////////// End Responsive Manager Section
}


