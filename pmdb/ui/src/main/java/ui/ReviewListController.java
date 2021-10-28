package ui;

import core.IMovie;
import core.IReview;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * ReviewListController class.
 * 
 * 
 */
public class ReviewListController {

  @FXML
  VBox editReviewWindow;

  @FXML
  Button openEditReview;

  @FXML
  Label noMoviesWarning;

  @FXML
  Pane reviewDisplay;

  private MovieListController movieListController;

  @FXML
  EditReviewController editReviewController;

  @FXML
  void initialize() {
    editReviewController.injectReviewListController(this);
    hideEditReview();
  }

  @FXML
  public void editNewReview() {
    editReview(null, null);
  }

  protected void editReview(IMovie movie, IReview review) {
    editReviewController.setActiveReviewsMovie(movie);
    editReviewController.editReview(review);
    editReviewWindow.setVisible(true);
  }

  protected void hideEditReview() {
    editReviewWindow.setVisible(false);
  }

  protected void reviewListIsEdited() {
    movieListController.movieListIsEdited();
  }

  protected Collection<IMovie> getMovies() {
    return new ArrayList<IMovie>(movieListController.getMovieList().getMovies());
  }

  protected void injectMovieListController(MovieListController movieListController) {
    this.movieListController = movieListController;
    displayReviewList();
  }

  protected void deleteReview(IMovie movie, IReview review) {
    movie.removeReview(review);
    reviewListIsEdited();
  }

  protected void displayReviewList() {
    reviewDisplay.getChildren().clear();
    hideEditReview();
    if (!movieListController.getMovies().isEmpty()) {
      noAvailableMovies(false);
      try {
        openEditReview.setDisable(false);
        int counter = 0;
        double offsetX = reviewDisplay.getPrefWidth() / 2;
        double offsetY =
            ((Pane) new FXMLLoader(this.getClass().getResource("ReviewDisplayTemplate.fxml"))
                .load()).getPrefHeight();
        Collection<IMovie> movies = getMovies();
        for (IMovie movie : movies) {
          for (IReview review : movie.getReviews()) {
            FXMLLoader fxmlLoader =
                new FXMLLoader(this.getClass().getResource("ReviewDisplayTemplate.fxml"));
            Pane reviewPane = fxmlLoader.load();
            int counterCalc = (int) counter / 2;
            reviewPane.setLayoutX(offsetX * (counter % 2));
            reviewPane.setLayoutY(offsetY * counterCalc);
            reviewPane.setId("R" + String.valueOf(counter));

            ReviewDisplayTemplateController reviewDisplayTemplateController =
                fxmlLoader.getController();
            reviewDisplayTemplateController.injectReviewListController(this);
            reviewDisplayTemplateController.setReview(review);
            reviewDisplayTemplateController.setMovie(movie);
            reviewDisplayTemplateController.setContent();

            reviewDisplay.getChildren().add(reviewPane);
            counter++;
          }
          int counterCalc = (int) counter / 2;
          reviewDisplay.setLayoutY(counterCalc);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      noAvailableMovies(true);
    }
  }

  private void noAvailableMovies(boolean bool) {
    openEditReview.setDisable(bool);
    noMoviesWarning.setVisible(bool);
  }
}
