package core.moviecomparators;

import core.IMovie;

import java.io.Serializable;
import java.util.Comparator;

public class MovieSeenComparator implements Comparator<IMovie>, Serializable {

  @Override
  public int compare(IMovie o1, IMovie o2) {
    return Boolean.compare(o1.isWatched(), o2.isWatched());
  }

}
