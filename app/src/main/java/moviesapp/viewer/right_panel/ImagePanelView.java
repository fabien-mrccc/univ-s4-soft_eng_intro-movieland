package moviesapp.viewer.right_panel;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ImagePanelView {
    private final Pane imagePane;
    private final ScrollPane rightScrollPane;
    private final VBox vBox;
    private int numberOfImagesPerRow = 0;
    private double totalImageWidth = 0;
    private final int numberOfImages = 19; //TODO: update with real number of images;
    private int numberOfUnprintedImages = numberOfImages;
    static final int rightScrollPanePadding = 50;

    public ImagePanelView(Pane imagePane, ScrollPane rightScrollPane, VBox vBox) {
        this.imagePane = imagePane;
        this.rightScrollPane = rightScrollPane;
        this.vBox = vBox;

        setupView();
    }

    public void setupView() {
        setImagePane();
        setVBox();
        setHBoxes();
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
        int imageWidth = 230;

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
}
