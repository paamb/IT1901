package pmdb.restserver;

import json.moviepersistance.MovieStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import pmdb.restapi.MovieListService;

/**
 * MovieConfig, used in server.
 */
public class MovieConfig extends ResourceConfig {
  private MovieStorage movieStorage;


  /**
   * Constructor for MovieConfig.
   * 
   */
  public MovieConfig() {
    movieStorage = new MovieStorage();
    movieStorage.setFilePath("server-movielist.json");
    register(MovieListService.class);
    register(JacksonFeature.class);
    register(MovieModuleObjectMapperProvider.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(MovieConfig.this.movieStorage);
      }
    });
  }
}

