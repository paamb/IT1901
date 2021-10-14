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
    private String initTitle = "initTitle";
    private String initDescription = "initDescription";
    private LocalTime initDuration = LocalTime.of(02, 00);
    private Collection<IReview> initReview = new ArrayList<IReview>(Arrays.asList(new Review("", 1, LocalDate.now())));

    @Test
    public void testConstructor() {
        String title = "Film1";
        String description = "Dette er film nummer 1.";
        LocalTime duration = LocalTime.of(2, 10);
        Collection<IReview> reviews = new ArrayList<IReview>(Arrays.asList(new Review("", 1, LocalDate.now())));
        Movie movie = new Movie(title, description, duration, false, reviews);

        assertEquals(title, movie.getTitle(), "Movie title is not correct.");
        assertEquals(description, movie.getDescription(), "Description is not correct.");
        assertEquals(duration, movie.getDuration(), "Duration is not correct.");
    }

    @BeforeEach
    public void setUp() {
        movie = new Movie(initTitle, initDescription, initDuration,false, initReview);
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
    public void testSetWatched() {
        assertEquals(false, movie.isWatched(), "Wacthed is not correct");
        movie.setWatched(true);
        assertEquals(true, movie.isWatched(), "Failed to set watched true");
        movie.setWatched(true);
        assertEquals(true, movie.isWatched(), "Failed on setWatched() when movie is watched");
    }
}
