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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

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
    text.setText("");
  }

  private int reviewListSize() {
    int counter = 0;
    for (IMovie movie : movieListController.getMovies()) {
      counter += movie.getReviews().size();
    }
    return counter;
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
    clickOn("#openEditReview");
    clickOn("#commentField").write(comment);
    clickOn("#dateField");
    editReviewController.dateField.setValue(whenWatched);
    clickOn("#submitReview");
    assertEquals(1, reviewListSize());
    assertFalse(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void addReview_invalidDate() {
    LocalDate invalidDate = LocalDate.now().plusDays(30);
    clickOn("#openEditReview");
    clickOn("#dateField");
    editReviewController.dateField.setValue(invalidDate);
    clickOn("#submitReview");
    assertEquals(0, reviewListSize());
    assertTrue(reviewListController.editReviewWindow.isVisible());
  }

  @Test
  public void editReview() {
    
    clickOn("#openEditReview");
    clickOn("#commentField").write(comment);
    clickOn("#dateField");
    editReviewController.dateField.setValue(whenWatched);
    clickOn("#submitReview");
    assertEquals(1, reviewListSize());
    
    clickOn(reviewListController.reviewDisplay.lookup("#0").lookup("#editReview"));
    clickOn("#commentField");
    deleteInput(editReviewController.commentField);
    String newComment = "new comment";
    clickOn("#commentField").write(newComment);
    clickOn("#submitReview");

    assertEquals(1, reviewListSize());
    IReview review = movieListController.getMovieList().getMovie("test movie")
        .getReviews().stream().findFirst().get();
    assertEquals(newComment, review.getComment());
  }

  @Test
  public void deleteReview() {
    clickOn("#openEditReview");
    clickOn("#commentField").write(comment);
    clickOn("#dateField");
    editReviewController.dateField.setValue(whenWatched);
    clickOn("#submitReview");
    assertEquals(1, reviewListSize());

    clickOn(reviewListController.reviewDisplay.lookup("#0").lookup("#deleteReview"));
    assertEquals(0, reviewListSize());
  }
}
