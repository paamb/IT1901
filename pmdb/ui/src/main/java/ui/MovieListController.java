package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;

import core.MovieList;

import core.IMovie;

public class MovieListController {

    private MovieList movieList;

    @FXML
    Button openEditMovie;

    @FXML
    VBox editMovieWindow;

    @FXML
    Text moviesDisplay;

    @FXML
    EditMovieController editMovieController;

    @FXML
    Pane movieDisplay;

    private AppController appController;

    @FXML
    void initialize() {
        movieList = new MovieList();
        editMovieController.injectMovieListController(this);
        hideEditMovie();
        displayMovieList();
    }

    protected void injectAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private void openEditMovie(){
        editMovieWindow.setVisible(true);
    }

    protected void addMovie(IMovie movie) {
        movieList.addMovie(movie);
        displayMovieList();
        hideEditMovie();
    }

    protected MovieList getMovieList() {
        return movieList;
    }

    protected void hideEditMovie(){
        editMovieWindow.setVisible(false);
    }

    private void displayMovieList(){
        try {
            int counter = 0;
            double offsetX = movieDisplay.getPrefWidth()/2;
            double offsetY = ((Pane) new FXMLLoader(this.getClass().getResource("movieDisplayTemplate.fxml")).load()).getPrefHeight();
            for (IMovie movie : getMovieList().getMovies()) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("movieDisplayTemplate.fxml"));
                Pane moviePane = fxmlLoader.load();
                moviePane.setLayoutX(offsetX * (counter % 2));
                moviePane.setLayoutY(offsetY * ((int) counter / 2));
                
                MovieDisplayTemplateController movieDisplayTemplateController = fxmlLoader.getController();
                movieDisplayTemplateController.injectMovieListController(this);
                movieDisplayTemplateController.setMovie(movie);
                movieDisplayTemplateController.setContent();

                movieDisplay.getChildren().add(moviePane);
                counter++;
            }
            movieDisplay.setLayoutY((int) counter/2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
