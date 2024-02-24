package moviesapp.viewer;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class OptionButtonTest {

    private volatile boolean uiInitialized = false;

    @Start
    private void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OptionButtonGUI.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        // Mark UI as initialized once it's shown
        uiInitialized = true;
    }

    @Test
    public void testOpenClearConfirmationWindow(FxRobot robot) throws InterruptedException {
        // Wait for the UI to be initialized
        waitForUIInitialization();

        // Interact with UI elements on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Simulate clicking the clear button to open the confirmation window
            robot.clickOn("#clearButton");
        });

        // Wait for the confirmation window to become visible
        verifyThat("#clearConfirmationAnchorPane", isVisible());
        verifyThat("#confirmation", hasText("Do you want to continue?"));
    }

    // Utility method to wait for UI initialization
    private void waitForUIInitialization() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        while (!uiInitialized) {
            latch.await(100, TimeUnit.MILLISECONDS); // Sleep for a short duration
        }
    }
}