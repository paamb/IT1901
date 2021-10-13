package ui;

import javafx.fxml.FXML;

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

    public MovieListController getMovieListController(){
        return movieListController;
    }
    public ReviewListController getReviewListController(){
        return reviewListController;
    }
}
