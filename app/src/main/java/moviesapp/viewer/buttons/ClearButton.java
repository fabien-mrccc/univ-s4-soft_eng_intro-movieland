package moviesapp.viewer.buttons;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import moviesapp.model.movies.Favorites;

public class ClearButton {
    @FXML public Button clearButton;
    @FXML private Stage globalStage;
    @FXML private Button continueButton;
    @FXML private Button cancelButton;
    @FXML private Label confirmation;
    private final Pane leftPane;
    private final Pane withTitlePane;
    private final Pane clearPane;

    public ClearButton(Pane clearPane, Button clearButton, Pane leftPane, Pane withTitlePane){
        this.clearButton = clearButton;
        this.leftPane = leftPane;
        this.withTitlePane = withTitlePane;
        this.clearPane = clearPane;

        setView();
    }

    private void setView(){
        setClearPane();
        setClearButton();
    }

    private void setClearPane(){
        clearPane.layoutXProperty().bind(leftPane.widthProperty().divide(2).subtract(clearButton.widthProperty().divide(2)));
        clearPane.layoutYProperty().bind(withTitlePane.heightProperty().add(40));
    }

    private void setClearButton(){
        clearButton.setOnAction(event -> openClearConfirmationWindow());
        clearButton.setTextAlignment(TextAlignment.CENTER);
        clearButton.setPrefWidth(200);
        clearButton.setPrefHeight(26);
    }

    /**
     * Create a new window in which a confirmation is required to clear the list of favorites
     */
    @FXML
    public void openClearConfirmationWindow() {
        globalStage = new Stage();
        AnchorPane clearConfirmationAnchorPane = new AnchorPane();
        clearConfirmationAnchorPane.prefHeight(100);
        clearConfirmationAnchorPane.prefWidth(230);
        clearConfirmationAnchorPane.setId("clearConfirmationAnchorPane");

        initConfirmation();
        initContinueButton();
        initCancelButton();

        clearConfirmationAnchorPane.getChildren().add(confirmation);
        clearConfirmationAnchorPane.setStyle("-fx-background-color: #3D3D3D");
        clearConfirmationAnchorPane.getChildren().add(continueButton);
        clearConfirmationAnchorPane.getChildren().add(cancelButton);
        clearConfirmationAnchorPane.setVisible(true);
        Scene globalScene = new Scene(clearConfirmationAnchorPane, 230, 100);
        globalStage.setTitle("Confirmation");
        globalStage.setScene(globalScene);
        globalStage.show();
    }

    /**
     * initialise the button "continue"
     */
    @FXML
    private void initContinueButton(){
        continueButton = new Button("continue");
        continueButton.setLayoutX(20);
        continueButton.setLayoutY(60);
        continueButton.setPrefWidth(70);
        continueButton.setId("continueButton");
        continueButton.setTextFill(Paint.valueOf("white"));
        continueButton.setStyle("-fx-background-color: #E50914;");
        continueButton.setOnAction(event -> continueButtonClicked());
    }

    /**
     * initialise the button "cancel"
     */
    @FXML
    private void initCancelButton(){
        cancelButton = new Button("cancel");
        cancelButton.setLayoutX(140);
        cancelButton.setLayoutY(60);
        cancelButton.setPrefWidth(70);
        cancelButton.setId("cancelButton");
        cancelButton.setTextFill(Paint.valueOf("white"));
        cancelButton.setStyle("-fx-background-color: #E50914;");
        cancelButton.setOnAction(event -> closeClearConfirmationWindow());
    }

    /**
     * initialise the confirmation label
     */
    @FXML
    private void initConfirmation(){
        confirmation = new Label("Do you want to continue?");
        confirmation.setLayoutX(20);
        confirmation.setLayoutY(15);
        confirmation.setStyle("confirmation");
        confirmation.setTextFill(Paint.valueOf("white"));
        confirmation.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 17px;");
    }

    /**
     * close the confirmation window when the user click on "cancel"
     */
    @FXML
    public void closeClearConfirmationWindow(){
        globalStage.close();
    }

    @FXML
    public void continueButtonClicked(){
        Favorites.instance.clear();
        // TODO: add Numa's methode to update the user interface
        closeClearConfirmationWindow();
    }

    @FXML
    public Button getContinueButton(){
        return continueButton;
    }
}
