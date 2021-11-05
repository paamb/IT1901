package ui;

import core.IMovie;
import core.Movie;
import java.util.ArrayList;
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

  @FXML
  private void submit() {
    String title = titleField.getText();
    if (IMovie.isValidTitle(title)) {
      if (thisTitleIsAvailable(title)) {
        try {
          int hours = Integer.parseInt(hoursField.getText());
          int minutes = Integer.parseInt(minutesField.getText());
          int duration = DurationConverter.hoursAndMinutesToMinutes(hours, minutes);
          if (IMovie.isValidDuration(duration) && hours >= 0 && minutes >= 0) {
            String description = descriptionField.getText();
            if (IMovie.isValidDescription(description)) {
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
            } else {
              errorField.setText("Beskrivelse kan ikke være 'null'");
            }
          } else {
            errorField.setText("Ugyldig varighet");
          }
        } catch (Exception e) {
          errorField.setText("Varighet-feltet må være mer enn 00:00 og mindre enn 23:59");
        }
      } else {
        errorField.setText("Denne tittelen er allerede i bruk");
      }
    } else {
      errorField.setText("Tittel må ha lengde mellom 1 og 50 tegn");
    }
  }

  @FXML
  private void cancelEditMovie() {
    movieListController.hideEditMovie();
    clearFields();
    editingMovie = null;
  }

  @FXML
  private void addLabel() {
    //add label
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
