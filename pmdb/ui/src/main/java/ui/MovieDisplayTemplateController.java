package ui;

import core.IMovie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import util.DurationConverter;

/**
 * MovieDisplayTemplateController class.
 * 
 * 
 */
public class MovieDisplayTemplateController {

  private IMovie movie;

  private MovieListController movieListController;

  @FXML
  Label movieTitle;

  @FXML
  Label movieDuration;

  @FXML
  Label movieWatched;

  @FXML
  TextArea movieDescription;

  @FXML
  Button editMovie;

  @FXML
  Button deleteMovie;

  public void injectMovieListController(MovieListController movieListController) {
    this.movieListController = movieListController;
  }

  public void setMovie(IMovie movie) {
    this.movie = movie;
  }

  /**
   * Sets the content of the movie on the UI.
   */
  public void setContent() {
    movieTitle.setText(movie.getTitle());

    movieDuration.setText(DurationConverter.getDurationDisplayText(movie.getDuration()));
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
