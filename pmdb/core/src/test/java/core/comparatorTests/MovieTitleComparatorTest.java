package core.comparatorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import core.IMovie;
import core.Movie;
import core.MovieList;

public class MovieTitleComparatorTest {

    @Test
    public void testMovieTitleSort(){
        MovieList movieList = new MovieList();
        IMovie movie1 = new Movie("aaa", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList());
        IMovie movie2 = new Movie("bbb", "Action", LocalTime.of(2,3,4), false, Arrays.asList());
        IMovie movie3 = new Movie("ccc", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList());
        IMovie movie4 = new Movie("ddd", "Action", LocalTime.of(2,3,4), false, Arrays.asList());

        Collection<IMovie>movies = new ArrayList<IMovie>(Arrays.asList(movie2,movie3,movie4,movie1));

        Collection<IMovie> sortedMovies = movieList.getSortedMoviesByTitle(movies);
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();

        assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
        assertEquals(movie1, sortedMoviesIterator.next(), "First movie should be movie1 because title = aaa");

        assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
        assertEquals(movie2, sortedMoviesIterator.next(), "Second movie should be movie2 because title = bbb");

        assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
        assertEquals(movie3, sortedMoviesIterator.next(), "Third movie should be movie3 because title = ccc");

        assertTrue(sortedMoviesIterator.hasNext(), "The movieIterator should have more elements");
        assertEquals(movie4, sortedMoviesIterator.next(), "Fourth movie should be movie4 because title = ddd");

        assertFalse(sortedMoviesIterator.hasNext(), "The movieIterator should not have more elements");
    }
}
