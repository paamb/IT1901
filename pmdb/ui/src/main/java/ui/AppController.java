package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
  Label noConnectionInfo;

  @FXML
  Button syncWithServer;

  @FXML
  void initialize() {
    movieListController.injectReviewListController(reviewListController);
    movieListController.injectAppController(this);
    reviewListController.injectMovieListController(movieListController);
    updateConnectionInfo();
  }
  
  @FXML
  protected void syncWithServer() {
    movieListController.syncWithServer();
    updateConnectionInfo();
  }
  
  private void updateConnectionInfo() {
    noConnectionInfo.setVisible(!movieListController.serverIsRunning());
  }
}
