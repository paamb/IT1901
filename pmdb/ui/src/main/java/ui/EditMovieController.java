package ui;

import java.time.LocalTime;

import core.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class EditMovieController {

    @FXML
    CheckBox watchedCheckBox;

    @FXML
    TextField titleField, hoursField, minutesField;

    @FXML
    TextArea descriptionField;

    @FXML
    Button addTheMovie, cancelButton;

    @FXML
    Text errorField;

    private AppController appController;

    @FXML
    private void submit() {
        Movie movie = new Movie();
        try {
            String title = titleField.getText();
            movie.setTitle(title);
            try {
                int hours = Integer.parseInt(hoursField.getText());
                int minutes = Integer.parseInt(minutesField.getText());
                LocalTime duration = LocalTime.of(hours, minutes);
                movie.setDuration(duration);
                try {
                    String description = descriptionField.getText();
                    boolean watched = watchedCheckBox.isSelected();
                    movie.setDescription(description);
                    movie.setWatched(watched);

                    appController.getWatchList().addMovie(movie);
                    appController.hideEditMovie();
                    appController.printWatchList();
                    clearFields();
                } catch (Exception e) {
                    System.err.println(e);
                    errorField.setText("Sjekk navnet ikke finnes fra før.");
                }
            } catch (Exception e) {
                errorField.setText("Ugylidg filmvarighet.");
            }
        } catch (Exception e) {
            errorField.setText("Tittel er ugyldig.");
        }
    }

    @FXML
    private void cancelEditMovie() {
        appController.hideEditMovie();
        clearFields();
    }

    protected void setAppController(AppController appController) {
        this.appController = appController;
    }

    private void clearFields() {
        titleField.clear();
        hoursField.clear();
        minutesField.clear();
        descriptionField.clear();
        watchedCheckBox.setSelected(false);
        errorField.setText("");
    }
}
