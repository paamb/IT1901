package ui;

import java.time.LocalTime;
import java.util.ArrayList;

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
    private void submit(){
        try {
            boolean watched = watchedCheckBox.isSelected();
            String title = titleField.getText();
            int hours = Integer.parseInt(hoursField.getText());
            int minutes = Integer.parseInt(minutesField.getText());
            String description = descriptionField.getText();
            LocalTime duration = LocalTime.of(hours,minutes);
    
            Movie movie = new Movie(title, description, duration, watched, new ArrayList<>());
    
            appController.getWatchList().addMovie(movie);
            appController.hideEditMovie();
            appController.printWatchList();
            clearFields();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            // TODO: Fiks error-melding
            errorField.setText("Error: Se terminal");
        }
    }

    @FXML
    private void cancelEditMovie() {
        appController.hideEditMovie();
        clearFields();
    }

    protected void setAppController(AppController appController){
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
