package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MovieTest {

    private Movie movie;
    String initTitle = "initTitle";
    String initDescription = "initDescription";
    LocalTime initDuration = LocalTime.of(02, 00);
    Label initLabel = new Label("initLabel");
    Collection<Label> initLabels = new ArrayList<Label>(Arrays.asList(initLabel));

    @Test
    public void testConstructor() {
        String title = "Film1";
        String description = "Dette er film nummer 1.";
        LocalTime duration = LocalTime.of(2, 10);
        Collection<Label> labels = new ArrayList<Label>(Arrays.asList(new Label("label")));
        Movie movie = new Movie(title, description, duration, labels);

        assertEquals(title, movie.getTitle());
        assertEquals(description, movie.getDescription());
        assertEquals(duration, movie.getDuration());
        assertEquals(labels, movie.getLabels());
    }

    @BeforeEach
    public void setUp() {
        movie = new Movie(initTitle, initDescription, initDuration, initLabels);
    }

    @Test
    public void testSetTitle() {
        String legalTitle = "Title1.!?";
        String illegalTitle1 = "title:";
        String illegalTitle2 = "title,";

        assertEquals(initTitle, movie.getTitle());
        movie.setTitle(legalTitle);
        assertEquals(legalTitle, movie.getTitle());
        assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle1));
        assertThrows(IllegalArgumentException.class, () -> movie.setTitle(illegalTitle2));
    }

    @Test
    public void testSetDescription() {
        String newDescription = "newDescription";

        assertEquals(initDescription, movie.getDescription());
        movie.setDescription(newDescription);
        assertEquals(newDescription, movie.getDescription());
    }

    @Test
    public void testSetDuration() {
        LocalTime newDuration = LocalTime.of(3, 30);

        assertEquals(initDuration, movie.getDuration());
        movie.setDuration(newDuration);
        assertEquals(newDuration, movie.getDuration());
    }

    @Test
    public void testAddLabel() {
        Label labelOne = new Label("labelOne");
        Label labelTwo = new Label("labelTwo");
        Collection<Label> newLabels = new ArrayList<Label>(initLabels);
        newLabels.add(labelOne);
        newLabels.add(labelTwo);

        assertEquals(initLabels, movie.getLabels());
        movie.addLabel(labelOne);
        movie.addLabel(labelTwo);
        assertEquals(newLabels, movie.getLabels());
        assertThrows(IllegalStateException.class, () -> movie.addLabel(labelTwo));        
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

        assertEquals(newLabels, movie.getLabels());
        movie.removeLabel(labelTwo);
        newLabels.remove(labelTwo);
        assertEquals(newLabels, movie.getLabels());
        movie.removeLabel(initLabel);
        newLabels.remove(initLabel);
        assertEquals(newLabels, movie.getLabels());
        movie.removeLabel(new Label("notAddedLabel"));
        assertEquals(newLabels, movie.getLabels());
    }

    @Test
    public void testSetLabels() {
        Label labelOne = new Label("labelOne");
        Label labelTwo = new Label("labelTwo");
        Collection<Label> newLabels = new ArrayList<Label>(initLabels);
        newLabels.add(labelOne);

        assertEquals(initLabels, movie.getLabels());
        movie.setLabels(newLabels);
        assertEquals(newLabels, movie.getLabels());
        newLabels = new ArrayList<Label>(Arrays.asList(labelOne, labelTwo));
        movie.setLabels(newLabels);
        assertEquals(newLabels, movie.getLabels());
        assertThrows(IllegalArgumentException.class, () -> movie.setLabels(new ArrayList<Label>(Arrays.asList(labelOne, labelTwo, labelOne))));
    }

    @Test
    public void testSetWatched() {
        assertEquals(false, movie.isWatched());
        movie.setWatched();
        assertEquals(true, movie.isWatched());
    }
}
