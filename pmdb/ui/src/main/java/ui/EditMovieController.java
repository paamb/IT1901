package ui;

import java.time.LocalTime;
import java.util.ArrayList;

import core.Movie;
import core.WatchList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


public class EditMovieController {
    
    @FXML CheckBox watchedCheckBox;
    @FXML TextField titleField, hoursField, minutesField;
    @FXML TextArea descriptionField;
    @FXML Button addTheMovie;
    
    @FXML private AppController appController;

    @FXML
    private void initialize() {
        System.out.println("editMovieController initialized");
    }

    @FXML
    private void submit(){
        // boolean watched = watchedCheckBox.isSelected();
        // String title = titleField.getText();
        // int hours = Integer.parseInt(hoursField.getText());
        // int minutes = Integer.parseInt(minutesField.getText());
        // String description = descriptionField.getText();
        // LocalTime duration = LocalTime.of(hours,minutes);

        // Movie movie = new Movie(title, description, duration, watched, new ArrayList<>());

        // appController.getWatchList().addMovie(movie);
        appController.hideEditMovie();
    }

    public void setAppController(AppController appController){
        this.appController = appController;
    }
}
