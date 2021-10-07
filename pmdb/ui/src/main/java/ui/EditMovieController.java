package ui;

import java.time.LocalTime;

import core.Movie;
import core.IMovie;
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

    private MovieListController movieListController;

    private IMovie editingMovie;

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

                    if(editingMovie == null){
                        movieListController.addMovie(movie);
                    } else {
                        updateExistingMovie(movie);
                        editingMovie = null;
                    }
                    movieListController.movieListIsEdited();
                    movieListController.hideEditMovie();
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
        movieListController.hideEditMovie();
        clearFields();
        editingMovie = null;
    }

    protected void editMovie(IMovie movie){
        if(movie == null){
            editingMovie = null;
            clearFields();
        } else {
            editingMovie = movie;
            fillFields();
        }
    }

    protected void injectMovieListController(MovieListController movieListController) {
        this.movieListController = movieListController;
    }

    private void updateExistingMovie(IMovie movie){
        if(editingMovie == null){
            throw new IllegalStateException("Cant update movie if editingMovie is not set.");
        }
        editingMovie.setTitle(movie.getTitle());
        editingMovie.setDescription(movie.getDescription());
        editingMovie.setDuration(movie.getDuration());
        editingMovie.setWatched(movie.isWatched());
    }

    private void clearFields() {
        titleField.clear();
        hoursField.clear();
        minutesField.clear();
        descriptionField.clear();
        watchedCheckBox.setSelected(false);
        errorField.setText("");
    }

    private void fillFields(){
        if(editingMovie == null){
            throw new IllegalStateException("Cant fill fields when no editingMovie is set.");
        }

        watchedCheckBox.setSelected(editingMovie.isWatched());
        titleField.setText(editingMovie.getTitle());
        descriptionField.setText(editingMovie.getDescription());
        hoursField.setText(String.valueOf(editingMovie.getDuration().getHour()));
        minutesField.setText(String.valueOf(editingMovie.getDuration().getMinute()));
    }
}
