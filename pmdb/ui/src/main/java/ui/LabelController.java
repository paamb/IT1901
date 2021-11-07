package ui;

import core.ILabel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class LabelController {

  @FXML
  Button removeLabel;

  @FXML
  Pane self;

  private ILabel label;

  private EditMovieController editMovieController;

  public void injectEditMovieController(EditMovieController controller) {
    editMovieController = controller;
  }

  public void setLabel(ILabel label) {
    this.label = label;
  }

  public ILabel getLabel() {
    return label;
  }

  @FXML
  private void removeLabel() {
    editMovieController.removeLabel(self);
  }
  
}
