package ui;

import java.time.LocalTime;
import java.util.ArrayList;
import core.Movie;
import core.WatchList;
import javafx.fxml.FXML;

public class AppController {
    private WatchList watchList;
    private ArrayList<Movie> exampleMovies;
    public AppController() {
        watchList = new WatchList();
        exampleMovies = new ArrayList<>();
        exampleMovies.add(new Movie("Tenet", "Wtf", LocalTime.of(10,10,10)));
        exampleMovies.add(new Movie("Detregnerkjottboller", "En rimelig alreit film", LocalTime.of(10,10,10)));
        exampleMovies.add(new Movie("Simpsonsthemovie", "Spider pig, spider pig", LocalTime.of(10,10,10)));

    }

    @FXML
    public void addMovieToWatchList(){
        //TODO remove next line after testing is finished
        Movie randomMovie = exampleMovies.get((int) (exampleMovies.size() * Math.random()));
        watchList.addMovie(randomMovie);
    }
}
