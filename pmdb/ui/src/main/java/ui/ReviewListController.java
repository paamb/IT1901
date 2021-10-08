package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

import core.IReview;
import core.IMovie;
import javafx.fxml.FXML;

public class ReviewListController {

    @FXML
    VBox editReviewWindow;

    @FXML
    Button openEditReview;
    
    private MovieListController movieListController;

    @FXML
    EditReviewController editReviewController;

    @FXML
    void initialize() {
        editReviewController.injectReviewListController(this);
        hideEditReview();
    }

    @FXML
    public void editNewReview(){
        editReview(null);
    }

    protected void editReview(IReview review){
        editReviewController.editReview(review);
        editReviewWindow.setVisible(true);
    }

    protected void hideEditReview(){
        editReviewWindow.setVisible(false);
    }

    protected Collection<IMovie> getMovies(){
        return new ArrayList<IMovie>(movieListController.getMovieList().getMovies());
    }

    protected void injectMovieListController(MovieListController movieListController){
        this.movieListController = movieListController;
    }

}
