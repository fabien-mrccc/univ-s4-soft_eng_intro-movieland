package moviesapp.viewer;
import javafx.scene.control.Button;

public class OptionButton {
    private static final Button goButton = new Button("Go");
    private static final Button favoritesButton = new Button("Favorites");
    private static final Button addToFavoritesButton = new Button("add");
    private static final Button removeFromFavoritesButton = new Button("remove");
    private static final Button clearButton = new Button("clear");
    public void activateButton(Button button){
        if(button.isPressed()){
            switch(button.getText()){
                case "Go":
                    //TODO: call the methode that prints all the movies resulting from the user research;
                    break;

                case "Favorites":
                    //TODO: call the methode that prints all the movies from the favorites list;
                    break;

                case "add":
                    //TODO: call the methode that adds a film the favorites;
                    break;

                case "remove":
                    //TODO: call the methode that remove a film from favorites;
                    break;

                case "clear":
                    //clearConfirm();
                    break;
            }
        }
    }

    public static Button getGoButton(){
        return goButton;
    }
    public static Button getFavoritesButton(){
        return favoritesButton;
    }
    public static Button getAddToFavoritesButton(){
        return addToFavoritesButton;
    }
    public static Button getRemoveFromFavoritesButton(){
        return removeFromFavoritesButton;
    }
    public static Button getClearButton(){
        return clearButton;
    }

}
