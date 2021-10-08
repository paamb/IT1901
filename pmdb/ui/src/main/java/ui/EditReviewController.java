package ui;

import core.IReview;

public class EditReviewController {
    
    private ReviewListController reviewListController;
    private IReview editingReview;

    protected void injectReviewListController(ReviewListController reviewListController){
        this.reviewListController = reviewListController;
    }

    protected void editReview(IReview review){
        editingReview = review;
        if(review == null){
            clearFields();
        } else{
            fillFields();
        }
    }

    private void clearFields(){
        
    }

    private void fillFields(){

    }

}
