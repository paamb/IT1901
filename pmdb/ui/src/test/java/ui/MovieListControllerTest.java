package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.IMovie;
import core.MovieList;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class MovieListControllerTest extends ApplicationTest {

  // Need instance of NodeFinderHelper because NodeFinderHelper cannot have static methods
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

  private String title = "title";
  private String description = "description";
  private String hours = "2";
  private String minutes = "30";
  private boolean watched = false;

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

  private int movieListSize() {
    return movieListController.getMovies().size();
  }

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setup() {
    nodeFinder = new NodeFinderHelper();
    try {
      movieListController.loadMovieListFile(testFile);
      assertEquals(1, movieListSize(), "wrong number of movies loaded");
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
      fail(e);
    }
  }

  @Test
  public void test_initialize() {
    assertNotNull(movieListController, "movieListController not loaded");
    assertNotNull(editMovieController, "reviewListController not loaded");
  }

  @Test
  public void testOpenEditMovie() {
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    waitForNode("#movieDisplay");
    assertTrue(movieListController.editMovieWindow.isVisible(),
        "EditMovie-window should be visible.");
    clickOn(waitForNode("#openEditMovie"));
    assertTrue(movieListController.editMovieWindow.isVisible(),
        "EditMovie-window should be visible.");
  }

  @Test
  public void testCloseEditMovie() {
    clickOn(waitForNode("#openEditMovie"));
    waitForNode("#editMovieWindow");
    WaitForAsyncUtils.waitForFxEvents();

    clickOn(waitForNode("#cancelButton"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible(),
        "EditMovie-window should not be visible.");
  }

  @Test
  public void testAddMovie_valid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(2, movieListController.getMovieList().getMovieCount(),
        "wrong number of movies");
    assertEquals(false, movieListController.editMovieWindow.isVisible(),
        "editMovieWindow should not be visible");

    IMovie movie = movieListController.getMovieList().getMovie(title);

    assertEquals(title, movie.getTitle(), "wrong movieTitle saved");
    assertEquals(description, movie.getDescription(), "wrong description saved");
    Duration totalMinutes = Duration.ofMinutes(movie.getDuration());
    int inputHours = (int) totalMinutes.toHours();
    int inputMinutes = totalMinutes.toMinutesPart();
    assertEquals(hours, String.valueOf(inputHours), "wrong hours saved");
    assertEquals(minutes, String.valueOf(inputMinutes), "wrong minutes saved");
    assertEquals(watched, movie.isWatched(), "wrong 'watched' saved");
  }

  @Test
  public void testAddMovie_titleInUse() {
    String title = "test movie";
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, movieListSize(), "wrong number of movies");
  }

  @Test
  public void testAddMovie_invalidDuration() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    String nonInteger = "1o";
    enterMovieValues(title, description, nonInteger, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListSize(), "wrong number of movies");

    clickOn(waitForNode("#hoursField")).eraseText(nonInteger.length());
    waitThenWrite(hours);
    String minutesOutOfRange = "-30";
    clickOn(waitForNode("#minutesField")).eraseText(minutes.length());
    waitThenWrite(minutesOutOfRange);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListSize(), "wrong number of movies");
  }

  @Test
  public void testDeleteMovie() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#M1").lookup("#deleteMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListSize(), "wrong number of movies");
  }

  @Test
  public void testEditMovie_valid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    clickOn(waitForNode("#M1").lookup("#editMovie"));

    String newTitle = "new title";

    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#titleField")).eraseText(newTitle.length());
    waitThenWrite(newTitle);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible(),
        "editMovieWindow should not be visible");
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle(),
        "wrong title saved");
    assertEquals(2, movieListSize(), "wrong number of movies");

    clickOn(waitForNode("#M1").lookup("#editMovie"));
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible(),
        "editMovieWindow should not be visible");
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle(),
        "wrong title saved");
    assertEquals(2, movieListSize(), "wrong number of movies");
  }

  @Test
  public void testSortMovies() {
    clickOn(waitForNode("#openEditMovie"));
    String title2 = "aaa";
    enterMovieValues(title2, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    String title1 = "test movie";
    assertEquals(title1, ((Label) waitForNode("#M0").lookup("#movieTitle")).getText(),
        "not sorted correctly");

    clickOn(waitForNode("#sortOnTitleCheckbox"));
    assertEquals(title2, ((Label) waitForNode("#M0").lookup("#movieTitle")).getText(),
        "not sorted correctly");

    clickOn(waitForNode("#sortOnSeenCheckbox"));
    assertEquals(title2, ((Label) waitForNode("#M0").lookup("#movieTitle")).getText(),
        "not sorted correctly");
  }
}
