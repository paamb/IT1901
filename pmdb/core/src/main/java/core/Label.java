package core;

import java.util.Random;

/**
 * Label class.
 */
public class Label implements ILabel {
  private String title;
  private String color;
  private Random rnd = new Random();

  public Label(String title) {
    setTitle(title);
    setColor(getRandomColor());
  }

  public Label(String title, String color) {
    setTitle(title);
    setColor(color);
  }

  /**
   * Generates a random color.
   * 
   * @return A random hexcolor #FFFFFF
   */
  public String getRandomColor() {
    String hexValues = "0123456789ABCEDF";
    StringBuilder colorValue = new StringBuilder("#");

    for (int i = 0; i < 6; i++) {
      int randomValue = rnd.nextInt(16);
      char randomHexValue = hexValues.charAt(randomValue);
      colorValue.append(randomHexValue);
    }

    return colorValue.toString();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  @Override
  public String toString() {
    return "Title: " + getTitle() + "\n" + "Color: " + getColor();
  }
}

