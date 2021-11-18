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

/**
 * Sets available Http-requests in api.
 */
@Path(MovieListService.MOVIE_LIST_SERVICE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class MovieListService {
  public static final String MOVIE_LIST_SERVICE_PATH = "movielist";

  private static final Logger LOG = LoggerFactory.getLogger(MovieListService.class);

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
      MovieList movieList = movieStorage.loadMovieList();
      LOG.debug("getMovieList(): " + movieList.toString());
      return movieStorage.loadMovieList();
    } catch (Exception e) {
      LOG.debug("getMovieList(): failed, returning empty movieList");
      return new MovieList();
    }
  }

  /**
   * Gets movie by title.
   * 
   * @param movieTitle the movie title for the movie to be returned.
   * @return The movie with movie title in param.
   */
  @GET
  @Path("/{movieTitle}")
  public IMovie getMovieByTitle(@PathParam("movieTitle") String movieTitle) {
    LOG.debug("getMovieByTitle({})", movieTitle);
    return getMovieList().getMovie(movieTitle);
  }

  /**
   * puts a MovieList to server.
   * 
   * @param movieList to be put.
   * @return if put was successfull.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public boolean putMovieList(MovieList movieList) {
    LOG.debug("putMovieList(): " + movieList.toString());
    try {
      movieStorage.saveMovieList(movieList);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
