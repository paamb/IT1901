package core.comparatorTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.IMovie;
import core.Movie;
import core.MovieList;
/**
* Important note is that the SeenComparator should trumf the TitleComparator in the running app
*/
public class CombinedComparatorSortTest {

    private MovieList movieList;
    private List<IMovie> movies;
    @BeforeEach
    public void setUp(){
        movieList = new MovieList();
        IMovie movie1 = new Movie("aaa", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList());
        IMovie movie2 = new Movie("aaa", "Action", LocalTime.of(2,3,4), false, Arrays.asList());
        IMovie movie3 = new Movie("ccc", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList());
        IMovie movie4 = new Movie("ddd", "Action", LocalTime.of(2,3,4), false, Arrays.asList());
        IMovie movie5 = new Movie("eee", "Action", LocalTime.of(2,3,4), false, Arrays.asList());
        IMovie movie6 = new Movie("fff", "Action", LocalTime.of(2,3,4), true, Arrays.asList());
        movies = new ArrayList<>(Arrays.asList(movie1,movie2,movie5,movie6,movie3,movie4));
    }
    @Test
    public void testFirstSortOnSeenThenTitle(){
        Collection<IMovie> sortedMoviesOnSeen = movieList.getSortedMoviesOnSeen(movies);
        Collection<IMovie> sortedMovies = movieList.getSortedMoviesByTitle(sortedMoviesOnSeen);
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();


        try{
            // First is movie2 because its false and aaa
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(1), sortedMoviesIterator.next());
            }
            // Second is movie1 because its true and aaa
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(0), sortedMoviesIterator.next());
            }
            // Third is movie3 because its ccc
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(4), sortedMoviesIterator.next());
            }
            // Fourth is movie4 because its ddd
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(5), sortedMoviesIterator.next());
            }
            // Fifth is movie5 because its eee
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(2), sortedMoviesIterator.next());
            }
            // Sixth is movie6 because its fff
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(3), sortedMoviesIterator.next());
            }
        }catch(Exception e){
            fail();
        }
    }
    @Test
    public void testFirstSortOnTitleThenSeen(){
        Collection<IMovie> sortedMoviesOnTitle = movieList.getSortedMoviesByTitle(movies);
        Collection<IMovie> sortedMovies = movieList.getSortedMoviesOnSeen(sortedMoviesOnTitle);
        
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();


        try{
            // First is movie2 because its false and aaa
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(1), sortedMoviesIterator.next());
            }
            // Second is movie4 because its false and ddd
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(5), sortedMoviesIterator.next());
            }
            // Third is movie5 because its false and eee 
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(2), sortedMoviesIterator.next());
            }
            // Fourth is movie1 because its true and aaa
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(0), sortedMoviesIterator.next());
            }
            // Fifth is movie3 because its true and ccc
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(4), sortedMoviesIterator.next());
            }
            // Sixth is movie6 because its true and fff
            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(3), sortedMoviesIterator.next());
            }
        }catch(Exception e){
            fail();
        }
    }
}
