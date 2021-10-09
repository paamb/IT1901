package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

import core.IReview;
import core.IMovie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class ReviewListController {

    @FXML
    VBox editReviewWindow;

    @FXML
    Button openEditReview;

    @FXML
    Pane reviewDisplay;
    
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

    protected void reviewListIsEdited() {
        movieListController.movieListIsEdited();
    }

    protected Collection<IMovie> getMovies(){
        return new ArrayList<IMovie>(movieListController.getMovieList().getMovies());
    }

    protected void injectMovieListController(MovieListController movieListController){
        this.movieListController = movieListController;
    }

    protected void displayReviewList() {
        reviewDisplay.getChildren().clear();
        try {
            int counter = 0;
            double offsetX = reviewDisplay.getPrefWidth()/2;
            double offsetY = ((Pane) new FXMLLoader(this.getClass().getResource("reviewDisplayTemplate.fxml")).load()).getPrefHeight();
            Collection<IMovie> movies = getMovies();
            for(IMovie movie : movies){
                for(IReview review : movie.getReviews()){
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("reviewDisplayTemplate.fxml"));
                    Pane reviewPane = fxmlLoader.load();
                    reviewPane.setLayoutX(offsetX * (counter % 2));
                    reviewPane.setLayoutY(offsetY * ((int) counter / 2));

                    ReviewDisplayTemplateController reviewDisplayTemplateController = fxmlLoader.getController();
                    reviewDisplayTemplateController.injectReviewListController(this);
                    reviewDisplayTemplateController.setReview(review);
                    reviewDisplayTemplateController.setMovie(movie);
                    reviewDisplayTemplateController.setContent();

                    reviewDisplay.getChildren().add(reviewPane);
                    counter++;
                }
                reviewDisplay.setLayoutY((int) counter / 2);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
