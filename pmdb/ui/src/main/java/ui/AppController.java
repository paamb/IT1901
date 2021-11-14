package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * AppController class.
 * 
 * 
 */
public class AppController {

  @FXML
  MovieListController movieListController;

  @FXML
  ReviewListController reviewListController;

  @FXML
  Label serverNotRunningInfo;

  @FXML
  void initialize() {
    movieListController.injectReviewListController(reviewListController);
    reviewListController.injectMovieListController(movieListController);
    if (!movieListController.serverIsRunning()) {
      serverNotRunningInfo.setVisible(true);
    }
  }
}
