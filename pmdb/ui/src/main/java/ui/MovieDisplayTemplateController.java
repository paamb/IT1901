package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import core.IMovie;

public class MovieDisplayTemplateController {

    private IMovie movie;

    private MovieListController movieListController;
    
    @FXML
    Label movieTitle;

    @FXML
    Label movieDuration;
    
    @FXML
    TextArea movieDescription;
    
    @FXML
    Label movieWatched;

    @FXML
    Button editMovie;

    public void injectMovieListController(MovieListController movieListController) {
        this.movieListController = movieListController;
    }

    public void setMovie(IMovie movie) {
        this.movie = movie;
    }

    public void setContent() {
        movieTitle.setText(movie.getTitle());
        movieDuration.setText(movie.getDuration().toString());
        movieDescription.setText(movie.getDescription());
        movieWatched.setText(movie.isWatched() ? "Watched" : "Not watched");
    }

    public void editMovie() {
        movieListController.editMovie(movie);
    }
    
}
