package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import core.Movie;
import core.WatchList;
import javafx.stage.Modality;
import javafx.scene.control.Button;


public class AppController {
    private WatchList watchList;
    private ArrayList<Movie> exampleMovies;
        
    public AppController() {
        watchList = new WatchList();
        exampleMovies = new ArrayList<>();
        exampleMovies.add(new Movie("Tenet", "Wtf", LocalTime.of(10,10,10), true));
        exampleMovies.add(new Movie("Detregnerkjottboller", "En rimelig alreit film", LocalTime.of(10,10,10), false));
        exampleMovies.add(new Movie("Simpsonsthemovie", "Spider pig, spider pig", LocalTime.of(10,10,10), false));
    }
    @FXML private Button addMovieButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void addMovie() throws IOException {
        Stage newWindow = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("EditMovie.fxml"));
        Parent addMovieWindow = fxmlLoader.load();
        newWindow.setScene(new Scene(addMovieWindow));

        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(App.parentStage);
        newWindow.show();
    }

    @FXML
    public void saveMovie(){
        Movie randomMovie = exampleMovies.get((int) (exampleMovies.size() * Math.random()));
        watchList.addMovie(randomMovie);
    }
}