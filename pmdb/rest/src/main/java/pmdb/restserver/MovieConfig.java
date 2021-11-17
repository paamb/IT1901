package pmdb.restserver;

import core.IReview;
import core.Movie;
import core.MovieList;
import core.Review;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import json.moviepersistance.MovieStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import pmdb.restapi.MovieListService;

/**
 * MovieConfig, used in server.
 */
public class MovieConfig extends ResourceConfig {
  private MovieList movieList;
  private MovieStorage movieStorage;


  /**
   * Constructor for MovieConfig.
   * 
   */
  public MovieConfig(MovieList movieList) {
    setMovieList(movieList);
    movieStorage = new MovieStorage();
    movieStorage.setFile(new File("server-movielist.json"));
    register(MovieListService.class);
    register(JacksonFeature.class);
    register(MovieModuleObjectMapperProvider.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(MovieConfig.this.movieList);
        bind(MovieConfig.this.movieStorage);
      }
    });
  }

  public MovieConfig() {
    this(createDefaultMovieList());
  }

  public void setMovieList(MovieList movieList) {
    this.movieList = movieList;
  }

  private static MovieList createDefaultMovieList() {
    MovieStorage movieStorage = new MovieStorage();
    URL url = MovieConfig.class.getResource("default-movielist.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return movieStorage.readMovieList(reader);
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
    MovieList movieList = new MovieList();
    ArrayList<IReview> reviews =
        new ArrayList<IReview>(Arrays.asList(new Review("Teit", 1, LocalDate.of(2000, 1, 1)),
            new Review("Bra", 8, LocalDate.of(2001, 2, 2))));
    movieList.addMovie(new Movie("Movie_1", "Desc_1", 182, true, Arrays.asList(reviews.get(0))));
    movieList.addMovie(new Movie("Movie_2", "Desc_2", 182, false, Arrays.asList(reviews.get(1))));
    movieList.addMovie(new Movie("Movie_3", "Desc_3", 182, true, new ArrayList<>()));
    return movieList;
  }
}

