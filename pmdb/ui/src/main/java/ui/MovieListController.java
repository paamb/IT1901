package ui;

import java.io.IOException;

import core.IMovie;
import core.MovieList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import json.moviepersistance.MovieStorage;

public class MovieListController {

    private MovieList movieList;
    private MovieStorage storage;

    @FXML
    Button openEditMovie;

    @FXML
    VBox editMovieWindow;

    @FXML
    Text moviesDisplay;

    @FXML
    EditMovieController editMovieController;

    private AppController appController;

    @FXML
    void initialize() throws IOException {
        storage = new MovieStorage();
        movieList = storage.loadMovies();
        editMovieController.injectMovieListController(this);
        hideEditMovie();
        displayMovieList();
    }

    protected void injectAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void openEditMovie(){
        editMovieWindow.setVisible(true);
    }

    protected void addMovie(IMovie movie) throws IOException {
        movieList.addMovie(movie);
        storage.saveMovies(movieList);
        displayMovieList();
        hideEditMovie();
    }

    protected MovieList getMovieList() {
        return movieList;
    }

    protected void hideEditMovie(){
        editMovieWindow.setVisible(false);
    }

    private void displayMovieList(){
        String moviesWatchList = "";
        
        for (IMovie movie : getMovieList().getMovies()) {
            moviesWatchList += movie.getTitle() + "\n";
        }
        moviesDisplay.setText(moviesWatchList);
    }
}
