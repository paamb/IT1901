package ui;

import javafx.fxml.FXML;

public class AppController {

    @FXML
    MovieListController movieListController;

    @FXML
    ReviewListController reviewListController;

    @FXML
    void initialize() {
        movieListController.setAppController(this);
        // reviewListController.setAppController(this);
    }
}
