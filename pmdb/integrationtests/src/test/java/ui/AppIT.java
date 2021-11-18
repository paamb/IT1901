package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

/**
 * AppIntegrationTest class.
 * 
 */
public class AppIT extends ApplicationTest {

  private MovieListController movieListController;

  @BeforeAll
  public static void setupHeadless() {
    App.supportHeadless();
  }

  /**
   * Setup metod before test.
   * 
   */
  @BeforeEach
  public void setupMovieList() {
    try {
      String port = System.getProperty("pmdb.port");
      assertNotNull(port, "The port pmdb.port is null.");
      String baseUri = "http://localhost:" + port + "/movielist";
      this.movieListController.syncWithServer(baseUri);
      assertTrue(this.movieListController.serverIsRunning());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
    final Parent parent = fxmlLoader.load();
    AppController appController = fxmlLoader.getController();
    this.movieListController = appController.movieListController;
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  public void testController() {
    assertNotNull(this.movieListController);
  }
}
