package ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
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

import core.IMovie;

public class MovieListTest extends ApplicationTest{
    
    private MovieListController movieListController;

    // private ReviewListController reviewListController;

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        final Parent root = loader.load();
        AppController appController = loader.getController();
        movieListController = appController.getMovieListController();
        // reviewListController = appController.getReviewListController();
        stage.setScene(new Scene(root));
        stage.show();

        // final FXMLLoader movieListLoader = new FXMLLoader(getClass().getResource("MovieList_test.fxml"));
        // movieListController = movieListLoader.getController();
        
        // final FXMLLoader reviewListLoader = new FXMLLoader(getClass().getResource("ReviewList_test.fxml"));
        // reviewListController = reviewListLoader.getController();
    }

    @BeforeEach
    public void setup(){
        clickOn("#movieListTab");
    }

    @Test
    public void test_initialize(){
        assertNotNull(movieListController);
    }

    @Test
    public void testOpenEditMovie(){
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    }
    
    @Test
    public void testCloseEditMovie(){
        clickOn("#openEditMovie");
        assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
        clickOn("#cancelButton");
        assertFalse(movieListController.editMovieWindow.isVisible(), "EditMovie-window should not be visible.");
    }
    
    @Test
    public void testAddMovie_valid(){
        String title = "title";
        String description = "This is a movie";
        LocalTime duration = LocalTime.of(2, 30);
        boolean watched = true;

        clickOn("#openEditMovie");
        clickOn("#titleField").write(title);
        clickOn("#descriptionField").write(description);
        clickOn("#hoursField").write(String.valueOf(duration.getHour()));
        clickOn("#minutesField").write(String.valueOf(duration.getMinute()));
        if(watched){clickOn("#watchedCheckBox");}
        clickOn("#addTheMovie");

        assertEquals(1, movieListController.getMovieList().getMovies().size());

        IMovie movie = movieListController.getMovies().stream().findFirst().get();
        
        assertEquals(title, movie.getTitle());
        assertEquals(description, movie.getDescription());
        assertEquals(duration, movie.getDuration());
        assertEquals(watched, movie.isWatched());
    }
}
