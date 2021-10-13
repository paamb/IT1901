package ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import core.IMovie;
import core.MovieList;

public class MovieListTest extends ApplicationTest{
    
    private MovieListController movieListController;

    private EditMovieController editMovieController;

    private final File testFile = new File("src\\test\\resources\\ui", "MovieList_test.json");

    String title = "title";
    String description = "description";
    LocalTime duration = LocalTime.of(2, 30);
    boolean watched = false;

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        final Parent root = loader.load();
        AppController appController = loader.getController();
        movieListController = appController.getMovieListController();
        editMovieController = movieListController.editMovieController;
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void enterMovieValues(String title, String description, String hours, String minutes, boolean watched){
        clickOn("#titleField").write(title);
        clickOn("#descriptionField").write(description);
        clickOn("#hoursField").write(hours);
        clickOn("#minutesField").write(minutes);
        if(watched){clickOn("#watchedCheckBox");}
    }

    private int movieListSize(){
        return movieListController.getMovies().size();
    }

    @BeforeEach
    public void setup() throws IOException{
        try {
            movieListController.loadMovieListFile(testFile);
        } catch (Exception e) {
            fail(e);
        }
        assertEquals(1, movieListSize());
        clickOn("#movieListTab");
    }

    @AfterEach
    public void tearDown() throws IOException{
        MovieStorage storage = new MovieStorage();
        storage.setFile(testFile);
        MovieList movieList = storage.loadMovies();
        movieList.getMovies().stream().forEach(movie -> {
            if(!movie.getTitle().equals("test movie")){
                movieList.removeMovie(movie);
            }
        });
        storage.saveMovies(movieList);
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
        clickOn("#openEditMovie");
        enterMovieValues(title, description, String.valueOf(duration.getHour()), String.valueOf(duration.getMinute()), watched);
        clickOn("#submitMovie");

        assertEquals(2, movieListController.getMovieList().getMovies().size());
        assertEquals(false, movieListController.editMovieWindow.isVisible());

        IMovie movie = movieListController.getMovieList().getMovie(title);
        
        assertEquals(title, movie.getTitle());
        assertEquals(description, movie.getDescription());
        assertEquals(duration, movie.getDuration());
        assertEquals(watched, movie.isWatched());
    }

    @Test 
    public void testAddMovie_titleInUse(){
        String title = "test movie";
        clickOn("#openEditMovie");
        enterMovieValues(title, description, String.valueOf(duration.getHour()), String.valueOf(duration.getMinute()), watched);
        clickOn("#submitMovie");
        assertNotEquals("", editMovieController.errorField.getText());
        assertEquals(1, movieListSize());
        assertEquals(true, movieListController.editMovieWindow.isVisible());
    }

    @Test
    public void testAddMovie_invalidDuration(){
        String validHours = "2";
        String validMinutes = "30";

        String nonInteger = "1o";
        clickOn("#openEditMovie");
        enterMovieValues(title, description, nonInteger, validMinutes, watched);
        clickOn("#submitMovie");
        assertEquals(1, movieListSize());
        assertTrue(movieListController.editMovieWindow.isVisible());
        assertNotEquals("", editMovieController.errorField.getText());

        String hoursOutOfRange = "30";
        clickOn("#openEditMovie");
        enterMovieValues(title, description, hoursOutOfRange, validMinutes, watched);
        clickOn("#submitMovie");
        assertEquals(1, movieListSize());
        
        String minutesOutOfRange = "-30";
        clickOn("#openEditMovie");
        enterMovieValues(title, description, validHours, minutesOutOfRange, watched);
        clickOn("#submitMovie");
        assertEquals(1, movieListSize());
    }
}
