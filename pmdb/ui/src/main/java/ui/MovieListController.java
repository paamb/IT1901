package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MovieListController {

    

    @FXML
    Button openEditMovie;

    @FXML
    VBox editMovieWindow;

    @FXML
    EditMovieController editMovieController;

    private AppController appController;

    @FXML
    void initialize() {
        editMovieController.injectMovieListController(this);
        hideEditMovie();
    }

    protected void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void openEditMovie(){
        editMovieWindow.setVisible(true);
    }

    protected void hideEditMovie(){
        editMovieWindow.setVisible(false);
    }

    
    
}
