package ui;

import core.MovieList;
import java.io.File;
import json.moviepersistance.MovieStorage;

public class LocalMovieListAccess implements MovieListAccess {

  private MovieStorage movieStorage;

  public LocalMovieListAccess(File movieListFile) {
    movieStorage = new MovieStorage();
    movieStorage.setFile(movieListFile);
  }

  public LocalMovieListAccess() {
    movieStorage = new MovieStorage();
  }

  /**
   * Retrieves MovieList from given file.
   * 
   * @return MovieList from file
   */
  public MovieList getMovieList() {
    try {
      return movieStorage.loadMovieList();
    } catch (Exception e) {
      throw new RuntimeException("Could not load MovieList from local file: " + e);
    }
  }

  /**
   * Saves MovieList to given file.
   * 
   * @return if saving was successfull.
   */
  public boolean putMovieList(MovieList movieList) {
    try {
      movieStorage.saveMovieList(movieList);
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Could not save MovieList to local file: " + e);
    }
  }
  
}
