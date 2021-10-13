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
* Important note is that the SeenComparator should trumf the TitleComparator in the running app. The test FirstSortOnTitleThenSeen() is testing how it works in the app.
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
    /**
     * First sorted on Seen then on Title. Every movie with lexicograpically lower movieTitle will come first.
     */
    @Test
    public void testFirstSortOnSeenThenTitle(){
        Collection<IMovie> sortedMoviesOnSeen = movieList.getSortedMoviesOnSeen(movies);
        Collection<IMovie> sortedMovies = movieList.getSortedMoviesByTitle(sortedMoviesOnSeen);
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();


        try{

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(1), sortedMoviesIterator.next(), "First movie should be movie2 because its title = aaa and seen = false");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(0), sortedMoviesIterator.next(), "Second movie should be movie1 because its title = aaa and seen = true");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(4), sortedMoviesIterator.next(), "Third movie should be movie3 because its title = ccc and seen = true");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(5), sortedMoviesIterator.next(), "Fourth movie should be movie4 because its title = ddd and seen = false");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(2), sortedMoviesIterator.next(), "Fifth movie should be movie5 because its title = eee and seen = false");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(3), sortedMoviesIterator.next(), "Sixth movie should be movie6 because its title = fff and seen = true");
            }
        }catch(Exception e){
            fail();
        }
    }

    /**
     * First sorted on Title, then on Seen. Every unseen movie will come before a seen movie
     */
    @Test
    public void testFirstSortOnTitleThenSeen(){
        Collection<IMovie> sortedMoviesOnTitle = movieList.getSortedMoviesByTitle(movies);
        Collection<IMovie> sortedMovies = movieList.getSortedMoviesOnSeen(sortedMoviesOnTitle);
        
        Iterator<IMovie> sortedMoviesIterator = sortedMovies.iterator();
        
        try{

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(1), sortedMoviesIterator.next(), "First movie should be movie2 because seen = false and title = aaa");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(5), sortedMoviesIterator.next(), "Second movie should be movie4 because seen = false and title = ddd");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(2), sortedMoviesIterator.next(), "Third movie should be movie5 because seen = false and title = eee");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(0), sortedMoviesIterator.next(), "Fourth movie should be movie1 because seen = true and title = aaa");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(4), sortedMoviesIterator.next(), "Fifth movie should be movie3 because seen = true and title = ccc");
            }

            if (sortedMoviesIterator.hasNext()){
                assertEquals(movies.get(3), sortedMoviesIterator.next(), "Sixth movie should be movie2 because seen = true and title = fff");
            }
        }catch(Exception e){
            fail("Something went wrong when reading the movies");
        }
    }
}
