package ui;

import core.ILabel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * Controller for Labels in UI.
 */
public class LabelController {

  @FXML
  Button removeLabel;

  @FXML
  Label labelName;

  private Pane selfPane;

  private ILabel label;

  private EditMovieController editMovieController;

  public void injectEditMovieController(EditMovieController controller) {
    editMovieController = controller;
  }

  /**
   * Sets label and displays labelname and -color.
   * 
   * @param label the label to be set.
   */
  public void setLabel(ILabel label) {
    this.label = label;
    labelName.setText(label.getTitle());
    labelName.setTextFill(Paint.valueOf(label.getColor()));
  }

  public ILabel getLabel() {
    return label;
  }

  public void setPane(Pane pane) {
    selfPane = pane;
  }

  public Pane getPane() {
    return selfPane;
  }

  @FXML
  private void removeLabel() {
    editMovieController.removeLabel(this);
  }
  
}
