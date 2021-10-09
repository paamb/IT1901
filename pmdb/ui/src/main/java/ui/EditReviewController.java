package ui;

import core.IReview;
import core.Review;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import core.IMovie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class EditReviewController {

    @FXML
    ComboBox<String> moviesComboBox;

    @FXML
    ComboBox<Integer> ratingComboBox;

    @FXML
    TextArea commentField;

    @FXML
    DatePicker dateField;

    @FXML
    Text errorField;

    @FXML
    Button cancelButton, addReviewButton;

    private ReviewListController reviewListController;

    private IReview editingReview;

    private IMovie activeReviewsMovie;
    
    private ArrayList<IMovie> availableMovies;

    @FXML
    void initialize(){
        ratingComboBox.getItems().addAll(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    @FXML
    void submit(){
        try {
            int rating = ratingComboBox.getSelectionModel().getSelectedItem();
            String comment = commentField.getText();
            LocalDate whenWatched = dateField.getValue();
            if(editingReview == null){
                IReview review = new Review(comment, rating, whenWatched);
                IMovie movie = availableMovies.get(moviesComboBox.getSelectionModel().getSelectedIndex());
                movie.addReview(review);
            } else {
                editingReview.setRating(rating);
                editingReview.setComment(comment);
                editingReview.setWhenWatched(whenWatched);
            }
            reviewListController.reviewListIsEdited();
            reviewListController.hideEditReview();
            clearFields();
        } catch (Exception e) {
            errorField.setText("Error");
            System.out.println(e);
        }
    }

    @FXML
    void cancelEditReview(){
        reviewListController.hideEditReview();
        editingReview = null;
        availableMovies = null;
        clearFields();
    }

    protected void injectReviewListController(ReviewListController reviewListController){
        this.reviewListController = reviewListController;
    }

    protected void editReview(IReview review){
        editingReview = review;
        if(review == null){
            availableMovies = new ArrayList<IMovie>(reviewListController.getMovies());
            clearFields();
        } else{
            availableMovies = null;
            fillFields();
        }
        errorField.setText("");
    }

    protected void setActiveReviewsMovie(IMovie movie){
        activeReviewsMovie = movie;
    }

    private void clearFields(){
        setMoviesComboBox();
        ratingComboBox.getSelectionModel().select(0);
        commentField.clear();
        dateField.setValue(LocalDate.now());
    }

    private void fillFields(){
        setMoviesComboBox();
        ratingComboBox.getSelectionModel().select(editingReview.getRating()-1);
        commentField.setText(editingReview.getComment());
        dateField.setValue(editingReview.getWhenWatched());
    }

    private void setMoviesComboBox(){
        moviesComboBox.getItems().clear();
        if (availableMovies == null){
            moviesComboBox.getItems().add(activeReviewsMovie.getTitle());
            moviesComboBox.setDisable(true);
        } else {
            for(IMovie movie : availableMovies){
                moviesComboBox.getItems().add(movie.getTitle());
            }
            moviesComboBox.setDisable(false);
        }
        if(!moviesComboBox.getItems().isEmpty()){
            moviesComboBox.getSelectionModel().select(0);
        }
    }

}
