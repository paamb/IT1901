package core.moviecomparators;

import core.IMovie;
import java.util.Comparator;


public class MovieSeenComparator implements Comparator<IMovie> {

  @Override
  public int compare(IMovie o1, IMovie o2) {
    return Boolean.compare(o1.isWatched(), o2.isWatched());
  }

}
