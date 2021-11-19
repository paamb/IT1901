package ui;

import core.IMovie;
import core.IReview;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * ReviewListController class.
 * 
 * 
 */
public class ReviewListController {

  @FXML
  VBox editReviewWindow;

  @FXML
  Button openEditReview;

  @FXML
  Label noMoviesWarning;

  @FXML
  Pane reviewDisplay;

  private MovieListController movieListController;

  private HashMap<String, IReview> displayedReviews;

  @FXML
  EditReviewController editReviewController;

  @FXML
  void initialize() {
    displayedReviews = new HashMap<String, IReview>();
    editReviewController.injectReviewListController(this);
    hideEditReview();
  }

  @FXML
  public void editNewReview() {
    editReview(null, null, null);
  }

  protected void editReview(IMovie movie, IReview review,
      ReviewDisplayTemplateController controller) {
    editReviewController.setActiveReviewsMovie(movie);
    editReviewController.editReview(review);
    editReviewController.setReviewDisplayController(controller);
    editReviewWindow.setVisible(true);
  }

  protected void hideEditReview() {
    editReviewWindow.setVisible(false);
  }

  protected void reviewListIsEdited() {
    movieListController.movieListIsEdited();
  }

  protected Collection<IMovie> getMovies() {
    return movieListController.getMovieList().getMovies();
  }

  protected void injectMovieListController(MovieListController movieListController) {
    this.movieListController = movieListController;
    displayReviewList();
  }

  protected void deleteReview(IMovie movie, IReview review) {
    movie.removeReview(review);
    reviewListIsEdited();
  }

  protected void displayReviewList() {
    clearDeletedReviews();
    HashMap<String, IReview> newHashMap = new HashMap<String, IReview>();
    hideEditReview();
    if (!movieListController.getMovies().isEmpty()) {
      noAvailableMovies(false);
      try {
        openEditReview.setDisable(false);
        int counter = 0;
        double offsetX = reviewDisplay.getPrefWidth() / 2;
        double offsetY = -1.0;
        Collection<IMovie> movies = getMovies();
        for (IMovie movie : movies) {
          for (Iterator<IReview> reviews = movie.reviewIterator(); reviews.hasNext(); ) {
            IReview review = reviews.next(); 
            Pane reviewPane = findReviewPane(review);
            if (reviewPane == null) {
              FXMLLoader fxmlLoader =
                  new FXMLLoader(this.getClass().getResource("ReviewDisplayTemplate.fxml"));
              reviewPane = fxmlLoader.load();
              
              ReviewDisplayTemplateController reviewDisplayTemplateController =
                  fxmlLoader.getController();
              reviewDisplayTemplateController.injectReviewListController(this);
              reviewDisplayTemplateController.setReview(review);
              reviewDisplayTemplateController.setMovie(movie);
              reviewDisplayTemplateController.setContent();
              reviewDisplay.getChildren().add(reviewPane);
            }

            if (offsetY < 0.0) {
              offsetY = reviewPane.getPrefHeight();
            }
            
            int counterCalc = (int) counter / 2;
            reviewPane.setLayoutX(offsetX * (counter % 2));
            reviewPane.setLayoutY(offsetY * counterCalc);
            String id = "R" + String.valueOf(counter);
            reviewPane.setId(id);
            newHashMap.put(id, review);

            counter++;
          }
          int counterCalc = (int) counter / 2;
          reviewDisplay.setLayoutY(counterCalc);
        }
        displayedReviews = newHashMap;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      noAvailableMovies(true);
    }
  }

  private void clearDeletedReviews() {
    Collection<IReview> allReviews = getAllReviews();
    Collection<Node> deletingNodes = new ArrayList<Node>();
    for (Node reviewNode : reviewDisplay.getChildren()) {
      IReview nodeReview = displayedReviews.get(reviewNode.getId());
      if (!allReviews.contains(nodeReview)) {
        deletingNodes.add(reviewNode);
      }
    }
    deletingNodes.forEach(node -> {
      reviewDisplay.getChildren().remove(node);
    });
  }

  private Pane findReviewPane(IReview review) {
    for (Node reviewNode : reviewDisplay.getChildren()) {
      try {
        if (review == displayedReviews.get(reviewNode.getId())) {
          return (Pane) reviewNode;
        }
      } catch (Exception e) {
        // ignore
      }
    }
    return null;
  }

  private Collection<IReview> getAllReviews() {
    Collection<IReview> allReviews = new ArrayList<IReview>();
    for (IMovie movie : getMovies()) {
      for (Iterator<IReview> reviews = movie.reviewIterator(); reviews.hasNext(); ) {
        allReviews.add(reviews.next());
      }
    }
    return allReviews;
  }

  private void noAvailableMovies(boolean bool) {
    openEditReview.setDisable(bool);
    noMoviesWarning.setVisible(bool);
  }
}
