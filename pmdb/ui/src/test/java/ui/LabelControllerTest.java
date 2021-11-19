package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import core.ILabel;
import core.IMovie;
import core.MovieList;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class LabelControllerTest extends ApplicationTest {

  private NodeFinderHelper nodeFinder;

  private Node waitForNode(String id) {
    return nodeFinder.waitForNode(id);
  }

  private void waitThenWrite(String text) {
    nodeFinder.waitThenWrite(text);
  }

  private MovieListController movieListController;

  private EditMovieController editMovieController;

  private final File testFile = new File(getClass().getResource("MovieList_test.json").getFile());

  private String title = "t";
  private String description = "";
  private String hours = "1";
  private String minutes = "1";
  private boolean watched = false;

  private String legalLabelTitle = "label";

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
    final Parent root = loader.load();
    AppController appController = loader.getController();
    movieListController = appController.movieListController;
    editMovieController = movieListController.editMovieController;
    stage.setScene(new Scene(root));
    stage.show();
  }

  private void enterMinMovieValues() {
    enterMovieValues(title, description, hours, minutes, watched);
  }

  private void enterMovieValues(String title, String description, String hours, String minutes,
      boolean watched) {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#titleField"));
    WaitForAsyncUtils.waitForFxEvents();
    waitThenWrite(title);
    clickOn(waitForNode("#descriptionField"));
    WaitForAsyncUtils.waitForFxEvents();
    waitThenWrite(description);
    clickOn(waitForNode("#hoursField"));
    WaitForAsyncUtils.waitForFxEvents();
    waitThenWrite(hours);
    clickOn(waitForNode("#minutesField"));
    WaitForAsyncUtils.waitForFxEvents();
    waitThenWrite(minutes);
    if (watched) {
      clickOn(waitForNode("#watchedCheckBox"));
    }
  }

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setup() {
    nodeFinder = new NodeFinderHelper();
    try {
      movieListController.loadMovieListFile(testFile);
      assertEquals(1, movieListController.getMovies().size(), "wrong number of movies loaded");
      ((TabPane) waitForNode("#tabPane")).getSelectionModel().selectLast();
      WaitForAsyncUtils.waitForFxEvents();
    } catch (Exception e) {
      fail(e);
    }
  }

  /**
   * Done after each test.
   */
  @AfterEach
  public void tearDown() throws IOException {
    try {
      WaitForAsyncUtils.waitForFxEvents();
      MovieStorage storage = new MovieStorage();
      storage.setFilePath(testFile);
      MovieList movieList = storage.loadMovieList();
      Collection<IMovie> deleteMovies = new ArrayList<IMovie>();
      for (Iterator<IMovie> movies = movieList.iterator(); movies.hasNext(); ) {
        IMovie movie = movies.next();
        if (!movie.getTitle().equals("test movie")) {
          deleteMovies.add(movie);
        }
      }
      deleteMovies.forEach(m -> movieList.removeMovie(m));
      storage.saveMovieList(movieList);
    } catch (Exception e) {
      e.printStackTrace();
      fail(e);
    }
  }

  @Test
  public void test_initialize() {
    assertNotNull(movieListController, "movieListController not loaded");
    assertNotNull(editMovieController, "reviewListController not loaded");
  }

  @Test
  public void testAddLabel_valid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMinMovieValues();
    clickOn(waitForNode("#labelComboBox"));
    waitThenWrite(legalLabelTitle);
    clickOn(waitForNode("#addLabel"));
    WaitForAsyncUtils.waitForFxEvents();

    VBox labelDisplay = (VBox) waitForNode("#labelDisplay");
    assertEquals(1, labelDisplay.getChildren().size(), "Label not added");
    assertEquals(legalLabelTitle,
        ((Label) labelDisplay.getChildren().get(0).lookup("#labelName")).getText(),
        "wrong labelTitle");

    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListController.getMovieList().getMovie(title).getLabelCount());
    Iterator<ILabel> labels = movieListController.getMovieList().getMovie(title).labelIterator();
    if (labels.hasNext()) {
      assertEquals(legalLabelTitle, labels.next().getTitle());
    } else {
      fail("Should be a label");
    }
  }

  @Test
  public void testAddLabel_invalid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));

    String tooShortLabel = "";
    clickOn(waitForNode("#labelComboBox"));
    waitThenWrite(tooShortLabel);
    clickOn(waitForNode("#addLabel"));
    WaitForAsyncUtils.waitForFxEvents();

    VBox labelDisplay = (VBox) waitForNode("#labelDisplay");
    assertEquals(0, labelDisplay.getChildren().size());

    String tooLongLabel = "thisIsOver15Characters";
    clickOn(waitForNode("#labelComboBox"));
    waitThenWrite(tooLongLabel);
    clickOn(waitForNode("#addLabel"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(0, labelDisplay.getChildren().size());
  }

  @Test
  public void testEditLabels() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMinMovieValues();
    clickOn(waitForNode("#labelComboBox"));
    waitThenWrite(legalLabelTitle);
    clickOn(waitForNode("#addLabel"));
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#M1").lookup("#editMovie"));
    WaitForAsyncUtils.waitForFxEvents();

    VBox labelDisplay = (VBox) waitForNode("#labelDisplay");
    assertEquals(1, labelDisplay.getChildren().size());

    clickOn((labelDisplay.getChildren().get(0)).lookup("#removeLabel"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(0, labelDisplay.getChildren().size());

    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(0, movieListController.getMovieList().getMovie(title).getLabelCount());
  }
}
