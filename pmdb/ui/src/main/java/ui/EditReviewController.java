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
    Text errorField;

    @FXML
    DatePicker dateField;

    @FXML
    Button cancelButton, addReviewButton;

    private ReviewListController reviewListController;

    private IReview editingReview;
    
    private ArrayList<IMovie> availableMovies;

    @FXML
    void initialize(){
        ratingComboBox.getItems().addAll(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
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
    }

    private void clearFields(){
        setMoviesComboBox();
        ratingComboBox.getSelectionModel().select(0);
        commentField.clear();
        errorField.setText("");
        dateField.setValue(null);
    }

    private void fillFields(){
        setMoviesComboBox();
        ratingComboBox.getSelectionModel().select(editingReview.getRating()-1);
        commentField.setText(editingReview.getComment());
        errorField.setText("");
        dateField.setValue(editingReview.getWhenWatched());
    }

    private void setMoviesComboBox(){
        moviesComboBox.getItems().clear();
        if (availableMovies == null){

            moviesComboBox.setDisable(true);
        } else {
            for(IMovie movie : availableMovies){
                moviesComboBox.getItems().add(movie.getTitle());
            }
            moviesComboBox.setDisable(false);
        }
    }

}
