package moviesapp.viewer.right_panel;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.ArrayList;
import java.util.List;

public class ImagePanelView {
    private final Pane imagePane;
    private final ScrollPane rightScrollPane;
    private final VBox vBox;
    private int numberOfImagesPerRow = 0;
    private double totalImageWidth = 0;
    private int numberOfImages;
    private int numberOfUnprintedImages;
    static final int rightScrollPanePadding = 50;

    public ImagePanelView(Pane imagePane, ScrollPane rightScrollPane, VBox vBox, Movies movies) {
        this.numberOfImages = movies.size();
        this.numberOfUnprintedImages = numberOfImages;
        this.imagePane = imagePane;
        this.rightScrollPane = rightScrollPane;
        this.vBox = vBox;

        setupView(movies);
    }

    public void setupView(Movies movies) {
        setImagePane();
        setVBox();
        setHBoxes(movies);
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

    private void setHBoxes(Movies movies) {
        Movies remainingMovies = movies;

        imagePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            initHBoxCriteria(newValue.doubleValue());
            List<Integer> indexes = new ArrayList<>();
            for(int i=0; i<numberOfImagesPerRow;i++){
                indexes.add(i);
            }
            while(numberOfUnprintedImages != 0){
                Movies moviesToPlaceHorizontally = remainingMovies.get(indexes);
                setHBox(moviesToPlaceHorizontally);
                remainingMovies.remove(moviesToPlaceHorizontally);
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

    private void setHBox(Movies movies){
        HBox hBox = new HBox();

        for(Movie movie : movies){
            Image image = new Image("https://image.tmdb.org/t/p/w300"+movie.posterPath());
            int imageWidth = 230;

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

            if (movies.get(movies.size()-1).equals(movie)) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hBox.getChildren().add(spacer);
            }
        }

        vBox.getChildren().add(hBox);
    }
}
