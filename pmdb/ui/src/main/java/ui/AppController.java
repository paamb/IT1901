package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Modality;
import javafx.scene.control.Button;


public class AppController {

    @FXML private Button addMovieButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void addMovie() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("EditMovie.fxml"));
        Parent addMovieWindow = fxmlLoader.load();
        newWindow.setScene(new Scene(addMovieWindow));

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(App.parentStage);
        newWindow.show();
    }
}
