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

    @Test
    public void testConstructor() {
        String title = "Film1";
        String description = "Dette er film nummer 1.";
        LocalTime duration = LocalTime.of(2, 10);
        //TODO: implement testing with labels
        Collection<Label> labels = new ArrayList<Label>(Arrays.asList());

        Movie movie = new Movie(title, description, duration, labels);

        assertEquals(title, movie.getTitle());
        assertEquals(description, movie.getDescription());
        assertEquals(duration, movie.getDuration());

        String illegalMovieTitle = "movie:movie";

        assertThrows(IllegalArgumentException.class, () -> new Movie(illegalMovieTitle, description, duration, labels));
    }

    //TODO: more testing after updated Movie.java is merged (!10)
}
