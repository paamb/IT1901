package ui;

import core.WatchList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


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