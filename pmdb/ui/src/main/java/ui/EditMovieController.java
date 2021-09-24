package ui;

import java.time.LocalTime;
import java.util.ArrayList;

import core.Movie;
import core.WatchList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditMovieController {
    //TODO: Initialize this field in AppController and refere to that field in this controller
    private WatchList watchList;
    
    @FXML
    CheckBox watchedCheckBox;

    @FXML
    TextField titleField, hoursField, minutesField;
    
    @FXML
    TextArea descriptionField;
    
    @FXML
    private void submit(){
        boolean watched = watchedCheckBox.isSelected();
        String title = titleField.getText();
        int hours = Integer.parseInt(hoursField.getText());
        int minutes = Integer.parseInt(minutesField.getText());
        String description = descriptionField.getText();
        LocalTime duration = LocalTime.of(hours,minutes);

        Movie movie = new Movie(title, description, duration, watched, new ArrayList<>());
        watchList.addMovie(movie);
    }
}
