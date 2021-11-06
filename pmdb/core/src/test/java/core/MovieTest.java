package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieTest {

  private Movie movie;
  private String initTitle = "initTitle";
  private String initDescription = "initDescription";
  private int initDuration = 360;
  private Collection<IReview> initReview = new ArrayList<IReview>(Arrays.asList());
  private Review review1 = new Review("Bra film", 8, LocalDate.of(2000, 1, 1));
  private Review review2 = new Review("Teit film", 1, LocalDate.of(2001, 2, 2));

  @Test
  public void testConstructor() {
    String title = "Film1";
    String description = "Dette er film nummer 1.";
    int duration = 130;
    Collection<IReview> reviews =
        new ArrayList<IReview>(Arrays.asList(new Review("", 1, LocalDate.now())));
    Movie movie = new Movie(title, description, duration, false, reviews, new ArrayList<>());

    assertEquals(title, movie.getTitle(), "Movie title is not correct.");
    assertEquals(description, movie.getDescription(), "Description is not correct.");
    assertEquals(duration, movie.getDuration(), "Duration is not correct.");
    assertEquals(1, reviews.size(), "This movie should have 1 review");
  }

  @BeforeEach
  public void setUp() {
    movie = new Movie(initTitle, initDescription,
        initDuration, false, initReview, new ArrayList<>());
  }

  @Test
  public void testSetTitle() {
    String legalTitle = "Title1.!?";

    assertEquals(initTitle, movie.getTitle(), "Movie title is not correct.");
    movie.setTitle(legalTitle);
    String illegalTitle1 = "";
    String illegalTitle2 = "ThisTitleIsTooLongBecauseItContainsOver50Characters";
    assertEquals(legalTitle, movie.getTitle(), "Movie title is not correct.");
    assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle1),
        "Did not throw exception on illegal title: " + illegalTitle1);
    assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle2),
        "Did not throw exception on illegal title: " + illegalTitle2);
  }

  @Test
  public void testSetDescription() {
    String newDescription = "newDescription";

    assertEquals(initDescription, movie.getDescription(), "Description is not correct");
    movie.setDescription(newDescription);
    assertEquals(newDescription, movie.getDescription(), "Description is not correct");
  }

  @Test
  public void testSetDuration_valid() {
    int newDuration = 210;
    assertEquals(initDuration, movie.getDuration(), "Duration is not correct");
    movie.setDuration(newDuration);
    assertEquals(newDuration, movie.getDuration(), "Duration is not correct");
  }

  @Test
  public void testSetDuration_unvalidZero() {
    int zeroDuration = 0;
    assertThrows(IllegalArgumentException.class, () -> movie.setDuration(zeroDuration),
        "Movie cannot have duration zero");
    movie.setDuration(20);
    assertEquals(20, movie.getDuration(), "Durations should match.");
  }

  @Test
  public void testSetDuration_unvalidNegative() {
    int negativeDuration = 0;
    assertThrows(IllegalArgumentException.class, () -> movie.setDuration(negativeDuration),
        "Movie cannot have duration zero");
    movie.setDuration(30);
    assertEquals(30, movie.getDuration(), "Durations should match.");
  }

  @Test
  public void testSetDuration_validEdgeCase() {
    int edgeDuration = 1;
    movie.setDuration(edgeDuration);
    assertEquals(edgeDuration, movie.getDuration(), "Durations should match.");
  }

  @Test
  public void testSetWatched() {
    assertEquals(false, movie.isWatched(), "Wacthed is not correct");
    movie.setWatched(true);
    assertEquals(true, movie.isWatched(), "Failed to set watched true");
    movie.setWatched(true);
    assertEquals(true, movie.isWatched(), "Failed on setWatched() when movie is watched");
  }

  @Test
  public void testAddReview() {
    movie.addReview(review1);
    movie.addReview(review2);

    assertEquals(2, movie.getReviews().size(), "This movie should have 2 reviews");
    assertTrue(movie.getReviews().contains(review1), "Movie should contain review1");
    assertTrue(movie.getReviews().contains(review2), "Movie should contain reivew2");
    assertThrows(IllegalStateException.class, () -> movie.addReview(review1),
        "Failed to throw exception on adding duplicate review");
    assertThrows(IllegalArgumentException.class, () -> movie.addReview(null),
        "Failed to throw exception on adding null");
  }

  @Test
  public void testSetReviews() {
    movie.addReview(review1);
    movie.addReview(review2);
    movie.setReviews(Arrays.asList(review1, review2));

    assertEquals(2, movie.getReviews().size(), "This movie should have 2 reviews");
    assertTrue(movie.getReviews().contains(review1), "Movie should contain review1");
    assertTrue(movie.getReviews().contains(review2), "Movie should contain reivew2");

    assertThrows(IllegalArgumentException.class,
        () -> movie.setReviews(Arrays.asList(review1, review1)),
        "Cannot add a list with duplicate reviews");
  }
}
