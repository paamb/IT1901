package core.moviecomparators;

import java.util.Comparator;

import core.IMovie;

public class MovieTitleComparator implements Comparator<IMovie>{

    @Override
    public int compare(IMovie o1, IMovie o2) {
        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
    }
}