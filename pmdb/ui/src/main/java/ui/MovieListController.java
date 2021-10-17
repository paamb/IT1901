package ui;

import core.IMovie;
import core.MovieList;
import java.io.IOException;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import json.moviepersistance.MovieStorage;

public class MovieListController {

  private MovieList movieList;
  private MovieStorage storage;

  @FXML
  CheckBox sortOnTitleCheckbox;

  @FXML
  CheckBox sortOnSeenCheckbox;

  @FXML
  Button openEditMovie;

  @FXML
  VBox editMovieWindow;

  @FXML
  Pane movieDisplay;

  @FXML
  EditMovieController editMovieController;

  private ReviewListController reviewListController;

  @FXML
  void initialize() throws IOException {
    storage = new MovieStorage();
    movieList = storage.loadMovieList();
    editMovieController.injectMovieListController(this);
    hideEditMovie();
    displayMovieList();
  }

  protected void injectReviewListController(ReviewListController reviewListController) {
    this.reviewListController = reviewListController;
  }

  protected void editMovie(IMovie movie) {
    editMovieController.editMovie(movie);
    editMovieWindow.setVisible(true);
  }

  @FXML
  private void editNewMovie() {
    editMovie(null);
  }

  protected void addMovie(IMovie movie) {
    movieList.addMovie(movie);
  }

  protected Collection<IMovie> getMovies() {
    return movieList.getMovies();
  }

  protected Collection<IMovie> getSortedMoviesByTitle(Collection<IMovie> movies) {
    return movieList.getSortedMoviesByTitle(movies);
  }

  protected Collection<IMovie> getSortedMoviesOnSeen(Collection<IMovie> movies) {
    return movieList.getSortedMoviesOnSeen(movies);
  }

  protected MovieList getMovieList() {
    return movieList;
  }

  protected void hideEditMovie() {
    editMovieWindow.setVisible(false);
  }

  protected void movieListIsEdited() {
    displayMovieList();
    reviewListController.displayReviewList();
    saveMovieList();
  }

  protected void saveMovieList() {
    try {
      storage.saveMovieList(movieList);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void deleteMovie(IMovie movie) {
    movieList.removeMovie(movie);
    movieListIsEdited();
  }

  @FXML
  private void displayMovieList() {
    movieDisplay.getChildren().clear();
    try {
      double counter = 0;
      double offsetX = movieDisplay.getPrefWidth() / 2;
      double offsetY = ((Pane) new FXMLLoader(this.getClass().getResource("movieDisplayTemplate.fxml")).load())
          .getPrefHeight();

      Collection<IMovie> movies = getMovies();

      if (sortOnTitleCheckbox.isSelected()) {
        movies = getSortedMoviesByTitle(movies);
      }

      if (sortOnSeenCheckbox.isSelected()) {
        movies = getSortedMoviesOnSeen(movies);
      }

      for (IMovie movie : movies) {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("movieDisplayTemplate.fxml"));
        Pane moviePane = fxmlLoader.load();
        moviePane.setLayoutX(offsetX * (counter % 2));
        moviePane.setLayoutY(offsetY * (counter / 2));

        MovieDisplayTemplateController movieDisplayTemplateController = fxmlLoader.getController();
        movieDisplayTemplateController.injectMovieListController(this);
        movieDisplayTemplateController.setMovie(movie);
        movieDisplayTemplateController.setContent();

        movieDisplay.getChildren().add(moviePane);
        counter++;
      }
      movieDisplay.setLayoutY(counter / 2);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
