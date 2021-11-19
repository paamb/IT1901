package ui;

import core.ILabel;
import core.IMovie;
import core.Label;
import core.Movie;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

  @FXML
  ComboBox<String> labelComboBox;

  @FXML
  VBox labelDisplay;

  private MovieListController movieListController;

  private IMovie editingMovie;

  private Collection<ILabel> currentLabels = new ArrayList<>();

  private String invalidTitleText = "Tittel må ha lengde mellom 1 og 50 tegn";

  private String usedTitleText = "Denne tittelen er allerede i bruk";

  private String invalidDurationText = "Ugyldig varighet";

  private String invalidDescriptionText = "Beskrivelse kan ikke være 'null'";

  private MovieDisplayTemplateController movieDisplayController;

  @FXML
  private void submit() {
    validateTitle(() -> {
      validateDuration(() -> {
        validateDescription(() -> {
          int hours = Integer.parseInt(hoursField.getText());
          int minutes = Integer.parseInt(minutesField.getText());
          int duration = (int) Duration.ofHours(hours).toMinutes() + minutes;
          String title = titleField.getText();
          String description = descriptionField.getText();
          boolean watched = watchedCheckBox.isSelected();

          try {
            if (editingMovie == null) {
              IMovie movie = new Movie(title, description, duration, watched, new ArrayList<>(),
                  new ArrayList<>(currentLabels));
              movieListController.addMovie(movie);
            } else {
              updateExistingMovie(title, description, duration, watched);
              editingMovie = null;
            }
          } catch (Exception e) {
            movieListController.syncWithServer();
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
    validateDescription(() -> {
      if (errorField.getText().equals(invalidDescriptionText)) {
        errorField.setText("");
      }
    });
  }

  @FXML
  private void addLabel() {
    String labelName = labelComboBox.getSelectionModel().getSelectedItem();
    validateLabelName(() -> {
      ILabel label = null;
      for (ILabel l : movieListController.getMovieList().getAllLabels()) {
        if (l.getTitle().equals(labelName)) {
          label = l;
        }
      }
      if (label == null) {
        label = new Label(labelName);
      }
      displayLabel(label);
    });
  }

  private void displayLabel(ILabel label) {
    // Checking if label already is added
    for (Node labelPane : labelDisplay.getChildren()) {
      if (labelPane.getId().equals(label.getTitle())) {
        return;
      }
    }
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Label.fxml"));
      Pane labelPane = fxmlLoader.load();
      labelPane.setId(label.getTitle());
      LabelController labelController = fxmlLoader.getController();
      labelController.injectEditMovieController(this);
      labelController.setLabel(label);
      labelController.setPane(labelPane);
      labelDisplay.getChildren().add(labelPane);

      currentLabels.add(label);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void validateLabelName(Runnable ifValid) {
    String labelName = labelComboBox.getSelectionModel().getSelectedItem();
    if (ILabel.isValidTitle(labelName)) {
      ifValid.run();
    }
  }

  private void validateTitle(Runnable ifValid) {
    String title = titleField.getText();
    if (IMovie.isValidTitle(title)) {
      if (thisTitleIsAvailable(title)) {
        ifValid.run();
      } else {
        errorField.setText(usedTitleText);
      }
    } else {
      errorField.setText(invalidTitleText);
    }
  }

  private void validateDuration(Runnable ifValid) {
    try {
      int hours = Integer.parseInt(hoursField.getText());
      int minutes = Integer.parseInt(minutesField.getText());
      int duration = (int) Duration.ofHours(hours).toMinutes() + minutes;
      if (IMovie.isValidDuration(duration) && hours >= 0 && minutes >= 0) {
        ifValid.run();
      } else {
        errorField.setText(invalidDurationText);
      }
    } catch (Exception e) {
      errorField.setText(invalidDurationText);
    }
  }

  private void validateDescription(Runnable ifValid) {
    String description = descriptionField.getText();
    if (IMovie.isValidDescription(description)) {
      ifValid.run();
    } else {
      errorField.setText(invalidDescriptionText);
    }
  }

  protected void setMovieDisplayController(MovieDisplayTemplateController controller) {
    movieDisplayController = controller;
  }

  protected void removeLabel(LabelController controller) {
    labelDisplay.getChildren().remove(controller.getPane());
    currentLabels.remove(controller.getLabel());
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
    editingMovie.setLabels(new ArrayList<ILabel>(currentLabels));
    movieDisplayController.setContent();
  }

  private void clearFields() {
    titleField.clear();
    hoursField.clear();
    minutesField.clear();
    descriptionField.clear();
    watchedCheckBox.setSelected(false);
    errorField.setText("");
    labelDisplay.getChildren().clear();
    currentLabels = new ArrayList<ILabel>();
    fillLabelComboBox();
  }

  private void fillFields() {
    if (editingMovie == null) {
      throw new IllegalStateException("Cant fill fields when no editingMovie is set.");
    }

    watchedCheckBox.setSelected(editingMovie.isWatched());
    titleField.setText(editingMovie.getTitle());
    descriptionField.setText(editingMovie.getDescription());

    Duration totalMinutes = Duration.ofMinutes(editingMovie.getDuration());
    int hours = (int) totalMinutes.toHours();
    int minutes = totalMinutes.toMinutesPart();
    hoursField.setText(String.valueOf(hours));
    minutesField.setText(String.valueOf(minutes));
    errorField.setText("");
    fillLabelComboBox();
    for (Node labelNode : labelDisplay.getChildren()) {
      for (Iterator<ILabel> labels = editingMovie.labelIterator(); labels.hasNext(); ) {
        if (labels.next().getTitle().equals(labelNode.getId())) {
          labelDisplay.getChildren().remove(labelNode);
        }
      }
    }
    currentLabels = new ArrayList<ILabel>();
    for (Iterator<ILabel> labels = editingMovie.labelIterator(); labels.hasNext(); ) {
      displayLabel(labels.next());
    }
  }

  private boolean thisTitleIsAvailable(String title) {
    IMovie movie = movieListController.getMovieList().getMovie(title);
    return (movie == null) || (movie == editingMovie);
  }

  private void fillLabelComboBox() {
    List<String> addItems = new ArrayList<String>();
    for (ILabel label : movieListController.getMovieList().getAllLabels()) {
      addItems.add(label.getTitle());
    }
    labelComboBox.getItems().setAll(addItems);
    labelComboBox.getSelectionModel().select(0);
  }
}
