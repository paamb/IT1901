package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.IMovie;
import core.IReview;
import core.MovieList;

import java.io.File;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import json.moviepersistance.MovieStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotInterface;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class ReviewListTest extends ApplicationTest {

  private MovieListController movieListController;

  private ReviewListController reviewListController;

  private EditReviewController editReviewController;

  private final File testFile = new File("src/test/resources/ui/MovieList_test.json");

  private String comment = "comment";
  private LocalDate whenWatched = LocalDate.of(2021, 10, 10);

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
    final Parent root = loader.load();
    AppController appController = loader.getController();
    movieListController = appController.movieListController;
    reviewListController = appController.reviewListController;
    editReviewController = reviewListController.editReviewController;
    stage.setScene(new Scene(root));
    stage.show();
  }

  private void deleteInput(TextArea text) {
    waitForThenClick(text).eraseText(text.getText().length());
  }

  private int reviewListSize() {
    int counter = 0;
    for (IMovie movie : movieListController.getMovies()) {
      counter += movie.getReviews().size();
    }
    return counter;
  }

  private FxRobotInterface waitForThenWrite(String text){
    int counter = 0;
    while(counter < 50){
      try {
        return write(text);
      } catch (Exception e) {
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

  private FxRobotInterface waitForThenClick(String id){
    int counter = 0;
    while(counter < 50){
      try {
        return clickOn(id);
      } catch (Exception e) {
        System.out.println("NoSuchElement, trying again");
      }
      counter++;
      try {
        Thread.sleep(100);
      } catch (Exception e) {
        System.out.println("Could not sleep");
      }
    }
    return null;
  }

  private FxRobotInterface waitForThenClick(Node node){
    int counter = 0;
    while(counter < 100){
      try {
        return clickOn(node);
      } catch (Exception e) {
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
    try {
      WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS, () -> {
        while (true) {
          if (node != null && node.isVisible()) {
            return true;
          }
          Thread.sleep(100);
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      Thread.sleep(500);
    } catch (Exception e) {
      //TODO: handle exception
    }
  }

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setup() {
    try {
      movieListController.loadMovieListFile(testFile);
      WaitForAsyncUtils.waitForFxEvents();
    } catch (Exception e) {
      fail(e);
    }
    assertEquals(0, reviewListSize());
  }

  /**
   * Done after each test.
   */
  @AfterEach
  public void tearDown() {
    try {
      MovieStorage storage = new MovieStorage();
      storage.setFile(testFile);
      MovieList movieList = storage.loadMovieList();
      movieList.getMovies().stream().forEach(movie -> {
        if (!movie.getTitle().equals("test movie")) {
          movieList.removeMovie(movie);
        }
      });
      IMovie movie = movieList.getMovie("test movie");
      movie.getReviews().stream().forEach(review -> {
        movie.removeReview(review);
      });
      storage.saveMovieList(movieList);
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void test_initialize() {
    assertNotNull(reviewListController);
    assertNotNull(editReviewController);
    assertNotNull(movieListController);
  }

  @Test
  public void addReview_valid() {
    waitForThenClick("#openEditReview");
    WaitForAsyncUtils.waitForFxEvents();
    waitForNode(editReviewController.commentField);
    waitForThenClick("#commentField");
    waitForThenWrite(comment);
    waitForThenClick("#dateField");
    editReviewController.dateField.setValue(whenWatched);
    waitForThenClick("#submitReview");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, reviewListSize());
    assertFalse(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void addReview_invalidDate() {
    LocalDate invalidDate = LocalDate.now().plusDays(30);
    waitForThenClick("#openEditReview");
    waitForThenClick("#dateField");
    editReviewController.dateField.setValue(invalidDate);
    waitForThenClick("#submitReview");
    waitForNode(reviewListController.editReviewWindow);
    assertEquals(0, reviewListSize());
    assertTrue(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void editReview() {
    waitForThenClick("#openEditReview");
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick("#commentField");
    waitForThenWrite(comment);
    waitForThenClick("#dateField");
    editReviewController.dateField.setValue(whenWatched);
    waitForThenClick("#submitReview");
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals(1, reviewListSize());

    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick(reviewListController.reviewDisplay.lookup("#0").lookup("#editReview"));
    deleteInput(editReviewController.commentField);
    String newComment = "new comment";
    waitForThenWrite(newComment);
    waitForThenClick("#submitReview");

    assertEquals(1, reviewListSize());
    IReview review = movieListController.getMovieList().getMovie("test movie").getReviews().stream().findFirst().get();
    assertEquals(newComment, review.getComment());
  }

  @Test
  public void deleteReview() {
    waitForNode(movieListController.openEditMovie);
    waitForThenClick("#openEditReview");
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenClick(editReviewController.commentField);
    WaitForAsyncUtils.waitForFxEvents();
    waitForThenWrite(comment);
    waitForThenClick(editReviewController.dateField);
    editReviewController.dateField.setValue(whenWatched);
    waitForThenClick(editReviewController.submitReview);
    assertEquals(1, reviewListSize());

    waitForThenClick(reviewListController.reviewDisplay.lookup("#0").lookup("#deleteReview"));
    assertEquals(0, reviewListSize());
  }
}
