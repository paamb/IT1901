package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * MovieList class.
 * 
 * 
 */
public class MovieList implements Iterable<IMovie> {

  private Collection<IMovie> movieList;

  public static final Comparator<IMovie> sortOnSeenComparator =
      (IMovie o1, IMovie o2) -> Boolean.compare(o1.isWatched(), o2.isWatched());

  public static final Comparator<IMovie> sortOnTitleComparator =
      (IMovie o1, IMovie o2) -> o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());

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

  public Iterator<IMovie> movieIterator() {
    return movieList.iterator();
  }

  public int getMovieCount() {
    return movieList.size();
  }

  public Collection<IMovie> getMovies() {
    return Collections.unmodifiableCollection(movieList);
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
   * Loops through movies and finds all labels.
   * 
   * @return all labels in movieList.
   */
  public Collection<ILabel> getAllLabels() {
    Collection<ILabel> allLabels = new ArrayList<>();
    movieList.stream().forEach(m -> m.labelIterator().forEachRemaining(l -> {
      if (!allLabels.contains(l)) {
        allLabels.add(l);
      }
    }));
    return allLabels;
  }

  /**
   * Sorts movielist by title and returns it.
   * 
   * @param movies the movieList to sort
   * @return returns a collection of movies sorted on title
   */
  public Collection<IMovie> getSortedMovies(Collection<IMovie> movies, Comparator<IMovie> cmp) {
    List<IMovie> sortedMovieList = new ArrayList<>(movies);
    Collections.sort(sortedMovieList, cmp);
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
