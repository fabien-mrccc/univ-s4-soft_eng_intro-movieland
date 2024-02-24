package moviesapp.model.fxml;

import java.util.ArrayList;
import java.util.List;

public class FxmlImports {

    private static final List<String> fxmlImports = new ArrayList<>();

    /**
     * Initializes the list of FXML imports with default JavaFX control imports.
     */
    private static void initFxmlImports(){
        fxmlImports.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        fxmlImports.add("<?import javafx.scene.control.Button?>");
        fxmlImports.add("<?import javafx.scene.control.ComboBox?>");
        fxmlImports.add("<?import javafx.scene.control.Label?>");
        fxmlImports.add("<?import javafx.scene.control.Slider?>");
        fxmlImports.add("<?import javafx.scene.control.TextField?>");
        fxmlImports.add("<?import javafx.scene.layout.AnchorPane?>");
        fxmlImports.add("<?import javafx.scene.layout.HBox?>");
        fxmlImports.add("<?import javafx.scene.layout.VBox?>");
        fxmlImports.add("<?import javafx.scene.shape.Line?>");
        fxmlImports.add("<?import javafx.scene.text.Font?>");
    }


    /**
     * Retrieves the FXML imports as a single string.
     *
     * @return A string containing all the FXML import statements separated by newline characters.
     */
    static String getFxmlImports(){
        initFxmlImports();
        StringBuilder stringBuilder = new StringBuilder();

        for(String fxmlImport : fxmlImports){
            stringBuilder.append(fxmlImport).append("\n");
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }
}
