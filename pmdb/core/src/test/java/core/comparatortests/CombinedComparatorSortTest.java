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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Important note is that the SeenComparator should trumf the TitleComparator in the running app.
 * The test FirstSortOnTitleThenSeen() is testing how it works in the app.
 */
public class CombinedComparatorSortTest {

  private MovieList movieList;
  private List<IMovie> movies;

  /**
   * Runs setup before each test.
   * 
   * 
   */
  @BeforeEach
  public void setUp() {
    movieList = new MovieList();
    IMovie movie1 = new Movie("aaa", "Komedie", 1, true, Arrays.asList(), Arrays.asList());
    IMovie movie2 = new Movie("aaa", "Action", 2, false, Arrays.asList(), Arrays.asList());
    IMovie movie3 = new Movie("ccc", "Komedie", 3, true, Arrays.asList(), Arrays.asList());
    IMovie movie4 = new Movie("ddd", "Action", 4, false, Arrays.asList(), Arrays.asList());
    IMovie movie5 = new Movie("eee", "Action", 5, false, Arrays.asList(), Arrays.asList());
    IMovie movie6 = new Movie("fff", "Action", 6, true, Arrays.asList(), Arrays.asList());
    movies = new ArrayList<>(Arrays.asList(movie1, movie2, movie5, movie6, movie3, movie4));
  }

  /**
   * First sorted on Seen then on Title. Every movie with lexicograpically lower movieTitle will
   * come first.
   */
  @Test
  public void testFirstSortOnSeenThenTitle() {
    Collection<IMovie> sortedMoviesOnSeen =
        movieList.getSortedMovies(movies, MovieList.sortOnSeenComparator);
    Collection<IMovie> sortedMovies =
        movieList.getSortedMovies(sortedMoviesOnSeen, MovieList.sortOnTitleComparator);
    Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(1), sortedMoviesIterator.next(),
        "First movie should be movie2 because its title = aaa and seen = false");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(0), sortedMoviesIterator.next(),
        "Second movie should be movie1 because its title = aaa and seen = true");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(4), sortedMoviesIterator.next(),
        "Third movie should be movie3 because its title = ccc and seen = true");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(5), sortedMoviesIterator.next(),
        "Fourth movie should be movie4 because its title = ddd and seen = false");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(2), sortedMoviesIterator.next(),
        "Fifth movie should be movie5 because its title = eee and seen = false");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(3), sortedMoviesIterator.next(),
        "Sixth movie should be movie6 because its title = fff and seen = true");

    assertFalse(sortedMoviesIterator.hasNext());
  }

  /**
   * First sorted on Title, then on Seen. Every unseen movie will come before a seen movie
   */
  @Test
  public void testFirstSortOnTitleThenSeen() {
    Collection<IMovie> sortedMoviesOnTitle =
        movieList.getSortedMovies(movies, MovieList.sortOnTitleComparator);
    Collection<IMovie> sortedMovies =
        movieList.getSortedMovies(sortedMoviesOnTitle, MovieList.sortOnSeenComparator);
    Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(1), sortedMoviesIterator.next(),
        "First movie should be movie2 because seen = false and title = aaa");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(5), sortedMoviesIterator.next(),
        "Second movie should be movie4 because seen = false and title = ddd");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(2), sortedMoviesIterator.next(),
        "Third movie should be movie5 because seen = false and title = eee");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(0), sortedMoviesIterator.next(),
        "Fourth movie should be movie1 because seen = true and title = aaa");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(4), sortedMoviesIterator.next(),
        "Fifth movie should be movie3 because seen = true and title = ccc");

    assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
    assertEquals(movies.get(3), sortedMoviesIterator.next(),
        "Sixth movie should be movie2 because seen = true and title = fff");

    assertFalse(sortedMoviesIterator.hasNext(), "The movieIterator should not have more elements");
  }
}
