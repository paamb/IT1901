package ui;

import java.io.IOException;

import core.IMovie;
import core.MovieList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import json.moviepersistance.MovieStorage;

public class MovieListController {

    private MovieList movieList;
    private MovieStorage storage;

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
    void initialize() throws IOException {
        storage = new MovieStorage();
        movieList = storage.loadMovies();
        editMovieController.injectMovieListController(this);
        hideEditMovie();
        displayMovieList();
    }

    protected void injectAppController(AppController appController) {
        this.appController = appController;
    }

    protected void editMovie(IMovie movie){
        editMovieController.editMovie(movie);
        editMovieWindow.setVisible(true);
    }

    @FXML
    private void editNewMovie(){
        editMovie(null);
    }

    protected void addMovie(IMovie movie){
        movieList.addMovie(movie);
    }
    
    protected MovieList getMovieList() {
        return movieList;
    }
    
    protected void hideEditMovie(){
        editMovieWindow.setVisible(false);
    }
    
    protected void movieListIsEdited() throws IOException{
        hideEditMovie();
        displayMovieList();
        storage.saveMovies(movieList);
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
