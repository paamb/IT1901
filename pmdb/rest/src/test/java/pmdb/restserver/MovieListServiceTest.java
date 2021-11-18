package pmdb.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.MovieList;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import json.moviepersistance.MovieStorage;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pmdb.restapi.MovieListService;

public class MovieListServiceTest extends JerseyTest {

  private ObjectMapper objectMapper;

  @Override
  protected ResourceConfig configure() {
    final MovieConfig config = new MovieConfig();
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    return config;
  }

  @BeforeEach
  @Override
  public void setUp() throws Exception {
    super.setUp();
    objectMapper = MovieStorage.createObjectMapper();
  }

  @Test
  public void testGet_movieList() {
    Response getResponse = target(MovieListService.MOVIE_LIST_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      MovieList movieList =
          objectMapper.readValue(getResponse.readEntity(String.class), MovieList.class);
      assertNotNull(movieList, "Should load a movieList");

    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
