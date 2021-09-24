package ui;

import core.WatchList;
import core.IMovie;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;


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
        printWatchList();
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

        for (IMovie movie : getWatchList().getMovies()) {
            moviesWatchList += movie.getTitle() + "\n";
        }
        watchListMovies.setText(moviesWatchList);
    }
}
