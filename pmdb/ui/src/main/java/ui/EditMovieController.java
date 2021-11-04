package ui;

import core.IMovie;
import core.Movie;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.DurationConverter;

/**
 * EditMovieController class.
 * 
 * 
 */
public class EditMovieController {

  @FXML
  CheckBox watchedCheckBox;

  @FXML
  TextField titleField;

  @FXML
  TextField hoursField;

  @FXML
  TextField minutesField;

  @FXML
  TextArea descriptionField;

  @FXML
  Button submitMovie;

  @FXML
  Button cancelButton;

  @FXML
  Text errorField;

  private MovieListController movieListController;

  private IMovie editingMovie;

  private String invalidTitleText = "Tittel må ha lengde mellom 1 og 50 tegn";

  private String usedTitleText = "Denne tittelen er allerede i bruk";

  private String invalidDurationText = "Ugyldig varighet";

  private String invalidDescriptionText = "Beskrivelse kan ikke være 'null'";

  @FXML
  private void submit() {
    validateTitle(() -> {
      validateDuration(() -> {
        validateDescription(() -> {
          int hours = Integer.parseInt(hoursField.getText());
          int minutes = Integer.parseInt(minutesField.getText());
          int duration = DurationConverter.hoursAndMinutesToMinutes(hours, minutes);
          String title = titleField.getText();
          String description = descriptionField.getText();
          boolean watched = watchedCheckBox.isSelected();

          if (editingMovie == null) {
            IMovie movie = new Movie(title, description, duration, watched, new ArrayList<>());
            movieListController.addMovie(movie);
          } else {
            updateExistingMovie(title, description, duration, watched);
            editingMovie = null;
          }
          
          movieListController.movieListIsEdited();
          movieListController.hideEditMovie();
          clearFields();
        });
      });
    });
  }

  @FXML
  private void cancelEditMovie() {
    movieListController.hideEditMovie();
    clearFields();
    editingMovie = null;
  }

  @FXML
  private void titleOnChange() {
    Platform.runLater(() -> {
      validateTitle(() -> {
        String errorFieldText = errorField.getText();
        if (errorFieldText.equals(invalidTitleText) || errorFieldText.equals(usedTitleText)) {
          errorField.setText("");
        }
      });
    });
  }

  @FXML
  private void durationOnChange() {
    validateDuration(() -> {
      if (errorField.getText().equals(invalidDurationText)) {
        errorField.setText("");
      }
    });
  }

  @FXML
  private void descriptionOnChange() {
    validateDuration(() -> {
      if (errorField.getText().equals(invalidDescriptionText)) {
        errorField.setText("");
      }
    });
  }

  private void validateTitle(Runnable ifValid) {
    String title = titleField.getText();
    if (IMovie.isValidTitle(title)) {
      if (thisTitleIsAvailable(title)) {
        ifValid.run();
      } else if (errorField.getText().isEmpty()) {
        errorField.setText(usedTitleText);
      }
    } else if (errorField.getText().isEmpty()) {
      errorField.setText(invalidTitleText);
    }
  }

  private void validateDuration(Runnable ifValid) {
    try {
      int hours = Integer.parseInt(hoursField.getText());
      int minutes = Integer.parseInt(minutesField.getText());
      int duration = DurationConverter.hoursAndMinutesToMinutes(hours, minutes);
      if (IMovie.isValidDuration(duration) && hours >= 0 && minutes >= 0) {
        ifValid.run();
      } else if (errorField.getText().isEmpty()) {
        errorField.setText(invalidDurationText);
      }
    } catch (Exception e) {
      if (errorField.getText().isEmpty()) {
        errorField.setText(invalidDurationText);
      }
    }
  }

  private void validateDescription(Runnable ifValid) {
    String description = descriptionField.getText();
    if (IMovie.isValidDescription(description)) {
      ifValid.run();
    } else if (errorField.getText().isEmpty()) {
      errorField.setText(invalidDescriptionText);
    }
  }

  protected void editMovie(IMovie movie) {
    editingMovie = movie;
    if (movie == null) {
      clearFields();
    } else {
      fillFields();
    }
  }

  protected void injectMovieListController(MovieListController movieListController) {
    this.movieListController = movieListController;
  }

  private void updateExistingMovie(String title, String description, int duration,
      boolean watched) {
    if (editingMovie == null) {
      throw new IllegalStateException("Cant update movie if editingMovie is not set.");
    }
    if (!thisTitleIsAvailable(title)) {
      throw new IllegalStateException("This title is already in use");
    }
    editingMovie.setTitle(title);
    editingMovie.setDescription(description);
    editingMovie.setDuration(duration);
    editingMovie.setWatched(watched);
  }

  private void clearFields() {
    titleField.clear();
    hoursField.clear();
    minutesField.clear();
    descriptionField.clear();
    watchedCheckBox.setSelected(false);
    errorField.setText("");
  }

  private void fillFields() {
    if (editingMovie == null) {
      throw new IllegalStateException("Cant fill fields when no editingMovie is set.");
    }

    watchedCheckBox.setSelected(editingMovie.isWatched());
    titleField.setText(editingMovie.getTitle());
    descriptionField.setText(editingMovie.getDescription());

    int[] timetuppel = DurationConverter.minutesToHoursAndMinutes(editingMovie.getDuration());
    int hours = timetuppel[0];
    int minutes = timetuppel[1];
    hoursField.setText(String.valueOf(hours));
    minutesField.setText(String.valueOf(minutes));
  }

  private boolean thisTitleIsAvailable(String title) {
    IMovie movie = movieListController.getMovieList().getMovie(title);
    return (movie == null) || (movie == editingMovie);
  }
}
