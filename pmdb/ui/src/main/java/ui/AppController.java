package ui;

import javafx.fxml.FXML;

public class AppController {

    @FXML
    MovieListController movieListController;

    @FXML
    ReviewListController reviewListController;

    @FXML
    void initialize() {
        movieListController.injectAppController(this);
        // reviewListController.setAppController(this);
    }
}
