package ui;

import core.ILabel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class LabelController {

  @FXML
  Button removeLabel;

  private Pane selfPane;

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

  public void setPane(Pane pane) {
    selfPane = pane;
  }

  public Pane getPane() {
    return selfPane;
  }

  @FXML
  private void removeLabel() {
    System.out.println("removing");
    editMovieController.removeLabel(this);
  }
  
}
