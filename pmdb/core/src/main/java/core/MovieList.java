package core;

import core.moviecomparators.MovieSeenComparator;
import core.moviecomparators.MovieTitleComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class MovieList implements Iterable<IMovie> {

  private Collection<IMovie> movieList;

  public MovieList() {
    movieList = new ArrayList<>();
  }

  /**
   * Adds the provided movie.
   * 
   * @param movie the movie to add
   */
  public void addMovie(IMovie movie) {
    if (getMovie(movie.getTitle()) == null) {
      movieList.add(movie);
    } else {
      throw new IllegalStateException("This movie-title is already in use.");
    }
  }

  public void removeMovie(IMovie movie) {
    movieList.remove(movie);
  }

  public Collection<IMovie> getMovies() {
    return new ArrayList<>(movieList);
  }

  public void clearMovieList() {
    movieList.clear();
  }

  /**
   * Finds the movie with the provided title and returns it.
   * 
   * @param title The title of the movie to find
   * @return Movie with matching title, if there is no such movie, return null
   */
  public IMovie getMovie(String title) {
    return movieList.stream().filter(m -> m.getTitle().equals(title)).findFirst().orElse(null);
  }

  /**
   * Sorts movielist by title and returns it.
   * 
   * @param movies the movieList to sort
   * @return returns a collection of movies sorted on title
   */
  public Collection<IMovie> getSortedMoviesByTitle(Collection<IMovie> movies) {
    List<IMovie> sortedMovieList = new ArrayList<>(movies);
    Collections.sort(sortedMovieList, new MovieTitleComparator());
    return sortedMovieList;
  }

  /**
   * Sorts movielist by seen and returns it.
   * 
   * @param movies the movieList to sort
   * @return returns a collection of movies sorted on seen
   */
  public Collection<IMovie> getSortedMoviesOnSeen(Collection<IMovie> movies) {
    List<IMovie> sortedMovieList = new ArrayList<>(movies);
    Collections.sort(sortedMovieList, new MovieSeenComparator());
    return sortedMovieList;
  }

  @Override
  public String toString() {
    String returnString = "";
    for (IMovie movie : movieList) {
      returnString += movie.getTitle() + "\n";
    }
    return returnString;
  }

  @Override
  public Iterator<IMovie> iterator() {
    return movieList.iterator();
  }
}
