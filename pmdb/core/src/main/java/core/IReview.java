package core;

import java.time.LocalDate;

public interface IReview {

  /**
   * 
   * @param comment
   * @return true if comment is valid
   */
  public static boolean isValidComment(String comment) {
    return comment != null;
  }

  /**
   * 
   * @param rating
   * @return true if rating is valid
   */
  public static boolean isValidRating(int rating) {
    return (rating <= 10) && (rating >= 1);
  }

  /**
   * 
   * @param whenWatched
   * @return true if whenWatched is valid
   */
  public static boolean isValidWhenWatched(LocalDate whenWatched) {
    return (whenWatched != null) && (!whenWatched.isAfter(LocalDate.now()));
  }

  /**
   * 
   * @return This reviews comment
   */
  public String getComment();

  /**
   * Sets this reviews comment
   * 
   * @param comment
   * 
   * @throws IllegalArgumentException when comment is too long
   */
  public void setComment(String comment) throws IllegalArgumentException;

  /**
   * 
   * @return returns the rating of this reviews movie
   */
  public int getRating();

  /**
   * Sets this movies rating
   * 
   * @param rating
   * @throws IllegalArgumentException when rating is smaller lower than 1 or higher than 10
   */
  public void setRating(int rating) throws IllegalArgumentException;

  /**
   * 
   * @return returns when the movie was watched
   */
  public LocalDate getWhenWatched();

  /**
   * Sets when this movie was watched
   * 
   * @param whenWatched
   * @throws IllegalStateException if the argument is in the future
   */
  public void setWhenWatched(LocalDate whenWatched) throws IllegalStateException;

  /**
   * 
   * @return returns a stringified version of object
   */
  public String toString();
}
