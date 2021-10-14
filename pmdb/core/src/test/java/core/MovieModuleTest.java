package core;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import json.moviepersistance.MovieModule;

public class MovieModuleTest {
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp(){
        mapper = new ObjectMapper();
        mapper.registerModule(new MovieModule());
    }
    private final static String movieListString = """
    {
      "movies" : [ {
        "title" : "Up",
        "description" : "Komedie",
        "duration" : {
          "hour" : 1,
          "minute" : 2,
          "second" : 3,
          "nano" : 0
        },
        "watched" : true,
        "reviews" : [ {
          "comment" : "Teit",
          "rating" : 1,
          "whenWatched" : "2000-01-01"
        } ]
      }, {
        "title" : "Batman",
        "description" : "Action",
        "duration" : {
          "hour" : 2,
          "minute" : 3,
          "second" : 4,
          "nano" : 0
        },
        "watched" : false,
        "reviews" : [ {
          "comment" : "Bra",
          "rating" : 8,
          "whenWatched" : "2001-02-02"
        } ]
      } ]
    }
    """;
    @Test
    public void testSerializers(){
        MovieList movieList = new MovieList();
        ArrayList<IReview> reviews= new ArrayList<IReview>(Arrays.asList(new Review("Teit", 1, LocalDate.of(2000, 1, 1)), new Review("Bra", 8, LocalDate.of(2001, 2, 2))));
        Movie movie1 = new Movie("Up", "Komedie", LocalTime.of(1, 2, 3), true, Arrays.asList(reviews.get(0)));
        Movie movie2 = new Movie("Batman", "Action", LocalTime.of(2,3,4), false, Arrays.asList(reviews.get(1)));
        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        try{
            assertEquals(movieListString.replaceAll("\\s+", ""), mapper.writeValueAsString(movieList));
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testDeserializer(){
        try{
            MovieList movieList = mapper.readValue(movieListString, MovieList.class);
            List<IMovie> movies = new ArrayList<>();
            movieList.iterator().forEachRemaining(movies::add);
            IMovie movie1 = movies.get(0);
            IMovie movie2 = movies.get(1);
            IReview review1 = movie1.getReviews().iterator().next();
            IReview review2 = movie2.getReviews().iterator().next();
            assertEquals(movie1.getTitle(), "Up");
            assertEquals(movie2.getTitle(), "Batman");
            assertEquals(movie1.getDuration(), LocalTime.of(1, 2));
            assertEquals(movie2.getDuration(), LocalTime.of(2, 3));
            assertTrue(movie1.isWatched());
            assertFalse(movie2.isWatched());
            assertEquals("Teit", review1.getComment());
            assertEquals(1, review1.getRating());
            assertEquals(LocalDate.of(2000, 1, 1).toString(), review1.getWhenWatched().toString());
            assertEquals("Bra", review2.getComment());
            assertEquals(8, review2.getRating());
            assertEquals(LocalDate.of(2001, 2, 2).toString(), review2.getWhenWatched().toString());
            
        }catch(Exception e){
          fail();
        }
    }
}
