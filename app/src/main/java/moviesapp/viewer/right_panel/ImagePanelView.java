package moviesapp.viewer.right_panel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import moviesapp.model.movies.Movie;
import moviesapp.model.movies.Movies;

import java.util.Objects;

import static moviesapp.controller.GUI.AppController.handleClickOnImage;
import static moviesapp.model.api.UrlRequestBuilder.imageBaseURL;
import static moviesapp.model.api.UrlRequestBuilder.imageSize;
import static moviesapp.viewer.right_panel.RightPanelView.rightScrollPanePadding;

public class ImagePanelView {
    private final ScrollPane rightScrollPane;
    private final GridPane gridPane;
    private final int imageWidth = 258;
    private final int numberOfImagesPerRow = 3;
    private double horizontalGap = 15;

    public ImagePanelView(GridPane gridPane, ScrollPane rightScrollPane) {
        this.gridPane = gridPane;
        this.rightScrollPane = rightScrollPane;

        setupView();
    }

    public void setupView() {
        setGridPane();
    }

    private void setGridPane() {
        gridPane.prefWidthProperty().bind(rightScrollPane.widthProperty());
        gridPane.prefHeightProperty().bind(rightScrollPane.heightProperty());
        gridPane.setPadding(new Insets(0, 0, rightScrollPanePadding, 0));
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        adjustHorizontalGap();

        int verticalGap = 50;
        gridPane.setVgap(verticalGap);
    }

    private void adjustHorizontalGap() {
        rightScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double containerWidth = newVal.doubleValue() - (rightScrollPanePadding * 2);
            double totalGapWidth = containerWidth - (imageWidth * numberOfImagesPerRow);
            int numberOfGaps = numberOfImagesPerRow - 1 + 2;
            horizontalGap = totalGapWidth / numberOfGaps;
            gridPane.setHgap(horizontalGap);
        });
    }

    public void distributeImages(Movies movies) {
        int row = 0, col = 0;

        for(Movie movie : movies) {
            String moviePosterPath = movie.posterPath();
            Image image;
            if(moviePosterPath.equals("null")) {
                image = new Image(Objects.requireNonNull(getClass().getResource("/viewer/images/poster-unavailable.jpg")).toExternalForm());
            }
            else {
                image = new Image(imageBaseURL + imageSize + moviePosterPath);
            }
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(imageWidth);

            imageView.setOnMouseClicked(event -> handleClickOnImage(movie));

            gridPane.add(imageView, col, row);

            col++;
            if(col >= numberOfImagesPerRow) {
                col = 0;
                row++;
            }
        }
    }
}
