package moviesapp.controller.GUI;

import javafx.fxml.FXML;
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

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;
import static moviesapp.model.api.UrlRequestBuilder.maxAcceptableYearValue;
import static moviesapp.model.api.UrlRequestBuilder.minAcceptableYearValue;

public class AppController implements Initializable {

    /////////////////////////////////////////////////////////// Begin Responsive Manager attributes
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


    /////////////////////////////////////////////////////////// Begin Responsive Manager Section

    private void responsiveManager(){
        turnOnWithTitleMode();

        setMainAnchorPane();

        setLeftPane();
        setAppTitle();

        setSelectModePane();
        setWithTile();
        setWithoutTile();

        setTitleAndSearchPane();
        setTitle();
        setSearchBar();

        setYearPane();
        setYear();

        setGoWithTitlePane();
        setFavoritesWithTitlePane();
        setFavoritesWithTitle();
        setGoWithTitle();

        setYearsPane();
        setYears();

        setGenresPane();
        setGenres();

        setRatingPane();
        setRating();

        setButtonsWithoutTitlePane();
        setFavoritesWithoutTitle();
        setGoWithoutTitle();

        setRightStackPane();
        setRightScrollPane();

        setImagePane();

        setVBox();

        setHBoxes();
    }

    @FXML
    private void turnOnWithTitleMode(){
        withTitle.setVisible(true);
        withTitle.setDisable(false);
        withoutTitle.setVisible(false);
        withoutTitle.setDisable(true);
    }

    @FXML
    private void turnOnWithoutTitleMode(){
        withTitle.setVisible(false);
        withTitle.setDisable(true);
        withoutTitle.setVisible(true);
        withoutTitle.setDisable(false);
    }

    private void setMainAnchorPane(){
        mainAnchorPane.setMinWidth(minWidth);
        mainAnchorPane.setMinHeight(minHeight);
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

    private void setSelectModePane(){
        selectModePane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(selectModePane.widthProperty().divide(2)));
        selectModePane.layoutYProperty().bind(appTitle.layoutYProperty().add(95));
    }

    private void setWithTile(){
        withTile.setPrefHeight(26);
        withTile.setPrefWidth(140);
    }

    private void setWithoutTile(){
        withoutTile.layoutXProperty().bind(withTile.layoutXProperty().add(160));
        withoutTile.setPrefHeight(26);
        withoutTile.setPrefWidth(170);
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

        ratingField.layoutXProperty().bind(atLeast.layoutXProperty().add(89));
        ratingField.layoutYProperty().bind(atLeast.layoutYProperty().subtract(7));
        ratingField.setPrefWidth(75);
        ratingField.setPromptText("0 to 5");
    }

    private void setFavoritesWithTitlePane(){
        favoritesWithTitlePane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        favoritesWithTitlePane.layoutYProperty().bind(yearPane.layoutYProperty().add(60));
    }

    private void setFavoritesWithTitle(){
        favoritesWithTitle.setPrefHeight(26);
        favoritesWithTitle.setPrefWidth(140);
    }

    private void setGoWithTitlePane(){
        goWithTitlePane.layoutXProperty().bind(yearPane.layoutXProperty().add(280));
        goWithTitlePane.layoutYProperty().bind(yearPane.layoutYProperty().add(6));
    }

    private void setGoWithTitle(){
        goWithTitle.setPrefHeight(26);
        goWithTitle.setPrefWidth(80);
    }

    private void setButtonsWithoutTitlePane(){
        buttonsWithoutTitlePane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(buttonsWithoutTitlePane.widthProperty().divide(2)));
        buttonsWithoutTitlePane.layoutYProperty().bind(ratingPane.layoutYProperty().add(110));
    }

    private void setFavoritesWithoutTitle(){
        favoritesWithoutTitle.setPrefHeight(26);
        favoritesWithoutTitle.setPrefWidth(140);
    }

    private void setGoWithoutTitle(){
        goWithoutTitle.layoutXProperty().bind(favoritesWithoutTitle.layoutXProperty().add(160));
        goWithoutTitle.setPrefHeight(26);
        goWithoutTitle.setPrefWidth(80);
    }

    private void setRightStackPane(){
        leftPane.widthProperty().addListener((observable, oldValue, newValue) -> rightStackPane.layoutXProperty().setValue(newValue));

        rightStackPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().subtract(leftPane.widthProperty()));
        rightStackPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setRightScrollPane(){
        double sidePaddingMul = 1;
        rightScrollPane.setPadding(new Insets(rightScrollPanePadding, rightScrollPanePadding * sidePaddingMul, 0, rightScrollPanePadding * sidePaddingMul));
        rightScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private void setImagePane() {
        imagePane.prefWidthProperty().bind(rightScrollPane.widthProperty().subtract(rightScrollPanePadding * 2));
        imagePane.prefHeightProperty().bind(rightScrollPane.heightProperty().subtract(rightScrollPanePadding * 2));
    }

    private void setVBox() {
        double vBoxSpacing = 50;
        vBox.setSpacing(vBoxSpacing);
        vBox.setPadding(new Insets(0,0,rightScrollPanePadding,0));
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
        Image image = new Image("https://image.tmdb.org/t/p/w300/ldfCF9RhR40mppkzmftxapaHeTo.jpg");
        int imageWidth = 250;

        for(int i = 0; i < numberOfImagesPerRow; i++){
            if(numberOfUnprintedImages > 0){
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(imageWidth);
                hBox.getChildren().add(imageView);
                numberOfUnprintedImages--;
            } else {
                ImageView placeholder = new ImageView();
                placeholder.setImage(image);
                placeholder.setVisible(false);
                placeholder.setPreserveRatio(true);
                placeholder.setFitWidth(imageWidth);
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
    public Button goWithoutTitle;
    public Button favoritesWithoutTitle;
    public ScrollPane rightScrollPane;
    public AnchorPane imagePane;
    public StackPane rightStackPane;
    public VBox vBox;
    public Pane yearPane;
    public Label year;
    public TextField yearField;
    public Pane selectModePane;
    public Button withTile;
    public Button withoutTile;
    public Pane favoritesWithTitlePane;
    public Pane goWithTitlePane;
    public Button favoritesWithTitle;
    public Button goWithTitle;
    public Pane withTitle;
    public Pane withoutTitle;

    /////////////////////////////////////////////////////////// End FXML Identifiers
}
