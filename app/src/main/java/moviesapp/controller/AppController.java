package moviesapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

        @FXML private Button clearButton;
        @FXML private Scene clearConfirmScene;
        @FXML private Stage clearConfirmStage;

        @Override
        public void initialize(URL location, ResourceBundle resourceBundle) {}

    /**
     * Create a new window in which a confirmation is required to clear the list of favorites
     */
    @FXML
    public void openClearConfirmationWindow() {
        Stage clearConfirmationStage = new Stage();
        AnchorPane clearConfirmationAnchorPane = new AnchorPane();
        clearConfirmationAnchorPane.prefHeight(100);
        clearConfirmationAnchorPane.prefWidth(230);
        Label confirmation = new Label("Do you want to continue?");
        confirmation.setLayoutX(45);
        confirmation.setLayoutY(10);
        Button continueButton = new Button("continue");
        continueButton.setLayoutX(20);
        continueButton.setLayoutY(60);
        continueButton.setPrefWidth(70);
        Button cancelButton = new Button("cancel");
        cancelButton.setLayoutX(140);
        cancelButton.setLayoutY(60);
        cancelButton.setPrefWidth(70);
        clearConfirmationAnchorPane.getChildren().add(confirmation);
        clearConfirmationAnchorPane.getChildren().add(continueButton);
        clearConfirmationAnchorPane.getChildren().add(cancelButton);
        clearConfirmationAnchorPane.setVisible(true);
        Scene clearConfirmationScene = new Scene(clearConfirmationAnchorPane, 230, 100);
        clearConfirmationStage.setTitle("Confirmation Request");
        clearConfirmationStage.setScene(clearConfirmationScene);
        clearConfirmationStage.show();
    }
    /* to add to favorites fxml
    <VBox xmlns:fx="http://javafx.com/fxml/1">
      <Button fx:id="clearButton" text="clear" onAction="#openClearConfirmationWindow"/>
      <AnchorPane fx:id="clearConfirmationAnchorPane"   xmlns="http://javafx.com/javafx" xmlns:fx="http://javafx.com/fxml" prefHeight="200.0" prefWidth="300.0">
      </AnchorPane>
   </VBox>
     */
}


