package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import core.MovieList;
import core.IMovie;

public class MovieListController {

    private MovieList movieList;

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
    void initialize() {
        movieList = new MovieList();
        editMovieController.injectMovieListController(this);
        hideEditMovie();
        printMovieList();
    }

    protected void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void openEditMovie(){
        editMovieWindow.setVisible(true);
    }

    protected void addMovie(IMovie movie) {
        movieList.addMovie(movie);
        printMovieList();
        hideEditMovie();
    }

    protected MovieList getMovieList() {
        return movieList;
    }

    protected void hideEditMovie(){
        editMovieWindow.setVisible(false);
    }

    private void printMovieList(){
        String moviesWatchList = "";
        
        for (IMovie movie : getMovieList().getMovies()) {
            moviesWatchList += movie.getTitle() + "\n";
        }
        moviesDisplay.setText(moviesWatchList);
    }

    
    
}
