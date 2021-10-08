package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MovieTest {

    private Movie movie;
    String initTitle = "initTitle";
    String initDescription = "initDescription";
    LocalTime initDuration = LocalTime.of(02, 00);
    Label initLabel = new Label("initLabel");
    Collection<Label> initLabels = new ArrayList<Label>(Arrays.asList(initLabel));
    Collection<Review> initReview = new ArrayList<Review>(Arrays.asList(new Review("", 1, LocalDate.now())));

    @Test
    public void testConstructor() {
        String title = "Film1";
        String description = "Dette er film nummer 1.";
        LocalTime duration = LocalTime.of(2, 10);
        Collection<Label> labels = new ArrayList<Label>(Arrays.asList(new Label("label")));
        Collection<Review> reviews = new ArrayList<Review>(Arrays.asList(new Review("", 1, LocalDate.now())));
        Movie movie = new Movie(title, description, duration, false, labels, reviews);

        assertEquals(title, movie.getTitle(), "Movie title is not correct.");
        assertEquals(description, movie.getDescription(), "Description is not correct.");
        assertEquals(duration, movie.getDuration(), "Duration is not correct.");
        assertEquals(labels, movie.getLabels(), "Labels is not correct.");
    }

    @BeforeEach
    public void setUp() {
        movie = new Movie(initTitle, initDescription, initDuration,false, initLabels, initReview);
    }

    @Test
    public void testSetTitle() {
        String legalTitle = "Title1.!?";
        String illegalTitle1 = "";
        String illegalTitle2 = "ThisTitleIsTooLongBecauseItContainsOver50Characters";

        assertEquals(initTitle, movie.getTitle(), "Movie title is not correct.");
        movie.setTitle(legalTitle);
        assertEquals(legalTitle, movie.getTitle(), "Movie title is not correct.");
        assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle1), "Did not throw exception on illegal title: " + illegalTitle1);
        assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle2),  "Did not throw exception on illegal title: " + illegalTitle2);
    }

    @Test
    public void testSetDescription() {
        String newDescription = "newDescription";

        assertEquals(initDescription, movie.getDescription(), "Description is not correct");
        movie.setDescription(newDescription);
        assertEquals(newDescription, movie.getDescription(), "Description is not correct");
    }

    @Test
    public void testSetDuration() {
        LocalTime newDuration = LocalTime.of(3, 30);

        assertEquals(initDuration, movie.getDuration(), "Duration is not correct");
        movie.setDuration(newDuration);
        assertEquals(newDuration, movie.getDuration(), "Duration is not correct");
    }

    @Test
    public void testAddLabel() {
        Label labelOne = new Label("labelOne");
        Label labelTwo = new Label("labelTwo");
        Collection<Label> newLabels = new ArrayList<Label>(initLabels);
        newLabels.add(labelOne);
        newLabels.add(labelTwo);

        assertEquals(initLabels, movie.getLabels(), "Labels is not correct");
        movie.addLabel(labelOne);
        movie.addLabel(labelTwo);
        assertEquals(newLabels, movie.getLabels(), "Labels is not correct");
        assertThrows(IllegalStateException.class, () -> movie.addLabel(labelTwo), "Failed to throw exception on adding duplicate label");        
    }

    @Test
    public void testRemoveLabel() {
        Label labelOne = new Label("labelOne");
        Label labelTwo = new Label("labelTwo");
        Collection<Label> newLabels = new ArrayList<Label>(initLabels);
        newLabels.add(labelOne);
        newLabels.add(labelTwo);
        movie.addLabel(labelOne);
        movie.addLabel(labelTwo);

        assertEquals(newLabels, movie.getLabels(), "Labels is not correct");
        movie.removeLabel(labelTwo);
        newLabels.remove(labelTwo);
        assertEquals(newLabels, movie.getLabels(), "Failed to remove label");
        movie.removeLabel(initLabel);
        newLabels.remove(initLabel);
        assertEquals(newLabels, movie.getLabels(), "Failed to remove label");
        movie.removeLabel(new Label("notAddedLabel"));
        assertEquals(newLabels, movie.getLabels(), "Failed on removing non-added label");
    }

    @Test
    public void testSetLabels() {
        Label labelOne = new Label("labelOne");
        Label labelTwo = new Label("labelTwo");
        Collection<Label> newLabels = new ArrayList<Label>(initLabels);
        newLabels.add(labelOne);

        assertEquals(initLabels, movie.getLabels(), "Labels is not correct");
        movie.setLabels(newLabels);
        assertEquals(newLabels, movie.getLabels(), "Failed on setting collection of labels");
        newLabels = new ArrayList<Label>(Arrays.asList(labelOne, labelTwo));
        movie.setLabels(newLabels);
        assertEquals(newLabels, movie.getLabels(), "Failed on setting collection of labels");
        assertThrows(IllegalArgumentException.class, () -> movie.setLabels(new ArrayList<Label>(Arrays.asList(labelOne, labelTwo, labelOne))), "Did not throw exception when adding duplicate labels");
    }

    @Test
    public void testSetWatched() {
        assertEquals(false, movie.isWatched(), "Wacthed is not correct");
        movie.setWatched(true);
        assertEquals(true, movie.isWatched(), "Failed to set watched true");
        movie.setWatched(true);
        assertEquals(true, movie.isWatched(), "Failed on setWatched() when movie is watched");
    }
}
