package ui;

import core.WatchList;
import ui.EditMovieController;

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
import javafx.scene.layout.VBox;
import core.WatchList;


public class AppController {

    private WatchList watchList = new WatchList();

    @FXML
    private EditMovieController editMovieController;

    @FXML
    private Button addMovieButton;

    @FXML
    VBox addMovieWindow;

    @FXML
    private void initialize() {
        editMovieController.setAppController(this);
    }

    @FXML
    private void openEditMovie() {
        addMovieWindow.setVisible(true);
    }

    public void hideEditMovie() {
        addMovieWindow.setVisible(false);
    }

    public WatchList getWatchList() {
        return watchList;
    }
}
