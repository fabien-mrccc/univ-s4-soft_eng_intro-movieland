package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import moviesapp.viewer.buttons.SelectSearchModeButtons;

import static moviesapp.App.minHeight;
import static moviesapp.App.minWidth;

public class LeftPanelView {

    private final AnchorPane mainAnchorPane;
    private final Pane leftPane;
    private final Button appTitleButton;

    public SelectSearchModeButtons selectSearchModeButtons;


    public LeftPanelView(AnchorPane mainAnchorPane, Pane leftPane, Button appTitleButton, Pane selectModePane, Button withTitleButton, Button withoutTitleButton) {
        this.mainAnchorPane = mainAnchorPane;
        this.leftPane = leftPane;
        this.appTitleButton = appTitleButton;

        selectSearchModeButtons = new SelectSearchModeButtons(selectModePane, withTitleButton, withoutTitleButton, leftPane, appTitleButton);

        setupView();
    }

    private void setupView() {
        setMainAnchorPane();
        setLeftPane();
        setAppTitleButton();
    }

    private void setMainAnchorPane(){
        mainAnchorPane.setMinWidth(minWidth);
        mainAnchorPane.setMinHeight(minHeight);
    }

    private void setLeftPane(){
        leftPane.prefWidthProperty().bind(mainAnchorPane.widthProperty().multiply(0.34));
        leftPane.prefHeightProperty().bind(mainAnchorPane.heightProperty());
    }

    private void setAppTitleButton(){
        appTitleButton.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(appTitleButton.widthProperty().divide(2)));
        appTitleButton.layoutYProperty().bind(leftPane.heightProperty().divide(2).subtract(appTitleButton.heightProperty().divide(2)));
        appTitleButton.layoutYProperty().bind(leftPane.layoutYProperty().add(10));

    }
}
