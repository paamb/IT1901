package core;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import json.moviepersistance.MovieModule;

public class MovieModuleTest {
  private static ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new MovieModule());
  }

  private final static String movieListString = """
      {
        "labels" : [ {
          "title" : "Comedy",
          "color" : "#EEEEEE"
        }, {
          "title" : "Romance",
          "color" : "#AAAAAA"
        }, {
          "title" : "Action",
          "color" : "#FFFFFF"
        } ],
        "movies" : [ {
          "title" : "Up",
          "description" : "Komedie",
          "duration" : 1,
          "watched" : true,
          "labels" : [ "Comedy", "Romance" ],
          "reviews" : [ {
            "comment" : "Teit",
            "rating" : 1,
            "whenWatched" : "2000-01-01"
          } ]
        }, {
          "title" : "Batman",
          "description" : "Action",
          "duration" : 43,
          "watched" : false,
          "labels" : [ "Action", "Comedy", "Romance" ],
          "reviews" : [ {
            "comment" : "Bra",
            "rating" : 8,
            "whenWatched" : "2001-02-02"
          } ]
        } ]
      }
      """;

  private boolean isLabelInMovie(IMovie movie, String title) {
    return ILabel.isLabelInMovie(movie, title);
  }

  @Test
  public void testSerializers() {
    MovieList movieList = new MovieList();
    List<IReview> reviews =
        new ArrayList<>(Arrays.asList(new Review("Teit", 1, LocalDate.of(2000, 1, 1)),
            new Review("Bra", 8, LocalDate.of(2001, 2, 2))));
    List<ILabel> labels = new ArrayList<>(Arrays.asList(new Label("Action", "#FFFFFF"),
        new Label("Comedy", "#EEEEEE"), new Label("Romance", "#AAAAAA")));
    Movie movie1 =
        new Movie("Up", "Komedie", 1, true, Arrays.asList(reviews.get(0)), Arrays.asList());
    Movie movie2 =
        new Movie("Batman", "Action", 43, false, Arrays.asList(reviews.get(1)), Arrays.asList());
    movie1.addLabel(labels.get(1));
    movie1.addLabel(labels.get(2));
    movie2.addLabel(labels.get(0));
    movie2.addLabel(labels.get(1));
    movie2.addLabel(labels.get(2));
    movieList.addMovie(movie1);
    movieList.addMovie(movie2);
    try {
      assertEquals(movieListString.replaceAll("\\s+", ""), mapper.writeValueAsString(movieList));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserializer() {
    try {
      MovieList movieList = mapper.readValue(movieListString, MovieList.class);
      List<IMovie> movies = new ArrayList<>();
      movieList.iterator().forEachRemaining(movies::add);
      IMovie movie1 = movies.get(0);
      IMovie movie2 = movies.get(1);
      IReview review1 = movie1.reviewIterator().next();
      IReview review2 = movie2.reviewIterator().next();
      assertEquals(movie1.getTitle(), "Up");
      assertEquals(movie2.getTitle(), "Batman");
      assertEquals(movie1.getDuration(), 1);
      assertEquals(movie2.getDuration(), 43);
      assertTrue(movie1.isWatched());
      assertFalse(movie2.isWatched());
      assertEquals("Teit", review1.getComment());
      assertEquals(1, review1.getRating());
      assertEquals(LocalDate.of(2000, 1, 1).toString(), review1.getWhenWatched().toString());
      assertEquals("Bra", review2.getComment());
      assertEquals(8, review2.getRating());
      assertEquals(LocalDate.of(2001, 2, 2).toString(), review2.getWhenWatched().toString());
      assertTrue(isLabelInMovie(movie1, "Comedy"));
      assertTrue(isLabelInMovie(movie1, "Romance"));
      assertTrue(isLabelInMovie(movie2, "Action"));
      assertTrue(isLabelInMovie(movie2, "Comedy"));
      assertTrue(isLabelInMovie(movie2, "Romance"));
    } catch (Exception e) {
      fail(e.getMessage());
    }

  }

  @Test
  public void testSerializersDeserializers() {
    MovieList movieList = new MovieList();
    List<IReview> reviews =
        new ArrayList<IReview>(Arrays.asList(new Review("Dust", 1, LocalDate.of(2000, 1, 1)),
            new Review("Utrolig bra", 8, LocalDate.of(2001, 2, 2))));
    List<ILabel> labels = new ArrayList<>(Arrays.asList(new Label("Action", "#FFFFFF"),
        new Label("Comedy", "#EEEEEE"), new Label("Romance", "#AAAAAA")));
    Movie movie1 = new Movie("Spiderman", "Action", 103, true, Arrays.asList(reviews.get(0)),
        Arrays.asList(labels.get(1), labels.get(2)));
    Movie movie2 = new Movie("Shutter Island", "Thriller", 45, false, Arrays.asList(reviews.get(1)),
        Arrays.asList(labels.get(0), labels.get(1), labels.get(2)));
    movieList.addMovie(movie1);
    movieList.addMovie(movie2);
    try {
      String movieListAsJson = mapper.writeValueAsString(movieList);
      MovieList movieList2 = mapper.readValue(movieListAsJson, MovieList.class);
      List<IMovie> movies = new ArrayList<>();
      movieList2.iterator().forEachRemaining(movies::add);
      IMovie movie3 = movies.get(0);
      IMovie movie4 = movies.get(1);
      IReview review1 = movie1.reviewIterator().next();
      IReview review2 = movie2.reviewIterator().next();
      assertEquals(movie3.getTitle(), movie1.getTitle());
      assertEquals(movie4.getTitle(), movie2.getTitle());
      assertEquals(movie3.getDuration(), movie1.getDuration());
      assertEquals(movie4.getDuration(), movie2.getDuration());
      assertEquals(movie3.isWatched(), movie1.isWatched());
      assertEquals(movie4.isWatched(), movie2.isWatched());
      assertEquals("Dust", review1.getComment());
      assertEquals(1, review1.getRating());
      assertEquals(LocalDate.of(2000, 1, 1).toString(), review1.getWhenWatched().toString());
      assertEquals("Utrolig bra", review2.getComment());
      assertEquals(8, review2.getRating());
      assertEquals(LocalDate.of(2001, 2, 2).toString(), review2.getWhenWatched().toString());
      assertTrue(isLabelInMovie(movie1, "Comedy"));
      assertTrue(isLabelInMovie(movie1, "Romance"));
      assertTrue(isLabelInMovie(movie2, "Action"));
      assertTrue(isLabelInMovie(movie2, "Comedy"));
      assertTrue(isLabelInMovie(movie2, "Romance"));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
