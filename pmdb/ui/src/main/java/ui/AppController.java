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

    // @FXML
    // private void openEditMovie() {
    //     addMovieWindow.setVisible(true);
    // }

    // protected void hideEditMovie() {
    //     addMovieWindow.setVisible(false);
    // }

    // protected WatchList getWatchList() {
    //     return watchList;
    // }

    // protected void printWatchList() {
    //     String moviesWatchList = "";
        
    //     for (IMovie movie : getWatchList().getMovies()) {
    //         moviesWatchList += movie.getTitle() + "\n";
    //     }
    //     //watchListMovies.setText(moviesWatchList);
    // }
}
