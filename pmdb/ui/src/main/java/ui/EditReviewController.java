package ui;

import core.IMovie;
import core.IReview;
import core.Review;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * EditReviewController class.
 * 
 * 
 */
public class EditReviewController {

  @FXML
  ComboBox<String> moviesComboBox;

  @FXML
  ComboBox<Integer> ratingComboBox;

  @FXML
  TextArea commentField;

  @FXML
  DatePicker dateField;

  @FXML
  Text errorField;

  @FXML
  Button cancelButton;

  @FXML
  Button submitReview;

  private ReviewListController reviewListController;

  private IReview editingReview;

  private IMovie activeReviewsMovie;

  private ArrayList<IMovie> availableMovies;

  private String invalidRatingText = "Vurdering må være mellom 1 og 10";

  private String invalidCommentText = "Ugyldig kommentar";

  private String invalidWhenWatchedText = "Sett-dato kan ikke være i fremtiden";

  private ReviewDisplayTemplateController reviewDisplayTemplateController;

  @FXML
  void initialize() {
    ratingComboBox.getItems()
        .addAll(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
  }

  @FXML
  void submit() {
    validateRating(() -> {
      validateComment(() -> {
        validateWhenWatched(() -> {
          int rating = ratingComboBox.getSelectionModel().getSelectedItem();
          String comment = commentField.getText();
          LocalDate whenWatched = dateField.getValue();

          if (editingReview == null) {
            IReview review = new Review(comment, rating, whenWatched);
            IMovie movie =
                availableMovies.get(moviesComboBox.getSelectionModel().getSelectedIndex());
            movie.addReview(review);
          } else {
            editingReview.setRating(rating);
            editingReview.setComment(comment);
            editingReview.setWhenWatched(whenWatched);
            reviewDisplayTemplateController.setContent();
          }
          reviewListController.reviewListIsEdited();
          reviewListController.hideEditReview();
          editingReview = null;
          availableMovies = null;
          clearFields();
        });
      });
    });
  }

  @FXML
  private void cancelEditReview() {
    reviewListController.hideEditReview();
    editingReview = null;
    availableMovies = null;
    clearFields();
  }

  @FXML
  private void ratingOnChange() {
    validateRating(onChangeLambda(invalidRatingText));
  }

  @FXML
  private void commentOnChange() {
    validateComment(onChangeLambda(invalidCommentText));
  }

  @FXML
  private void whenWatchedOnChange() {
    validateWhenWatched(onChangeLambda(invalidWhenWatchedText));
  }
  
  private Runnable onChangeLambda(String errorText) {
    return () -> {
      if (errorField.getText().equals(errorText)) {
        errorField.setText("");
      }
    };
  }

  protected void injectReviewListController(ReviewListController reviewListController) {
    this.reviewListController = reviewListController;
  }

  protected void setReviewDisplayController(ReviewDisplayTemplateController controller) {
    reviewDisplayTemplateController = controller;
  }

  protected void editReview(IReview review) {
    editingReview = review;
    if (review == null) {
      availableMovies = new ArrayList<IMovie>(reviewListController.getMovies());
      clearFields();
    } else {
      availableMovies = null;
      fillFields();
    }
    errorField.setText("");
  }

  protected void setActiveReviewsMovie(IMovie movie) {
    activeReviewsMovie = movie;
  }

  private void validateRating(Runnable ifValid) {
    int rating = ratingComboBox.getSelectionModel().getSelectedItem();
    if (IReview.isValidRating(rating)) {
      ifValid.run();
    } else {
      errorField.setText(invalidRatingText);
    }
  }
  
  private void validateComment(Runnable ifValid) {
    String comment = commentField.getText();
    if (IReview.isValidComment(comment)) {
      ifValid.run();
    } else {
      errorField.setText(invalidCommentText);
    }
  }

  private void validateWhenWatched(Runnable ifValid) {
    LocalDate whenWatched = dateField.getValue();
    if (IReview.isValidWhenWatched(whenWatched)) {
      ifValid.run();
    } else {
      errorField.setText(invalidWhenWatchedText);
    }
  }

  private void clearFields() {
    setMoviesComboBox();
    ratingComboBox.getSelectionModel().select(0);
    commentField.clear();
    dateField.setValue(LocalDate.now());
  }

  private void fillFields() {
    setMoviesComboBox();
    ratingComboBox.getSelectionModel().select(editingReview.getRating() - 1);
    commentField.setText(editingReview.getComment());
    dateField.setValue(editingReview.getWhenWatched());
  }

  private void setMoviesComboBox() {
    moviesComboBox.getItems().clear();
    if (editingReview != null) {
      moviesComboBox.getItems().add(activeReviewsMovie.getTitle());
      moviesComboBox.setDisable(true);
    } else if (availableMovies != null) {
      for (IMovie movie : availableMovies) {
        moviesComboBox.getItems().add(movie.getTitle());
      }
      moviesComboBox.setDisable(false);
    } else {
      moviesComboBox.setDisable(true);
    }
    if (!moviesComboBox.getItems().isEmpty()) {
      moviesComboBox.getSelectionModel().select(0);
    }
  }
}
