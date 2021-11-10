package core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Movie class.
 * 
 * 
 */
public class Movie implements IMovie {

  private String title;
  private String description;
  private int duration;
  private boolean watched;
  private Collection<IReview> reviews;
  private Collection<ILabel> labels;

  /**
   * Movie constructor.
   * 
   * 
   */
  public Movie(String title, String description, int duration, boolean watched,
      Collection<IReview> reviews, Collection<ILabel> labels) {
    setTitle(title);
    setDescription(description);
    setDuration(duration);
    setWatched(watched);
    setReviews(reviews);
    setLabels(labels);
  }

  public Movie(String title, String description, int duration, boolean watched,
      Collection<IReview> reviews) {
    this(title, description, duration, watched, reviews, new ArrayList<>());
  }

  public void setWatched(boolean watched) {
    this.watched = watched;
  }

  public boolean isWatched() {
    return watched;
  }

  /**
   * Sets the description of the movie.
   * 
   * @param description The description of the movie
   */
  public void setDescription(String description) {
    if (!IMovie.isValidDescription(description)) {
      throw new IllegalArgumentException("Illegal movie description");
    }
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Sets the title of the movie.
   * 
   * @param title The title of the movie
   */
  public void setTitle(String title) {
    if (!IMovie.isValidTitle(title)) {
      throw new IllegalArgumentException("Illegal movie title");
    }
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  /**
   * Sets the duration of the movie.
   * 
   * @param duration The duration of the movie
   */
  public void setDuration(int duration) {
    if (!IMovie.isValidDuration(duration)) {
      throw new IllegalArgumentException("Illegal movie duration");
    }
    this.duration = duration;
  }

  public int getDuration() {
    return duration;
  }

  /**
   * Sets the reviews for the movie.
   * 
   * @param reviews Collection of reviews
   */
  public void setReviews(Collection<IReview> reviews) {
    for (IReview review : reviews) {
      int count = 0;
      for (IReview review2 : reviews) {
        if (review == review2) {
          count++;
        }
        if (count > 1) {
          throw new IllegalArgumentException("Duplicate reviews not allowed");
        }
      }
    }
    this.reviews = new ArrayList<>(reviews);
  }

  /**
   * Adds a review to the movie.
   * 
   * @param review The review to be added
   */
  public void addReview(IReview review) {
    if (review == null) {
      throw new IllegalArgumentException("Review cannot be null");
    }
    if (reviews.contains(review)) {
      throw new IllegalStateException("Duplicate review not allowed");
    }
    reviews.add(review);
  }

  public void removeReview(IReview review) {
    reviews.remove(review);
  }

  public Collection<IReview> getReviews() {
    return new ArrayList<>(reviews);
  }

  public Collection<ILabel> getLabels() {
    return new ArrayList<>(labels);
  }

  /**
   * Adds a label to the movie.
   * 
   * @param label The label to be added.
   */
  public void addLabel(ILabel label) {
    if (label == null) {
      throw new IllegalArgumentException("Label cannot be null");
    }
    if (labels.contains(label)) {
      throw new IllegalStateException("Duplicate labels not allowed");
    }
    labels.add(label);
  }

  public void setLabels(Collection<ILabel> labels) {
    this.labels = new ArrayList<>(labels);
  }

  public void removeLabel(ILabel label) {
    labels.remove(label);

  }

  @Override
  public String toString() {
    String s = "Movie: " + getTitle() + "\n" + "Description: " + getDescription() + "\n"
        + "Duration: " + getDuration() + "\n" + "Watched: " + (isWatched() ? "Yes" : "No");

    if (reviews.size() == 0) {
      return s;
    } else {
      s += "\nRating from reviews: ";
      for (IReview review : reviews) {
        s += review.getRating() + ", ";
      }
    }

    return s.substring(0, s.length() - 2);
  }


}
