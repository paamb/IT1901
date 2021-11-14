package ui;

import core.IMovie;
import core.IReview;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * ReviewDisplayTemplateController class.
 * 
 * 
 */
public class ReviewDisplayTemplateController {

  private IReview review;

  private IMovie movie;

  private ReviewListController reviewListController;

  @FXML
  Label movieTitle;

  @FXML
  Label whenWatched;

  @FXML
  Label rating;

  @FXML
  TextArea comment;

  @FXML
  Button editReview;

  @FXML
  Button deleteReview;

  public void injectReviewListController(ReviewListController reviewListController) {
    this.reviewListController = reviewListController;
  }

  public void setReview(IReview review) {
    this.review = review;
  }

  public void setMovie(IMovie movie) {
    this.movie = movie;
  }

  /**
   * Sets the content of the review in UI.
   */
  public void setContent() {
    movieTitle.setText(movie.getTitle());
    whenWatched.setText(review.getWhenWatched().toString());
    rating.setText(String.valueOf(review.getRating()));
    comment.setText(review.getComment());
  }

  @FXML
  public void editReview() {
    reviewListController.editReview(movie, review, this);
  }

  @FXML
  public void deleteReview() {
    reviewListController.deleteReview(movie, review);
  }
}
