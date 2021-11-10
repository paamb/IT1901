package core;

/**
 * Interface for Label.
 * 
 */
public interface ILabel {

  /**
   * Checks if label is in movie. Checks by title.
   * 
   * @param movie movie to be checked
   * @param title title to
   * @return Returns true if title matches a label titel in movie.
   */
  public static boolean isLabelInMovie(IMovie movie, String title) {
    return movie.getLabels().stream().anyMatch(label -> label.getTitle().equals(title));
  }

  /**
   * Sets this label title.
   * 
   * @param title Movie title
   * @throws IllegalArgumentException if illegal title
   */
  public void setTitle(String title);

  /**
   * Get the title of this label.
   * 
   * @return this label title
   */
  public String getTitle();

  /**
   * Sets the color of this label. Can be a random value or a color set by user.
   * 
   * @param color the color for this label
   */
  public void setColor(String color);

  /**
   * Get the color of this label.
   * 
   * @return this label color
   */
  public String getColor();

}
