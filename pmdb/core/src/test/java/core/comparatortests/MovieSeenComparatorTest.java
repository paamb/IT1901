package core.comparatortests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.IMovie;
import core.Movie;
import core.MovieList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class MovieSeenComparatorTest {

  @Test
  public void testMovieSeenSort() {
    MovieList movieList = new MovieList();
    IMovie movie1 = new Movie("aaa", "Komedie", 1, false, Arrays.asList(), Arrays.asList());
    IMovie movie2 = new Movie("ddd", "Action", 2, false, Arrays.asList(), Arrays.asList());
    IMovie movie3 = new Movie("aaa", "Komedie", 3, true, Arrays.asList(), Arrays.asList());
    IMovie movie4 = new Movie("ccc", "Action", 4, true, Arrays.asList(), Arrays.asList());

    Collection<IMovie> movies =
        new ArrayList<IMovie>(Arrays.asList(movie2, movie3, movie4, movie1));
    Collection<IMovie> sortedMovies =
        movieList.getSortedMovies(movies, MovieList.sortOnSeenComparator);
    Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movie2, sortedMoviesIterator.next(),
        "First movie should be movie2 because seen = false");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movie1, sortedMoviesIterator.next(),
        "Second movie should be movie1 because seen = false");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movie3, sortedMoviesIterator.next(),
        "Third movie should be movie3 because seen = true");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movie4, sortedMoviesIterator.next(),
        "Fourth movie should be movie4 because seen = true");

    assertFalse(sortedMoviesIterator.hasNext(), "The movieIterator should not have more elements");

  }
}
