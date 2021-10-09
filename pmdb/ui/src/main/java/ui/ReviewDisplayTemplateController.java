package ui;

import core.IMovie;
import core.IReview;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ReviewDisplayTemplateController {
    
    private IReview review;

    private IMovie movie;

    private ReviewListController reviewListController;

    @FXML
    Label movieTitle, whenWatched, rating;

    @FXML
    TextArea comment;

    @FXML
    Button editReview, deleteReview;

    public void injectReviewListController(ReviewListController reviewListController){
        this.reviewListController = reviewListController;
    }

    public void setReview(IReview review){
        this.review = review;
    }
    public void setMovie(IMovie movie){
        this.movie = movie;
    }

    public void setContent(){
        movieTitle.setText(movie.getTitle());
        whenWatched.setText(review.getWhenWatched().toString());
        rating.setText(String.valueOf(review.getRating()));
        comment.setText(review.getComment());
    }

    @FXML
    public void editReview() {
        reviewListController.editReview(review);
    }

    @FXML
    public void deleteReview() {
        //TODO: delete review
    }
}
