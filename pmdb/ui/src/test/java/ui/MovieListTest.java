package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.IMovie;
import core.MovieList;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class MovieListTest extends AbstractNodeFinderTest {

  @FXML
  Tab movieListTab;

  @FXML
  Tab reviewListTab;

  private MovieListController movieListController;

  private EditMovieController editMovieController;

  private final File testFile = new File("src/test/resources/ui/MovieList_test.json");

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
    WaitForAsyncUtils.waitForFxEvents();
  }

  private void deleteInput(TextField text) {
    clickOn(text).eraseText(text.getText().length());
  }

  

  private void enterMovieValues(String title, String description, String hours, String minutes, boolean watched) {
    clickOn(waitForNode("#titleField")).write(title);
    clickOn(waitForNode("#descriptionField")).write(description);
    clickOn(waitForNode("#hoursField")).write(hours);
    clickOn(waitForNode("#minutesField")).write(minutes);
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
    try {
      movieListController.loadMovieListFile(testFile);
      assertEquals(1, movieListSize());
      WaitForAsyncUtils.waitForFxEvents();
      clickOn(waitForNode("#movieListTab"));
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
      storage.setFile(testFile);
      MovieList movieList = storage.loadMovieList();
      movieList.getMovies().stream().forEach(movie -> {
        if (!movie.getTitle().equals("test movie")) {
          movieList.removeMovie(movie);
        }
      });
      storage.saveMovieList(movieList);
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void test_initialize() {
    assertNotNull(movieListController);
    assertNotNull(editMovieController);
  }

  @Test
  public void testOpenEditMovie() {
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    waitForNode("#movieDisplay");
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    clickOn(waitForNode("#openEditMovie"));
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
  }

  @Test
  public void testCloseEditMovie() {
    clickOn(waitForNode("#openEditMovie"));
    waitForNode("#editMovieWindow");
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    clickOn(waitForNode("#cancelButton"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible(), "EditMovie-window should not be visible.");
  }

  @Test
  public void testAddMovie_valid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(2, movieListController.getMovieList().getMovies().size());
    assertEquals(false, movieListController.editMovieWindow.isVisible());

    IMovie movie = movieListController.getMovieList().getMovie(title);

    assertEquals(title, movie.getTitle());
    assertEquals(description, movie.getDescription());
    assertEquals(hours, String.valueOf(movie.getDuration().getHour()));
    assertEquals(minutes, String.valueOf(movie.getDuration().getMinute()));
    assertEquals(watched, movie.isWatched());
  }

  @Test
  public void testAddMovie_titleInUse() {
    WaitForAsyncUtils.waitForFxEvents();
    String title = "test movie";
    clickOn(waitForNode("#openEditMovie"));
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertNotEquals("", editMovieController.errorField.getText());
    assertEquals(1, movieListSize());
    assertEquals(true, movieListController.editMovieWindow.isVisible());
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
    assertEquals(1, movieListSize());
    assertTrue(movieListController.editMovieWindow.isVisible());
    assertNotEquals("", editMovieController.errorField.getText());

    //deleteInput(editMovieController.hoursField);
    String hoursOutOfRange = "30";
    clickOn(waitForNode("#hoursField")).eraseText(nonInteger.length()).write(hoursOutOfRange);
    clickOn(waitForNode("#submitMovie"));
    assertEquals(1, movieListSize());

    //deleteInput(editMovieController.hoursField);
    clickOn(waitForNode("#hoursField")).eraseText(hoursOutOfRange.length()).write(hours);
    //deleteInput(editMovieController.minutesField);
    String minutesOutOfRange = "-30";
    clickOn(waitForNode("#minutesField")).eraseText(minutes.length()).write(minutesOutOfRange);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListSize());
  }

  @Test
  public void testDeleteMovie() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    clickOn(waitForNode("#1").lookup("#deleteMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, movieListSize());
  }

  @Test
  public void testEditMovie_valid() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    clickOn(waitForNode("#1").lookup("#editMovie"));

    String newTitle = "new title";
    //deleteInput(editMovieController.titleField);
    
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#titleField")).eraseText(newTitle.length()).write(newTitle);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible());
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    assertEquals(2, movieListSize());

    clickOn(waitForNode("#1").lookup("#editMovie"));
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(movieListController.editMovieWindow.isVisible());
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    assertEquals(2, movieListSize());
  }

  @Test
  public void testEditMovie_titleInUse() {
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#openEditMovie"));
    
    enterMovieValues(title, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));

    clickOn(waitForNode("#1").lookup("#editMovie"));
    //deleteInput(editMovieController.titleField);
    WaitForAsyncUtils.waitForFxEvents();
    String invalidTitle = "test movie";
    clickOn(waitForNode("#titleField")).eraseText(title.length()).write(invalidTitle);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    assertTrue(movieListController.editMovieWindow.isVisible());
    assertEquals(title, movieListController.getMovieList().getMovie(title).getTitle());
    assertEquals(2, movieListSize());
  }

  @Test
  public void testSortMovies() {
    clickOn(waitForNode("#openEditMovie"));
    String title2 = "aaa";
    enterMovieValues(title2, description, hours, minutes, watched);
    clickOn(waitForNode("#submitMovie"));
    WaitForAsyncUtils.waitForFxEvents();
    String title1 = "test movie";
    assertEquals(title1, ((Label) waitForNode("#0").lookup("#movieTitle")).getText());

    clickOn(waitForNode("#sortOnTitleCheckbox"));
    assertEquals(title2, ((Label) waitForNode("#0").lookup("#movieTitle")).getText());

    clickOn(waitForNode("#sortOnSeenCheckbox"));
    assertEquals(title2, ((Label) waitForNode("#0").lookup("#movieTitle")).getText());
  }
}
