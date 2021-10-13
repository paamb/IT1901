package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppTest extends ApplicationTest {

    private AppController appController;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App_test.fxml"));
        final Parent root = loader.load();
        appController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testAppController_initial() {
        assertNotNull(appController);
    }
}
