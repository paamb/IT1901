package ui;

import core.IReview;
import core.Review;
import core.IMovie;

import java.util.ArrayList;


public class EditReviewController {

    private ReviewListController reviewListController;

    private IReview editingReview;
    
    private ArrayList<IMovie> availableMovies;

    protected void injectReviewListController(ReviewListController reviewListController){
        this.reviewListController = reviewListController;
    }

    protected void editReview(IReview review){
        editingReview = review;
        if(review == null){
            clearFields();
            availableMovies = new ArrayList<IMovie>(reviewListController.getMovies());
        } else{
            fillFields();
            disableMoviesComboBox();
        }
    }

    private void disableMoviesComboBox(){

    }

    private void clearFields(){
        
    }

    private void fillFields(){

    }

}
