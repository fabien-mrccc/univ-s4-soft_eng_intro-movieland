package moviesapp.viewer.left_panel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class LeftPanelView {

    private final AnchorPane mainAnchorPane;
    private final Pane leftPane;
    private final Label appTitle;
    private final Pane selectModePane;
    private final Button withTile;
    private final Button withoutTile;

    public LeftPanelView(AnchorPane mainAnchorPane, Pane leftPane, Label appTitle, Pane selectModePane, Button withTile, Button withoutTile) {
        this.mainAnchorPane = mainAnchorPane;
        this.leftPane = leftPane;
        this.appTitle = appTitle;
        this.selectModePane = selectModePane;
        this.withTile = withTile;
        this.withoutTile = withoutTile;

        setupView();
    }

    private void setupView() {
        setLeftPane();
        setAppTitle();
        setSelectModePane();
        setWithTile();
        setWithoutTile();
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
}
