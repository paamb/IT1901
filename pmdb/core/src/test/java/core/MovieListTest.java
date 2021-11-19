package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieListTest {

  private MovieList movieList;
  private Movie movie1;
  private Movie movie2;
  private Movie movie3;

  /**
   * Done before each test.
   */
  @BeforeEach
  public void setUp() {
    movieList = new MovieList();
    movie1 = new Movie("tittel", "desc", 182, true, Arrays.asList(), Arrays.asList());
    movie2 = new Movie("tittel", "descasdas", 242, false, Arrays.asList(), Arrays.asList());
    movie3 = new Movie("tittela", "descasdas", 242, false, Arrays.asList(), Arrays.asList());
  }

  @Test
  public void testConstructor() {
    assertEquals(0, movieList.getMovieCount(), "The movies-list should be empty.");
  }

  @Test
  public void testAddMovieValid() {
    movieList.addMovie(movie1);
    movieList.addMovie(movie3);
    assertEquals(2, movieList.getMovieCount(), "List should contain 2 movies");

    assertSame(movie1, movieList.getMovie(movie1.getTitle()),
        "The movies should have same reference.");
    assertSame(movie3, movieList.getMovie(movie3.getTitle()),
        "The movies should have same reference.");
  }

  @Test
  public void testAddMovieUnvalid() {
    movieList.addMovie(movie1);
    assertThrows(IllegalStateException.class, () -> movieList.addMovie(movie2),
        "The movies have the same title.");
    movieList.addMovie(movie3);
    assertEquals(2, movieList.getMovieCount(), "The length of the list should be 2.");
  }

  @Test
  public void testRemoveMovie() {
    movieList.addMovie(movie1);
    movieList.addMovie(movie3);

    movieList.removeMovie(movie3);
    assertEquals(1, movieList.getMovieCount(), "The length of the list should be 2.");
    assertSame(null, movieList.getMovie(movie3.getTitle()), "The movie with title "
        + movie3.getTitle() + " should not be in the list because it should be removed.");
  }

  @Test
  public void testClearMovieList() {
    movieList.addMovie(movie1);
    movieList.addMovie(movie3);

    assertEquals(2, movieList.getMovieCount(), "The length of the list should be 3.");
    movieList.clearMovieList();
    assertEquals(0, movieList.getMovieCount(), "The length of the list should be 0.");
  }

  @Test
  public void testGetMovie_validOption() {
    movieList.addMovie(movie1);
    assertSame(movie1, movieList.getMovie(movie1.getTitle()),
        "The movies should have the same title.");
  }

  @Test
  public void testtestGetMovie_unvalidOption() {
    movieList.addMovie(movie1);
    assertSame(null, movieList.getMovie(movie3.getTitle()),
        "Could not add movie, because a movie with this title is already in the list");
  }

  @Test
  @DisplayName("Tests if addind and removing with iterator is working")
  public void testIterator_AddAndRemove() {
    movieList.addMovie(movie1);
    testIterator_helper(movieList.iterator(), movie1);
    movieList.addMovie(movie3);
    testIterator_helper(movieList.iterator(), movie1, movie3);
    movieList.removeMovie(movie1);
    testIterator_helper(movieList.iterator(), movie3);
    movieList.addMovie(movie1);

    Movie movie4 = new Movie("title", "description", 183, true, Arrays.asList(), Arrays.asList());
    movieList.addMovie(movie4);
    testIterator_helper(movieList.iterator(), movie3, movie1, movie4);
    movieList.removeMovie(movie1);
    testIterator_helper(movieList.iterator(), movie3, movie4);
    movieList.removeMovie(movie4);
    movieList.removeMovie(movie3);
    testIterator_helper(movieList.iterator());
  }

  @Test
  @DisplayName("Helper-metod to check the iterator")
  static void testIterator_helper(Iterator<IMovie> it, IMovie... movies) {
    int movieCounter = 0;
    while (it.hasNext()) {
      assertTrue(movieCounter < movies.length,
          "moviecounter should be less than the length of movies.");
      assertSame(movies[movieCounter], it.next(), "Wrong movie");
      movieCounter++;
    }
    assertTrue(movieCounter == movies.length, "moviecounter should be equal to length of movies.");
  }
}
