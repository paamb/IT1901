package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LabelTest {

  private ILabel label;
  private String title;
  private String color;

  /**
   * Runs setup before each test.
   * 
   * 
   */
  @BeforeEach
  public void setUp() {
    title = "Initial verdi";
    color = "#FFFFFF";
    label = new Label(title, color);
  }

  @Test
  @DisplayName("Testing the constructor.")
  public void testConstructorTitelAndColor() {
    assertEquals("Initial verdi", label.getTitle(), "The titles do not match");
    assertEquals("#FFFFFF", label.getColor(), "The colors do not match");
  }

  @Test
  @DisplayName("Testing the constructor. Random color")
  public void testConstructorOnlyTitle() {
    ILabel randomColorLabel = new Label("RandomColor");
    assertEquals("RandomColor", randomColorLabel.getTitle(), "The titles does not match");

    // Tests if the random color is valid
    assertTrue(ILabel.isValidColor(randomColorLabel.getColor()),
        "The hexadecimal color is not on the right form");
  }

  @Test
  @DisplayName("Testing setting valid title")
  public void testSetTitle_validTitle() {
    label.setTitle("Ny tittel");
    assertEquals("Ny tittel", label.getTitle(), "The new title was not set correctly");
  }

  @Test
  @DisplayName("Testing setting invalid title")
  public void testSetTitle_invalidTitle() {
    assertThrows(IllegalArgumentException.class, () -> label.setTitle("ThisIsOver15Characters"),
        "Title cannot have over 15 characters");
  }

  @Test
  @DisplayName("Test if title can be null")
  public void testSetTitle_null() {
    assertThrows(IllegalArgumentException.class, () -> label.setTitle(null),
        "Title cannot be null");
  }

  @Test
  @DisplayName("Test set valid color")
  public void testSetColor_validColor() {
    label.setColor("#123ABC");
    assertEquals("#123ABC", label.getColor(), "The new color does not match color");
  }

  @Test
  @DisplayName("Test set color with invalid length")
  public void testSetColor_invalidLength() {
    assertThrows(IllegalArgumentException.class, () -> label.setColor("#123ABCD"),
        "Color should be invalid because it contains 7 characters after #");
  }

  @Test
  @DisplayName("Test set color with invalid character")
  public void testSetColor_invalidCharacter() {
    assertThrows(IllegalArgumentException.class, () -> label.setColor("#123A?C"),
        "Color should be invalid, because it contains invalid letter");
  }

  @Test
  @DisplayName("Test set color without hashtag")
  public void testSetColor_withOutHashtag() {
    assertThrows(IllegalArgumentException.class, () -> label.setColor("123ABC"),
        "Color should be invalid, because it does not contain #");
  }
}

