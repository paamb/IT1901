package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
  private Collection<IReview> initReview = Arrays.asList();
  private Review review1 = new Review("Bra film", 8, LocalDate.of(2000, 1, 1));

  @Test
  public void testConstructor() {
    String title = "Film1";
    String description = "Dette er film nummer 1.";
    int duration = 130;
    Collection<IReview> reviews =
        new ArrayList<IReview>(Arrays.asList(new Review("", 1, LocalDate.now())));
    Movie movie = new Movie(title, description, duration, false, reviews, Arrays.asList());

    assertEquals(title, movie.getTitle(), "Movie title is not correct.");
    assertEquals(description, movie.getDescription(), "Description is not correct.");
    assertEquals(duration, movie.getDuration(), "Duration is not correct.");
    assertEquals(1, reviews.size(), "This movie should have 1 review");
  }

  @BeforeEach
  public void setUp() {
    movie = new Movie(initTitle, initDescription, initDuration, false, initReview, Arrays.asList());
  }

  @Test
  public void testSetTitle() {
    String legalTitle = "Title1.!?";

    movie.setTitle(legalTitle);
    assertEquals(legalTitle, movie.getTitle(), "Title should be set, because it is legal.");

    String illegalTitle1 = "";
    String illegalTitle2 = "ThisTitleIsTooLongBecauseItContainsOver50Characters";
    assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle1),
        "Did not throw exception on illegal title: " + illegalTitle1);
    assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle2),
        "Did not throw exception on illegal title: " + illegalTitle2);
  }

  @Test
  public void testSetDescription_unvalidNull() {
    assertThrows(IllegalArgumentException.class, () -> movie.setDescription(null),
        "Failed to throw exception when setting description null");
  }

  @Test
  public void testSetDuration_validEdgeCase() {
    int edgeDuration = 1;
    movie.setDuration(edgeDuration);
    assertEquals(edgeDuration, movie.getDuration(), "Durations should match.");
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
    int negativeDuration = -10;
    assertThrows(IllegalArgumentException.class, () -> movie.setDuration(negativeDuration),
        "Movie cannot have negative duration");
    movie.setDuration(30);
    assertEquals(30, movie.getDuration(), "Durations should match.");
  }

  @Test
  public void testAddReview_addingDuplicateReview() {
    movie.addReview(review1);
    assertEquals(1, movie.getReviewCount(), "This movie should have 1 review");

    assertThrows(IllegalStateException.class, () -> movie.addReview(review1),
        "Failed to throw exception on adding duplicate review");
    assertEquals(1, movie.getReviewCount(), "This movie should have 1 review");
  }

  @Test
  public void testAddReview_addingNull() {
    movie.addReview(review1);
    assertEquals(1, movie.getReviewCount(), "This movie should have 1 review");

    assertThrows(IllegalArgumentException.class, () -> movie.addReview(null),
        "Failed to throw exception on adding null");
    assertEquals(1, movie.getReviewCount(), "This movie should have 1 review");
  }

  @Test
  public void testSetReviews_duplicateReviews() {
    assertThrows(IllegalArgumentException.class,
        () -> movie.setReviews(Arrays.asList(review1, review1)),
        "Cannot add a list with duplicate reviews");
  }

  @Test
  public void testAddLabel_duplicateLabel() {
    Label labelOne = new Label("labelOne");

    movie.addLabel(labelOne);
    assertThrows(IllegalStateException.class, () -> movie.addLabel(labelOne),
        "Failed to throw exception on adding duplicate label");

    assertEquals(1, movie.getLabelCount(),
        "Labels should have size 1, because the illegal value was not added");
  }

  @Test
  public void testAddLabel_nullLabel() {
    assertThrows(IllegalArgumentException.class, () -> movie.addLabel(null),
        "Failed to throw exception on adding null as a label");

    assertEquals(0, movie.getLabelCount(),
        "Labels should have size 0, because we have only added an illegal value");
  }

  @Test
  public void testSetLabel_duplicateLabel() {
    Label labelOne = new Label("labelOne");

    assertThrows(IllegalStateException.class,
        () -> movie.setLabels(Arrays.asList(labelOne, labelOne)),
        "Failed to throw exception on setting duplicate labels");

    assertEquals(0, movie.getLabelCount(),
        "Labels should have size 0, because we have only added an illegal value");
  }
}
