package ui;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import javafx.scene.Node;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

class NodeFinderHelper extends ApplicationTest {
  
  //Methods cannot be static because of references to non-static methods
  public Node waitForNode(String id) {
    WaitForAsyncUtils.waitForFxEvents();
    Node[] nodes = new Node[1];
    try {
      WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS, () -> {
        int counter = 0;
        while (true && counter <= 20) {
          try {
            nodes[0] = lookup(id).queryAll().stream().findFirst().get();
            if (nodes[0] != null) {

              return true;
            }
          } catch (Exception e) {
            // Try again
          }
          counter++;
          Thread.sleep(100);
        }
        return false;
      });
    } catch (Exception e) {
      // Time ran out
    }
    return nodes[0];
  }

  public void waitThenWrite(String text) {
    WaitForAsyncUtils.waitForFxEvents();
    try {
      WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS, () -> {
        int counter = 0;
        while (true && counter <= 20) {
          try {
            write(text);
            return true;
          } catch (NoSuchElementException e) {
            // Try again
          }
          counter++;
          Thread.sleep(100);
        }
        return false;
      });
    } catch (Exception e) {
      // Time ran out
    }
  }
}
