package core;

import java.util.Iterator;

/**
 * Interface for Label.
 * 
 */
public interface ILabel {

  /**
   * Checks if the label title is valid.
   * 
   * @param title Label title.
   * @return true if title is valid.
   */
  public static boolean isValidTitle(String title) {
    return (title != null) && (title.length() >= 1 && title.length() <= 15);
  }

  /**
   * Checks if the label color is valid. Color needs to start with # followed by 6 hexadecimal
   * numbers.
   * 
   * @param color Color on hexadecimal form
   * @return true if title is valid.
   */
  public static boolean isValidColor(String color) {
    return (color != null) && color.matches("#[0-9a-fA-F]{6}+$");
  }

  /**
   * Checks if label is in movie. Checks by title.
   * 
   * @param movie movie to be checked
   * @param title title to
   * @return Returns true if title matches a label titel in movie.
   */
  public static boolean isLabelInMovie(IMovie movie, String title) {
    for (Iterator<ILabel> labels = movie.labelIterator(); labels.hasNext(); ) {
      if (labels.next().getTitle().equals(title)) {
        return true;
      }
    }
    return false;
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
