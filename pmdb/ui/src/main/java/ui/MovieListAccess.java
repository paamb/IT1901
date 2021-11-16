package ui;

import core.MovieList;

/**
 * Interface for access used in MovieListController.
 */
public interface MovieListAccess {

  /**
   * Fetches and returns movieList.
   * 
   * @return retrieved MovieList;
   */
  MovieList getMovieList();

  /**
   * Saves given movieList.
   * 
   * @param movieList the movieList to be saved
   * @return if saving was successful
   */
  boolean putMovieList(MovieList movieList);
  
}
