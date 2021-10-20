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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import json.moviepersistance.MovieStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

public class ReviewListTest extends AbstractNodeFinderTest {

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

  private int reviewListSize() {
    int counter = 0;
    for (IMovie movie : movieListController.getMovies()) {
      counter += movie.getReviews().size();
    }
    return counter;
  }

  private void addDummyReview() {
    clickOn(waitForNode("#openEditReview"));
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#commentField")).write(comment);
    clickOn(waitForNode("#dateField"));
    ((DatePicker) waitForNode("#dateField")).setValue(whenWatched);
    clickOn(waitForNode("#submitReview"));
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
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(reviewListController);
    assertNotNull(editReviewController);
    assertNotNull(movieListController);
  }

  @Test
  public void addReview_valid() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize());
    assertFalse(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void addReview_invalidDate() {
    LocalDate invalidDate = LocalDate.now().plusDays(30);
    clickOn(waitForNode("#openEditReview"));
    clickOn(waitForNode("#dateField"));
    ((DatePicker) waitForNode("#dateField")).setValue(invalidDate);
    clickOn(waitForNode("#submitReview"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(0, reviewListSize());
    assertTrue(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void editReview() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize());

    clickOn(waitForNode("#R0").lookup("#editReview"));
    String newComment = "new comment";
    clickOn(waitForNode("#commentField")).eraseText(comment.length()).write(newComment);
    clickOn(waitForNode("#submitReview"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize());
    IReview review = movieListController.getMovieList().getMovie("test movie").getReviews().stream()
        .findFirst().get();
    assertEquals(newComment, review.getComment());
  }

  @Test
  public void deleteReview() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize());
    clickOn(waitForNode("#R0").lookup("#deleteReview"));
    assertEquals(0, reviewListSize());
  }
}
