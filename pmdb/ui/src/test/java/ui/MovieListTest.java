package ui;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import json.moviepersistance.MovieStorage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import core.IMovie;
import core.MovieList;

public class MovieListTest extends ApplicationTest{
    
    private MovieListController movieListController;

    private EditMovieController editMovieController;

    private final File testFile = new File("src\\test\\resources\\ui", "MovieList_test.json");

    private String title = "title";
    private String description = "description";
    private String hours = "2";
    private String minutes = "30";
    private boolean watched = false;

    @Override
    public void start(final Stage stage) throws Exception{
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
        final Parent root = loader.load();
        AppController appController = loader.getController();
        movieListController = appController.movieListController;
        editMovieController = movieListController.editMovieController;
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void deleteInput(TextField text){
        while(text.getText().length() != 0){
            Platform.runLater(() -> {
                (new Robot()).keyPress(KeyCode.BACK_SPACE);
            });
        }
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
        assertNotNull(editMovieController);
    }

    // @Test
    // public void testOpenEditMovie(){
    //     clickOn("#openEditMovie");
    //     assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    //     clickOn("#openEditMovie");
    //     assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    // }
    
    // @Test
    // public void testCloseEditMovie(){
    //     clickOn("#openEditMovie");
    //     assertTrue(movieListController.editMovieWindow.isVisible(), "EditMovie-window should be visible.");
    //     clickOn("#cancelButton");
    //     assertFalse(movieListController.editMovieWindow.isVisible(), "EditMovie-window should not be visible.");
    // }
    
    // @Test
    // public void testAddMovie_valid(){
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, hours, minutes, watched);
    //     clickOn("#submitMovie");

    //     assertEquals(2, movieListController.getMovieList().getMovies().size());
    //     assertEquals(false, movieListController.editMovieWindow.isVisible());

    //     IMovie movie = movieListController.getMovieList().getMovie(title);
        
    //     assertEquals(title, movie.getTitle());
    //     assertEquals(description, movie.getDescription());
    //     assertEquals(hours, String.valueOf(movie.getDuration().getHour()));
    //     assertEquals(minutes, String.valueOf(movie.getDuration().getMinute()));
    //     assertEquals(watched, movie.isWatched());
    // }

    // @Test 
    // public void testAddMovie_titleInUse(){
    //     String title = "test movie";
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, hours, minutes, watched);
    //     clickOn("#submitMovie");
    //     assertNotEquals("", editMovieController.errorField.getText());
    //     assertEquals(1, movieListSize());
    //     assertEquals(true, movieListController.editMovieWindow.isVisible());
    // }

    // @Test
    // public void testAddMovie_invalidDuration(){

    //     String nonInteger = "1o";
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, nonInteger, minutes, watched);
    //     clickOn("#submitMovie");
    //     assertEquals(1, movieListSize());
    //     assertTrue(movieListController.editMovieWindow.isVisible());
    //     assertNotEquals("", editMovieController.errorField.getText());

    //     String hoursOutOfRange = "30";
    //     clickOn("#hoursField");
    //     deleteInput(editMovieController.hoursField);
    //     clickOn("#hoursField").write(hoursOutOfRange);
    //     clickOn("#submitMovie");
    //     assertEquals(1, movieListSize());
        
    //     String minutesOutOfRange = "-30";
    //     clickOn("#hoursField");
    //     deleteInput(editMovieController.hoursField);
    //     clickOn("#hoursField").write(hours);
    //     clickOn("#minutesField");
    //     deleteInput(editMovieController.minutesField);
    //     clickOn("#minutesField").write(minutesOutOfRange);
    //     clickOn("#submitMovie");
    //     assertEquals(1, movieListSize());
    // }

    // @Test
    // public void testDeleteMovie(){
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, hours, minutes, watched);
    //     clickOn("#submitMovie");
    //     clickOn(movieListController.movieDisplay.lookup("#1").lookup("#deleteMovie"));
    //     assertEquals(1, movieListSize());
    // }

    // @Test
    // public void testEditMovie_valid(){
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, hours, minutes, watched);
    //     clickOn("#submitMovie");
    //     clickOn(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));

    //     String newTitle = "new title";
    //     clickOn("#titleField");
    //     deleteInput(editMovieController.titleField);
    //     clickOn("#titleField").write(newTitle);
    //     clickOn("#submitMovie");
    //     assertFalse(movieListController.editMovieWindow.isVisible());
    //     assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    //     assertEquals(2, movieListSize());
        
    //     clickOn(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));
    //     clickOn("#submitMovie");
    //     assertFalse(movieListController.editMovieWindow.isVisible());
    //     assertEquals(newTitle, movieListController.getMovieList().getMovie(newTitle).getTitle());
    //     assertEquals(2, movieListSize());
    // }
    
    // @Test
    // public void testEditMovie_titleInUse(){
    //     clickOn("#openEditMovie");
    //     enterMovieValues(title, description, hours, minutes, watched);
    //     clickOn("#submitMovie");
        
    //     String invalidTitle = "test movie";
    //     clickOn(movieListController.movieDisplay.lookup("#1").lookup("#editMovie"));
    //     clickOn("#titleField");
    //     deleteInput(editMovieController.titleField);
    //     clickOn("#titleField").write(invalidTitle);
    //     clickOn("#submitMovie");
    //     assertTrue(movieListController.editMovieWindow.isVisible());
    //     assertEquals(title, movieListController.getMovieList().getMovie(title).getTitle());
    //     assertEquals(2, movieListSize());
    // }

    // @Test
    // public void testSortMovies(){
    //     String title1 = "test movie";
    //     String title2 = "aaa";

    //     clickOn("#openEditMovie");
    //     enterMovieValues(title2, description, hours, minutes, watched);
    //     clickOn("#submitMovie");
    //     assertEquals(title1, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());

    //     clickOn("#sortOnTitleCheckbox");
    //     assertEquals(title2, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());
        
    //     clickOn("#sortOnSeenCheckbox");
    //     assertEquals(title2, ((Label) movieListController.movieDisplay.lookup("#0").lookup("#movieTitle")).getText());
    // }
}
