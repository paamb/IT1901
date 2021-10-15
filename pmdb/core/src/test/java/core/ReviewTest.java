package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReviewTest {

  private Review review;
  private String comment;
  private int rating;
  private LocalDate localDate;

  /**
   * Runs setup before each test.
   * 
   * 
   */
  @BeforeEach
  public void setUp() {
    localDate = LocalDate.of(2002, 4, 17);
    comment = "Inital verdi";
    rating = 2;
    review = new Review(comment, rating, localDate);
  }

  @Test
  @DisplayName("Testing the constructor.")
  public void testConstructor() {
    assertEquals("Inital verdi", review.getComment(), "The comments do not match.");
    assertEquals("2002-04-17", review.getWhenWatched().toString(), "The dates do not match.");
    assertTrue(review.getRating() == 2, "The ratings do not match.");
  }

  @Test
  @DisplayName("Testing setComment when comment is valid, edge ex.")
  public void testSetComment_valid() {
    String str = "Hei dette er en ny k";
    String newCommentEdge = str.repeat(24) + "Hei dette er en ny k";
    assertEquals(500, newCommentEdge.length());
    review.setComment(newCommentEdge);
    assertEquals(newCommentEdge, review.getComment(), "The new comment does not match comment.");
  }

  @Test
  @DisplayName("Testing setting an empty comment.")
  public void testestSetComment_emptyComment() {
    review.setComment("");
    assertEquals("", review.getComment(), "The new comment does not match comment.");
    review.setComment("Hei");
    assertEquals("Hei", review.getComment(), "The comments do not match.");
  }

  @Test
  @DisplayName("Testing if comment can be null")
  public void testSetComment_null() {
    assertThrows(IllegalArgumentException.class, () -> review.setComment(null),
        "A comment cannot be null.");
    review.setComment("New comment12345.");
    assertEquals("New comment12345.", review.getComment(), "The comments do not match.");
  }

  @Test
  @DisplayName("Testing if the rating is less than 1, invalid")
  public void testSetRating_lessThanMin() {
    assertThrows(IllegalArgumentException.class, () -> review.setRating(0),
        "The rating is less than 1.");
    review.setRating(4);
    assertTrue(review.getRating() == 4);
  }

  @Test
  @DisplayName("Testing if the rating is more than 10, invalid")
  public void testSetRating_moreThanMax() {
    assertThrows(IllegalArgumentException.class, () -> review.setRating(11),
        "The rating is more than 10.");
    review.setRating(6);
    assertTrue(review.getRating() == 6);
  }

  @Test
  @DisplayName("Testing valid rating values, 1 to 10")
  public void testSetRating_valid() {
    review.setRating(10);
    assertTrue(review.getRating() == 10);

    review.setRating(1);
    assertTrue(review.getRating() == 1);
  }

  @Test
  @DisplayName("Testing valid values for when movie was watched, the day before today")
  public void testSetWhenWatched() {
    int dayToday = LocalDate.now().getDayOfMonth();
    int month = LocalDate.now().getMonthValue();
    int year = LocalDate.now().getYear();

    review.setWhenWatched(LocalDate.of(year, month, dayToday));
    assertEquals(LocalDate.now(), review.getWhenWatched(), "The dates do not match.");

    LocalDate newDate = LocalDate.of(0, 12, 30);
    review.setWhenWatched(newDate);
    assertEquals(newDate, review.getWhenWatched(), "The dates do not match.");
  }

  @Test
  @DisplayName("Testing invalid values for when movie was watched, in the future")
  public void testSetWhenWatched_invalid() {
    int dayAfterNow = LocalDate.now().getDayOfMonth() + 1;
    int month = LocalDate.now().getMonthValue();
    int year = LocalDate.now().getYear();

    assertThrows(IllegalArgumentException.class,
        () -> review.setWhenWatched(LocalDate.of(year, month, dayAfterNow)),
        "You can not set whenWatched in the future.");
    review.setWhenWatched(LocalDate.of(year, month, dayAfterNow - 1));
    assertEquals(LocalDate.now().toString(), review.getWhenWatched().toString(),
        "The dates do not match.");
  }

  @Test
  @DisplayName("Testing if whenWatched can be null")
  public void testSetWhenWatched_null() {
    assertThrows(IllegalArgumentException.class, () -> review.setWhenWatched(null),
        "whenWatched cannot be null.");
    review.setWhenWatched(LocalDate.of(2021, 1, 1));
    assertEquals("2021-01-01", review.getWhenWatched().toString(), "The dates do not match.");
  }
}
