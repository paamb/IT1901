package ui;

import java.util.concurrent.TimeUnit;
import javafx.scene.Node;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

abstract class AbstractNodeFinderTest extends ApplicationTest {
  
  protected Node waitForNode(String id) {
    WaitForAsyncUtils.waitForFxEvents();
    Node[] nodes = new Node[1];
    try {
      WaitForAsyncUtils.waitFor(2000, TimeUnit.MILLISECONDS,
          () -> {
            int counter = 0;
            while (true && counter <= 20) {
              try {
                nodes[0] = lookup(id).queryAll().stream().findFirst().get();
                if (nodes[0] != null) {
  
                  return true;
                }
              } catch (Exception e){
                // Try again
              }
              counter++;
              Thread.sleep(100);
            }
            return false;
          }
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
    return nodes[0];
  }

}
