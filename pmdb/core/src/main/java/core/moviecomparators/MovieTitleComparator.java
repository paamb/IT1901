package core.moviecomparators;

import core.IMovie;
import java.io.Serializable;
import java.util.Comparator;

public class MovieTitleComparator implements Comparator<IMovie>, Serializable {

  @Override
  public int compare(IMovie o1, IMovie o2) {
    return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
  }
}
