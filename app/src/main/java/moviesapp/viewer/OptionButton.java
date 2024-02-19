package moviesapp.viewer;
import javafx.scene.control.Button;

public class OptionButton {
    private static final Button goButton = new Button("Go");
    private static final Button favoritesButton = new Button("Favorites");
    public void activateButton(Button button){
        if(button.isPressed()){
            switch(button.getText()){
                case "Go":
                    //TODO: call the methode that prints all the movies resulting from the user research;
                    break;

                case "Favorites":
                    //TODO: call the methode that prints all the movies from the favorites list;
                    break;

            }
        }
    }

    public static Button getGoButton(){
        return goButton;
    }
    public static Button getFavorites(){
        return favoritesButton;
    }
}
