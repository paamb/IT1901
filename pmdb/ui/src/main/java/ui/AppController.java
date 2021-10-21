package ui;

import javafx.fxml.FXML;

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
  void initialize() {
    movieListController.injectReviewListController(reviewListController);
    reviewListController.injectMovieListController(movieListController);
  }
}
