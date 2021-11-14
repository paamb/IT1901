package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import core.IMovie;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppControllerTest extends ApplicationTest {

  private MovieListController movieListController;

  private ReviewListController reviewListController;

  private final File testFile = new File(getClass().getResource("MovieList_test.json").getFile());

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
    final Parent root = loader.load();
    AppController appController = loader.getController();
    movieListController = appController.movieListController;
    reviewListController = appController.reviewListController;
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setup() {
    try {
      movieListController.loadMovieListFile(testFile);
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void testAppController_initial() {
    assertNotNull(movieListController, "movieListController not loaded");
    assertNotNull(reviewListController, "reviewListController not loaded");
  }

  @Test
  public void testMovieList_initial() {
    assertEquals(1, movieListController.getMovies().size(), "Wrong number of loaded movies");
    IMovie movie = movieListController.getMovies().stream().findFirst().get();
    assertEquals("test movie", movie.getTitle(), "Wrong title loaded.");
  }
}
