package moviesapp.viewer.buttons;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class WithoutTitleButtons {

    private final Pane buttonsPane;
    private final Pane leftPane;
    private final Pane ratingPane;
    private final Button favoritesButton;
    private final Button goButton;

    public WithoutTitleButtons(Pane buttonsPane, Pane leftPane, Pane ratingPane, Button favoritesButton, Button goButton){
        this.buttonsPane = buttonsPane;
        this.leftPane = leftPane;
        this.ratingPane = ratingPane;
        this.favoritesButton = favoritesButton;
        this.goButton = goButton;

        setView();
    }

    private void setView(){
        setButtonsPane();
        setFavoritesButton();
        setGoButton();
    }

    private void setButtonsPane(){
        buttonsPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(buttonsPane.widthProperty().divide(2)));
        buttonsPane.layoutYProperty().bind(ratingPane.layoutYProperty().add(110));
    }

    private void setFavoritesButton(){
        favoritesButton.setPrefHeight(26);
        favoritesButton.setPrefWidth(140);
    }

    private void setGoButton(){
        goButton.layoutXProperty().bind(favoritesButton.layoutXProperty().add(160));
        goButton.setPrefHeight(26);
        goButton.setPrefWidth(80);
    }
}
