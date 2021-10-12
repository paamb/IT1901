package core.comparatorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import core.IMovie;
import core.Movie;
import core.MovieList;

public class MovieSeenComparatorTest {

    @Test
    public void testMovieTitleSort(){
        MovieList movieList = new MovieList();
        IMovie movie1 = new Movie("aaa", "Komedie", LocalTime.of(1, 2, 3), false, Arrays.asList());
        IMovie movie2 = new Movie("ddd", "Action", LocalTime.of(2,3,4), false, Arrays.asList());
        IMovie movie3 = new Movie("aaa", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList());
        IMovie movie4 = new Movie("ccc", "Action", LocalTime.of(2,3,4), true, Arrays.asList());
        

        Collection<IMovie>movies = new ArrayList<IMovie>(Arrays.asList(movie2,movie3,movie4,movie1));

        Collection<IMovie> sortedMovies = movieList.getSortedMoviesOnSeen(movies);
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();
        try{
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movie2, sortedMoviesIterator.next());
            }
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movie1, sortedMoviesIterator.next());
            }
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movie3, sortedMoviesIterator.next());
            }
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movie4, sortedMoviesIterator.next());
            } 
        }catch(Exception e){
            fail();
        }
    }
}