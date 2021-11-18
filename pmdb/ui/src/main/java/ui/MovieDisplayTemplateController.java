package ui;

import core.ILabel;
import core.IMovie;
import java.time.Duration;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

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

  @FXML
  VBox movieLabels;

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
    Duration totalMinutes = Duration.ofMinutes(movie.getDuration());
    movieDuration
        .setText(String.format("%02d:%02d", totalMinutes.toHours(), totalMinutes.toMinutesPart()));
    movieDescription.setText(movie.getDescription());
    movieWatched.setText(movie.isWatched() ? "Sett" : "Ikke sett");
    movieLabels.getChildren().clear();
    for (Iterator<ILabel> labels = movie.labelIterator(); labels.hasNext(); ) {
      ILabel label = labels.next();
      Label labelLabel = new Label();
      labelLabel.setText(label.getTitle());
      labelLabel.setTextFill(Paint.valueOf(label.getColor()));
      movieLabels.getChildren().add(labelLabel);
    }
  }

  @FXML
  public void editMovie() {
    movieListController.editMovie(movie, this);
  }

  @FXML
  public void deleteMovie() {
    movieListController.deleteMovie(movie);
  }
}
