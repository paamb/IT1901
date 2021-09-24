package ui;

import core.WatchList;
import core.Movie;

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
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import core.WatchList;


public class AppController {

    private WatchList watchList;

    @FXML
    EditMovieController editMovieController;

    @FXML
    Button addMovieButton;

    @FXML
    VBox addMovieWindow;

    @FXML
    Text watchListMovies;

    @FXML
    private void initialize() {
        watchList = new WatchList();
        editMovieController.setAppController(this);
    }

    @FXML
    private void openEditMovie() {
        addMovieWindow.setVisible(true);
    }

    protected void hideEditMovie() {
        addMovieWindow.setVisible(false);
    }

    protected WatchList getWatchList() {
        return watchList;
    }

    @FXML
    protected void printWatchList() {
        String moviesWatchList = "";

        for (Movie movie : getWatchList().getMovies()) {
            moviesWatchList += movie.getTitle() + "\n";
        }
        watchListMovies.setText(moviesWatchList);
    }
}
