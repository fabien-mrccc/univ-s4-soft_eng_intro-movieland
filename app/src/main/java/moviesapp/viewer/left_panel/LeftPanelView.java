package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;

public class LeftPanelView {

    private final AnchorPane mainAnchorPane;
    private final Pane leftPane;
    private final Label appTitle;


    public LeftPanelView(AnchorPane mainAnchorPane, Pane leftPane, Label appTitle) {
        this.mainAnchorPane = mainAnchorPane;
        this.leftPane = leftPane;
        this.appTitle = appTitle;

        setupView();
    }

    private void setupView() {
        setMainAnchorPane();
        setLeftPane();
        setAppTitle();
    }

    private void setMainAnchorPane(){
        mainAnchorPane.setMinWidth(minWidth);
        mainAnchorPane.setMinHeight(minHeight);
    }

    private void setLeftPane(){
        leftPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().multiply(0.34));
        leftPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setAppTitle(){
        appTitle.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(appTitle.widthProperty().divide(2)));
        appTitle.layoutYProperty().bind(leftPane.heightProperty().divide(2).subtract(appTitle.heightProperty().divide(2)));
        appTitle.layoutYProperty().bind(leftPane.layoutYProperty().add(10));
    }
}
