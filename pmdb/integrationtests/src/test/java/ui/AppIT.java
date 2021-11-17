package ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * AppIntegrationTest class.
 * 
 */
public class AppIT extends ApplicationTest {

  private AppController controller;
  private MovieListController movieListController;

  @BeforeAll
  public static void setupHeadless() {
    App.supportHeadless();
  }

  @BeforeEach
  public void setupMoiveList() throws URISyntaxException {
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("MovieListIT.json"))) {
      String port = System.getProperty("movie.port"); // TODO:
      assertNotNull(port, "The port moive.port is null.");
      // URI baseUri = new URI("http://localhost:" + port + "/movielist/"); // TODO:
      String baseUri = "http://localhost:" + port + "/movielist/";
      System.out.println("Base RemoteAcces URI: " + new URI(baseUri));

      this.movieListController.setBaseUri(baseUri);
      this.controller.syncWithServer();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppIT.fxml"));
    final Parent parent = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  @Test
  public void testController() {
    assertNotNull(this.controller);
  }
}
