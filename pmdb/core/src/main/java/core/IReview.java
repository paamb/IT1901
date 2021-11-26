package core;

import java.time.LocalDate;

/**
 * IReview interface.
 * 
 * 
 */
public interface IReview {

  /**
   * String comment to be added to review.
   * 
   * @param comment Review comment
   * @return true if comment is valid
   */
  public static boolean isValidComment(String comment) {
    return comment != null;
  }

  /**
   * Rating for movie.
   * 
   * @param rating movie rating
   * @return true if rating is valid
   */
  public static boolean isValidRating(int rating) {
    return (rating <= 10) && (rating >= 1);
  }

  /**
   * Checks if whenWatched date is valid.
   * 
   * @param whenWatched Date when movie was watched
   * @return true if whenWatched is valid
   */
  public static boolean isValidWhenWatched(LocalDate whenWatched) {
    return (whenWatched != null) && (!whenWatched.isAfter(LocalDate.now()));
  }

  /**
   * Get comment for this review.
   * 
   * @return This reviews comment
   */
  public String getComment();

  /**
   * Sets this reviews comment.
   * 
   * @param comment comment to be set.
   * @throws IllegalArgumentException when comment is too long
   */
  public void setComment(String comment) throws IllegalArgumentException;

  /**
   * Get the rating for the movie.
   * 
   * @return returns the rating of this reviews movie
   */
  public int getRating();

  /**
   * Sets this movies rating.
   * 
   * @param rating Rating for this movie
   * @throws IllegalArgumentException when rating is smaller lower than 1 or higher than 10
   */
  public void setRating(int rating) throws IllegalArgumentException;

  /**
   * Get the date for when the movie was watched.
   * 
   * @return returns when the movie was watched
   */
  public LocalDate getWhenWatched();

  /**
   * Sets when this movie was watched.
   * 
   * @param whenWatched movie watched date
   * @throws IllegalStateException if the argument is in the future
   */
  public void setWhenWatched(LocalDate whenWatched) throws IllegalStateException;

  /**
   * Returns a stringified version of object.
   * 
   * @return returns a stringified version of object
   */
  public String toString();
}
