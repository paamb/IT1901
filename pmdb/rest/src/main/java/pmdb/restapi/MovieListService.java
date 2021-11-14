package pmdb.restapi;

import core.IMovie;
import core.MovieList;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import json.moviepersistance.MovieStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(MovieListService.MOVIE_LIST_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MovieListService {
  public static final String MOVIE_LIST_SERVICE_PATH = "movie";

  private static final Logger LOG = LoggerFactory.getLogger(MovieListService.class);

  // @Context
  // public MovieList movieList;

  @Context
  public MovieStorage movieStorage = new MovieStorage();

  /**
   * The root resource, /movie.
   *
   * @return the MovieList
   */
  @GET
  public MovieList getMovieList() {
    try {
      return movieStorage.loadMovieList();
    } catch (Exception e) {
      return new MovieList();
    }
  }

  /**
   * Gets movie by title.
   * 
   * @param movieTitle the movie title for the movie to be returned.
   * @return The movie with movie title in param.
   */
  // @GET
  // @Path("/{movieTitle}")
  // public IMovie getMovieByTitle(@PathParam("movieTitle") String movieTitle) {
  //   IMovie movie = movieList.getMovie(movieTitle);
  //   LOG.debug("getMovieByTitle: " + movie);
  //   return movie;
  // }

  /**
   * Adds a movie to the movieList.
   * 
   * @param movie movie the movie to be added.
   * @return If the movie was successfully added.
   */
  // @PUT
  // @Path("/movie")
  // public boolean addMovie(IMovie movie) {
  //   this.movieList.addMovie(movie);
  //   return movieList.getMovies().contains(movie);
  // }


  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean putMovieList(MovieList movieList) {
    try {
      movieStorage.saveMovieList(movieList);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}

