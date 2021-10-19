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

public class MovieListTest extends ApplicationTest {

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
    waitForThenClick(text).eraseText(text.getText().length());
  }

  private void sleep500ms() {
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private FxRobotInterface waitForThenClick(String id){
    int counter = 0;
    while(counter < 50){
      try {
        return clickOn(id);
      } catch (NoSuchElementException e) {
        System.out.println("NoSuchElement, trying again");
      }
      counter++;
      try {
        Thread.sleep(50);
      } catch (Exception e) {
        System.out.println("Could not sleep");
      }
    }
    return null;
  }

  private FxRobotInterface waitForThenClick(Node node){
    int counter = 0;
    while(counter < 50){
      try {
        return clickOn(node);
      } catch (NoSuchElementException e) {
        System.out.println("NoSuchElement, trying again");
      }
      counter++;
      try {
        Thread.sleep(50);
      } catch (Exception e) {
        System.out.println("Could not sleep");
      }
    }
    return null;
  }

  private void waitForNode(Node node) {
    // try {
    //   WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS, () -> {
    //     while (true) {
    //       if (node != null && node.isVisible()) {
    //         return true;
    //       }
    //       Thread.sleep(100);
    //     }
    //   });
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }
  }

  private void enterMovieValues(String title, String description, String hours, String minutes, boolean watched) {
    WaitForAsyncUtils.waitForFxEvents();
    sleep500ms();
    waitForNode(editMovieController.titleField);
    waitForThenClick("#titleField").write(title);
    waitForThenClick("#descriptionField").write(description);
    waitForThenClick("#hoursField").write(hours);
    waitForThenClick("#minutesField").write(minutes);
    if (watched) {
      waitForThenClick("#watchedCheckBox");
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
      System.out.println("before");
      WaitForAsyncUtils.waitForFxEvents();
      System.out.println("after");
      sleep500ms();
      sleep500ms();
      waitForThenClick("#movieListTab");
      sleep500ms();
      System.out.println(0);
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
    System.out.println(1);
    assertNotNull(movieListController);
    assertNotNull(editMovieController);
  }

  @Test
  public void testOpenEditMovie() {
    System.out.println(2);
    // waitForNode(movieListController.openEditMovie);
    waitForThenClick("#openEditMovie");
    WaitForAsyncUtils.waitForFxEvents();
    // waitForNode(movieListController.editMovieWindow);
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    waitForThenClick("#openEditMovie");
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
  }

  @Test
  public void testCloseEditMovie() {
    System.out.println(3);
    waitForThenClick("#openEditMovie");
    WaitForAsyncUtils.waitForFxEvents();
    // waitForNode(movieListController.editMovieWindow);
    assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    waitForThenClick("#cancelButton");
    assertFalse(movieListController.editMovieWindow.isVisible(), "EditMovie-window should not be visible.");
  }

  @Test
  public void testAddMovie_valid() {
    System.out.println(4);
    WaitForAsyncUtils.waitForFxEvents();
    // waitForNode(movieListController.editMovieWindow);
    waitForThenClick("#openEditMovie");
    enterMovieValues(title, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");

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
    System.out.println(5);
    WaitForAsyncUtils.waitForFxEvents();
    String title = "test movie";
    waitForThenClick("#openEditMovie");
    // waitForNode(movieListController.editMovieWindow);
    enterMovieValues(title, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");
    assertNotEquals("", editMovieController.errorField.getText());
    assertEquals(1, movieListSize());
    assertEquals(true, movieListController.editMovieWindow.isVisible());
  }

  @Test
  public void testAddMovie_invalidDuration() {
    System.out.println(6);
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick("#openEditMovie");
    String nonInteger = "1o";
    enterMovieValues(title, description, nonInteger, minutes, watched);
    waitForThenClick("#submitMovie");
    assertEquals(1, movieListSize());
    assertTrue(movieListController.editMovieWindow.isVisible());
    assertNotEquals("", editMovieController.errorField.getText());

    deleteInput(editMovieController.hoursField);
    String hoursOutOfRange = "30";
    waitForThenClick("#hoursField").write(hoursOutOfRange);
    waitForThenClick("#submitMovie");
    assertEquals(1, movieListSize());

    deleteInput(editMovieController.hoursField);
    write(hours);
    deleteInput(editMovieController.minutesField);
    String minutesOutOfRange = "-30";
    write(minutesOutOfRange);
    waitForThenClick("#submitMovie");
    assertEquals(1, movieListSize());
  }

  @Test
  public void testDeleteMovie() {
    System.out.println(7);
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick("#openEditMovie");
    enterMovieValues(title, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");
    waitForThenClick(movieListController.movieDisplay.lookup("#1").lookup("#deleteMovie"));
    assertEquals(1, movieListSize());
  }

  @Test
  public void testEditMovie_valid() {
    System.out.println(8);
    // waitForNode(movieListController.openEditMovie);
    waitForThenClick("#openEditMovie");
    // waitForNode(movieListController.editMovieWindow);
    enterMovieValues(title, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");
    waitForThenClick(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));

    String newTitle = "new title";
    deleteInput(editMovieController.titleField);
    write(newTitle);
    waitForThenClick("#submitMovie");
    assertFalse(movieListController.editMovieWindow.isVisible());
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    assertEquals(2, movieListSize());

    waitForThenClick(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));
    waitForThenClick("#submitMovie");
    assertFalse(movieListController.editMovieWindow.isVisible());
    assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    assertEquals(2, movieListSize());
  }

  @Test
  public void testEditMovie_titleInUse() {
    System.out.println(9);
    // waitForNode(movieListController.openEditMovie);
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick("#openEditMovie");
    // waitForNode(movieListController.editMovieWindow);
    
    enterMovieValues(title, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");

    waitForThenClick(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));
    deleteInput(editMovieController.titleField);
    String invalidTitle = "test movie";
    write(invalidTitle);
    waitForThenClick("#submitMovie");
    assertTrue(movieListController.editMovieWindow.isVisible());
    assertEquals(title, movieListController.getMovieList().getMovie(title).getTitle());
    assertEquals(2, movieListSize());
  }

  @Test
  public void testSortMovies() {
    System.out.println(10);
    // waitForNode(movieListController.openEditMovie);
    waitForThenClick("#openEditMovie");
    String title2 = "aaa";
    sleep500ms();
    enterMovieValues(title2, description, hours, minutes, watched);
    waitForThenClick("#submitMovie");
    String title1 = "test movie";
    assertEquals(title1, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());

    waitForThenClick("#sortOnTitleCheckbox");
    assertEquals(title2, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());

    waitForThenClick("#sortOnSeenCheckbox");
    assertEquals(title2, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());
  }
}
