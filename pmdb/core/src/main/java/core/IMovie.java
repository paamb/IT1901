package core;

import java.util.Collection;
import java.util.Iterator;

/**
 * Interface for movie.
 * 
 */
public interface IMovie {

  /**
   * Checks if the movie title is valid.
   * 
   * @param title Movie title
   * @return true if title is valid
   */
  public static boolean isValidTitle(String title) {
    return (title != null) && (title.length() >= 1 && title.length() <= 50);
  }

  /**
   * Checks if the description is valid.
   * 
   * @param description Movie description
   * @return true if description is valid
   */
  public static boolean isValidDescription(String description) {
    return description != null;
  }

  /**
   * Checks if the movie duration is valid.
   * 
   * @param duration Movie duration
   * @return true if duration is valid
   */
  public static boolean isValidDuration(int duration) {
    return duration > 0;
  }

  /**
   * Sets if movie is watched or not.
   * 
   * @param watched Sets if the movie is watched or not
   */
  public void setWatched(boolean watched);

  /**
   * Whether movie is watched or not.
   * 
   * @return whether movie is watched or not
   */
  public boolean isWatched();

  /**
   * Sets this movies description.
   * 
   * @param description Movie description
   */
  public void setDescription(String description);

  /**
   * Gets this movies description.
   * 
   * @return this movies description
   */
  public String getDescription();

  /**
   * Sets this movies title.
   * 
   * @param title Movie title
   * @throws IllegalArgumentException if illegal title
   */
  public void setTitle(String title) throws IllegalArgumentException;

  /**
   * Get the title of this movie.
   * 
   * @return this movies title
   */
  public String getTitle();

  /**
   * sets this movies duration.
   * 
   * @param duration Movie duration
   */
  public void setDuration(int duration);

  /**
   * Get the duration of this movie.
   * 
   * @return this movies duration
   */
  public int getDuration();

  /**
   * Sets a Collection of reviews.
   * 
   * @param reviews Collection of reviews
   */
  public void setReviews(Collection<IReview> reviews);

  /**
   * Adds a review to this movie.
   * 
   * @param review A single review
   */
  public void addReview(IReview review);

  /**
   * Removes a review from this movie.
   * 
   * @param review A single review
   */
  public void removeReview(IReview review);

  /**
   * Returns iterator over reviews.
   * 
   * @return iterator
   */
  public Iterator<IReview> reviewIterator();

  /**
   * Returns reviewlistsize.
   * 
   * @return size
   */
  public int getReviewCount();

  /**
   * Set labels for this movie.
   * 
   * @param labels Collection of labels to be set.
   */
  public void setLabels(Collection<ILabel> labels);

  /**
   * Adds a label to this movie.
   * 
   * @param label to be added to the movie.
   */
  public void addLabel(ILabel label);

  /**
   * Removes a label from this movie.
   * 
   * @param label to be removed from the movie.
   */
  public void removeLabel(ILabel label);

  /**
   * Returns iterator over labels.
   * 
   * @return iterator
   */
  public Iterator<ILabel> labelIterator();

  /**
   * returns size of labels.
   * 
   * @return size
   */
  public int getLabelCount();
}
