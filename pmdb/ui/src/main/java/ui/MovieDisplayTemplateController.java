package ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
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
    CheckBox movieWatched;

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
        String durationAsString =  String.valueOf(movie.getDuration().getHour()) + ":" + String.valueOf(movie.getDuration().getMinute());
        movieDuration.setText(durationAsString);
        movieDescription.setText(movie.getDescription());
        movieWatched.setSelected(movie.isWatched());
    }

    public void editMovie() {
        System.out.println("Editing movie...");
    }
    
}
