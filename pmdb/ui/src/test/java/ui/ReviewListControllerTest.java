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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import json.moviepersistance.MovieStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class ReviewListControllerTest extends ApplicationTest {

  // Need instance of NodeFinderHelper because NodeFinderHelper cannot have static methods
  private NodeFinderHelper nodeFinder;

  private Node waitForNode(String id) {
    return nodeFinder.waitForNode(id);
  }

  private void waitThenWrite(String text) {
    nodeFinder.waitThenWrite(text);
  }

  private MovieListController movieListController;

  private ReviewListController reviewListController;

  private EditReviewController editReviewController;

  private final File testFile = new File(getClass().getResource("MovieList_test.json").getFile());

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
      counter += movie.getReviewCount();
    }
    return counter;
  }

  private void addDummyReview() {
    clickOn(waitForNode("#openEditReview"));
    WaitForAsyncUtils.waitForFxEvents();
    clickOn(waitForNode("#commentField"));
    waitThenWrite(comment);
    clickOn(waitForNode("#dateField"));
    ((DatePicker) waitForNode("#dateField")).setValue(whenWatched);
    clickOn(waitForNode("#submitReview"));
  }

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setup() {
    nodeFinder = new NodeFinderHelper();
    try {
      movieListController.loadMovieListFile(testFile);
      WaitForAsyncUtils.waitForFxEvents();
    } catch (Exception e) {
      fail(e);
    }
    assertEquals(0, reviewListSize(), "wrong number of reviews loaded");
  }

  /**
   * Done after each test.
   */
  @AfterEach
  public void tearDown() {
    try {
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
      IMovie movie = movieList.getMovie("test movie");
      movie.setReviews(new ArrayList<IReview>());
      storage.saveMovieList(movieList);
    } catch (Exception e) {
      fail(e);
    }
  }

  @Test
  public void test_initialize() {
    WaitForAsyncUtils.waitForFxEvents();
    assertNotNull(reviewListController, "reviewListController not loaded");
    assertNotNull(editReviewController, "editReviewController not loaded");
    assertNotNull(movieListController, "movieListController not loaded");
  }

  @Test
  public void addReview_valid() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize(), "wrong number of reviews");
    assertFalse(reviewListController.editReviewWindow.isVisible(),
        "editReviewWindow should not be visible");
  }

  @Test
  public void addReview_invalidDate() {
    LocalDate invalidDate = LocalDate.now().plusDays(30);
    clickOn(waitForNode("#openEditReview"));
    clickOn(waitForNode("#dateField"));
    ((DatePicker) waitForNode("#dateField")).setValue(invalidDate);
    clickOn(waitForNode("#submitReview"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(0, reviewListSize(), "wrong number of reviews");
    assertTrue(reviewListController.editReviewWindow.isVisible(),
        "editReviewWindow should be visible");
  }

  @Test
  public void editReview() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize(), "wrong number of reviews");

    clickOn(waitForNode("#R0").lookup("#editReview"));
    String newComment = "new comment";
    clickOn(waitForNode("#commentField")).eraseText(comment.length());
    waitThenWrite(newComment);
    clickOn(waitForNode("#submitReview"));
    WaitForAsyncUtils.waitForFxEvents();

    assertEquals(1, reviewListSize(), "wrong number of reviews");

    Iterator<IReview> reviewIterator = movieListController.getMovieList()
        .getMovie("test movie").reviewIterator();
    if (reviewIterator.hasNext()) {
      assertEquals(newComment, reviewIterator.next().getComment(), "wrong comment saved");
    } else {
      fail();
    }
  }

  @Test
  public void deleteReview() {
    addDummyReview();
    WaitForAsyncUtils.waitForFxEvents();

    clickOn(waitForNode("#R0").lookup("#deleteReview"));
    assertEquals(0, reviewListSize(), "wrong number of reviews");
  }
}
