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
    Label movieTitle, movieDuration, movieWatched;
    
    @FXML
    TextArea movieDescription;

    @FXML
    Button editMovie, deleteMovie;

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
        movieWatched.setText(movie.isWatched() ? "Sett" : "Ikke sett");
    }

    @FXML
    public void editMovie() {
        movieListController.editMovie(movie);
    }

    @FXML
    public void deleteMovie() {
        movieListController.deleteMovie(movie);
    }    
    
}
