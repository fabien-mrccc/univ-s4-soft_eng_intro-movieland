package moviesapp.model.fxml;

public class FxmlMainWindow {
    private static final double prefHeight = 400.0;
    private static final double prefWidth = 600.0;
    private static final String xmlns = "http://javafx.com/javafx/21";
    private static final String xmlnsFx = "http://javafx.com/fxml/1";
    private static final String controller = "moviesapp.controller.AppController";


    /**
     * Generates the opening code for the main window in FXML format.
     *
     * @return A string representing the opening code for the main window, including properties such as preferred height, preferred width, XML namespaces, and controller.
     */
    static String openMainWindowCode(){
        return  "<AnchorPane prefHeight=\"" + prefHeight + "\" " +
                " prefWidth=\"" + prefWidth + "\" " +
                "xmlns=\"" + xmlns + "\" " +
                "xmlns:fx=" + xmlnsFx + "\" " +
                "fx:controller=\"" + controller + "\"" +
                ">\n";
    }

    /**
     * Generates the closing code for the main window in FXML format.
     *
     * @return A string representing the closing code for the main window in FXML format.
     */
    static String closeMainWindowCode(){
        return "</AnchorPane>\n";
    }

}
