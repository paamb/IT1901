package ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class MovieListTest extends ApplicationTest{
    
    private MovieListController movieListController;

    // private ReviewListController reviewListController;

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieList_test.fxml"));
        final Parent root = loader.load();
        movieListController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();

        // final FXMLLoader movieListLoader = new FXMLLoader(getClass().getResource("MovieList_test.fxml"));
        // movieListController = movieListLoader.getController();
        
        // final FXMLLoader reviewListLoader = new FXMLLoader(getClass().getResource("ReviewList_test.fxml"));
        // reviewListController = reviewListLoader.getController();
    }

    @Test
    public void test_initialize(){
        assertNotNull(movieListController);
    }

    @Test
    public void testOpenEditMovie(){
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible());
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible());
    }
    
    @Test
    public void testCloseEditMovie(){
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible());
        clickOn("#cancelButton");
        assertFalse(movieListController.editMovieWindow.isVisible());
    }
}
