package moviesapp.viewer;
import javafx.scene.control.Button;

public class OptionButton {
    private static final Button goButton = new Button("Go");

    public void activateButton(Button button){
        if(button.isPressed()){
            switch(button.getText()){
                case "Go":
                    //TODO: call the methode that print all the movies resulting from the user research;
                    break;

            }
        }
    }

    public static Button getGoButton(){
        return goButton;
    }
}
