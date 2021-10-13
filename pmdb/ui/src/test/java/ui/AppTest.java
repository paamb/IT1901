package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppTest extends ApplicationTest {

    private MovieListController movieListController;

    private ReviewListController reviewListController;

    private final File testFile = new File("src\\test\\resources\\ui", "MovieList_test.json");

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        final Parent root = loader.load();
        AppController appController = loader.getController();
        movieListController = appController.getMovieListController();
        reviewListController = appController.getReviewListController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setup(){
        try {
            movieListController.loadMovieListFile(testFile);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testAppController_initial() {
        assertNotNull(movieListController);
        assertNotNull(reviewListController);
    }
}
