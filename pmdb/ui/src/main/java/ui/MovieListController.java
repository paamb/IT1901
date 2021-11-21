package ui;

import core.IMovie;
import core.MovieList;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * MovieListController class.
 * 
 * 
 */
public class MovieListController {

  private MovieList movieList;

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
  String localMovieListPath;

  @FXML
  String apiBaseUri;

  @FXML
  EditMovieController editMovieController;

  private AppController appController;

  private ReviewListController reviewListController;

  private MovieListAccess access;

  private Runnable initViewRunnable = () -> {
    hideEditMovie();
    displayMovieList();
  };

  @FXML
  void initialize() throws IOException {
    syncWithServer();
    editMovieController.injectMovieListController(this);
  }

  /**
   * Loades MovieList from given file.
   * 
   * @param file the file to load from.
   * @throws IOException when unable to load from file.
   */
  public void loadMovieListFile(File file) throws IOException {
    access = new LocalMovieListAccess(file);
    movieList = access.getMovieList();
    Platform.runLater(initViewRunnable);
  }

  protected void injectReviewListController(ReviewListController reviewListController) {
    this.reviewListController = reviewListController;
  }

  protected void injectAppController(AppController appController) {
    this.appController = appController;
  }

  protected void syncWithServer() {
    syncWithServer(apiBaseUri);
  }

  protected void syncWithServer(String baseUri) {
    try {
      access = new RemoteMovieListAccess(new URI(baseUri));
      movieList = access.getMovieList();
    } catch (Exception e) {
      access = new LocalMovieListAccess(localMovieListPath);
      movieList = access.getMovieList();
    }
    Platform.runLater(initViewRunnable);
  }

  protected void editMovie(IMovie movie) {
    editMovieController.editMovie(movie);
    editMovieController.setMovieDisplayController(null);
    editMovieWindow.setVisible(true);
  }

  protected void editMovie(IMovie movie, MovieDisplayTemplateController controller) {
    editMovie(movie);
    editMovieController.setMovieDisplayController(controller);
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
    return movieList.getSortedMovies(movies, MovieList.sortOnTitleComparator);
  }

  protected Collection<IMovie> getSortedMoviesOnSeen(Collection<IMovie> movies) {
    return movieList.getSortedMovies(movies, MovieList.sortOnSeenComparator);
  }

  protected MovieList getMovieList() {
    return movieList;
  }

  protected void hideEditMovie() {
    editMovieWindow.setVisible(false);
  }

  protected void movieListIsEdited() {
    try {
      access.putMovieList(movieList);
    } catch (Exception e) {
      appController.syncWithServer();
    }
    displayMovieList();
  }

  protected void deleteMovie(IMovie movie) {
    movieList.removeMovie(movie);
    movieListIsEdited();
  }

  protected boolean serverIsRunning() {
    return access instanceof RemoteMovieListAccess;
  }

  @FXML
  private void displayMovieList() {
    clearDeletedMovies();
    try {
      int counter = 0;
      double offsetX = movieDisplay.getPrefWidth() / 2;
      double offsetY = -1.0;

      Collection<IMovie> movies = getMovies();

      if (sortOnTitleCheckbox.isSelected()) {
        movies = getSortedMoviesByTitle(movies);
      }

      if (sortOnSeenCheckbox.isSelected()) {
        movies = getSortedMoviesOnSeen(movies);
      }

      for (IMovie movie : movies) {
        Pane moviePane = findMoviePane(movie);
        if (moviePane == null) {
          FXMLLoader fxmlLoader = 
              new FXMLLoader(this.getClass().getResource("MovieDisplayTemplate.fxml"));
          moviePane = fxmlLoader.load();
          MovieDisplayTemplateController movieDisplayTemplateController =
              fxmlLoader.getController();
          movieDisplayTemplateController.injectMovieListController(this);
          movieDisplayTemplateController.setMovie(movie);
          movieDisplayTemplateController.setContent();
          movieDisplay.getChildren().add(moviePane);
        }
        if (offsetY < 0.0) {
          offsetY = moviePane.getPrefHeight();
        }
        int counterCalc = (int) counter / 2;
        moviePane.setLayoutX(offsetX * (counter % 2));
        moviePane.setLayoutY(offsetY * counterCalc);
        moviePane.setId("M" + String.valueOf(counter));
        counter++;
      }
      int counterCalc = (int) counter / 2;
      movieDisplay.setLayoutY(counterCalc);
      reviewListController.displayReviewList();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void clearDeletedMovies() {
    ObservableList<Node> movieNodes = movieDisplay.getChildren();
    Collection<Node> deletingNodes = new ArrayList<>();
    movieNodes.forEach(movieNode -> {
      if (movieList.getMovie(((Label) movieNode.lookup("#movieTitle")).getText()) == null) {
        deletingNodes.add(movieNode);
      }
    });
    deletingNodes.forEach(node -> movieNodes.remove(node));
  }

  private Pane findMoviePane(IMovie movie) {
    for (Node node : movieDisplay.getChildren()) {
      if (((Label) node.lookup("#movieTitle")).getText().equals(movie.getTitle())) {
        return (Pane) node;
      }
    }
    return null;
  }
}
