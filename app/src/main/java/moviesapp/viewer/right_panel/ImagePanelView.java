package moviesapp.viewer.right_panel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import static moviesapp.viewer.right_panel.RightPanelView.rightScrollPanePadding;

public class ImagePanelView {
    private final ScrollPane rightScrollPane;
    private final GridPane gridPane;
    private final int numberOfImages;
    private final int imageWidth = 258;
    private int numberOfImagesPerRow = 3;
    private double horizontalGap = 15;
    private final int verticalGap = 50;

    public ImagePanelView(GridPane gridPane, ScrollPane rightScrollPane, int numberOfImages) {
        this.gridPane = gridPane;
        this.rightScrollPane = rightScrollPane;
        this.numberOfImages = numberOfImages;

        setupView();
    }

    public void setupView() {
        setGridPane();
        distributeImages();
    }

    private void setGridPane() {
        gridPane.prefWidthProperty().bind(rightScrollPane.widthProperty());
        gridPane.prefHeightProperty().bind(rightScrollPane.heightProperty());
        gridPane.setPadding(new Insets(0, 0, rightScrollPanePadding, 0));
        gridPane.setAlignment(Pos.BASELINE_CENTER);
        adjustHorizontalGap();
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


    private void distributeImages() {
        Image image = new Image("https://image.tmdb.org/t/p/w300/ldfCF9RhR40mppkzmftxapaHeTo.jpg");
        int row = 0, col = 0;

        for(int i = 0; i < numberOfImages; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(imageWidth);

            gridPane.add(imageView, col, row);

            col++;
            if(col >= numberOfImagesPerRow) {
                col = 0;
                row++;
            }
        }
    }
}
