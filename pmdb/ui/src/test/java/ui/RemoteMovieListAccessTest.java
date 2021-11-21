package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.MovieList;

/**
 * Test class for RemoteMovieListAccess.java.
 */
public class RemoteMovieListAccessTest {

  private final String movieListFil = """
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

  private WireMockConfiguration wireMockConfig;
  private WireMockServer wireMockServer;

  private RemoteMovieListAccess remoteMovieListAccess;

  /**
   * Sets up and starts the Mockerver, and initializes remoteMovieListAccess.
   */
  @BeforeEach
  public void setupAndStartWireMockServer() throws URISyntaxException {
    wireMockConfig = WireMockConfiguration.wireMockConfig().port(8999);
    wireMockServer = new WireMockServer(wireMockConfig.portNumber());
    wireMockServer.start();

    WireMock.configureFor("localhost", wireMockConfig.portNumber());
    URI uri = new URI("http://localhost:" + wireMockServer.port() + "/movielist");
    remoteMovieListAccess = new RemoteMovieListAccess(uri);
  }

  @Test
  public void testGetMovieList() {
    stubFor(get(urlEqualTo("/movielist")).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(movieListFil)));

    MovieList movieList = remoteMovieListAccess.getMovieList();

    assertEquals(2, movieList.getMovies().size());
    assertNotNull(movieList.getMovie("Up"));
    assertNotNull(movieList.getMovie("Batman"));
    assertTrue(movieList.getMovie("Up").isWatched());
    assertTrue(!movieList.getMovie("Batman").isWatched());
    assertEquals(1, movieList.getMovie("Up").getDuration());
    assertEquals(3, movieList.getMovie("Batman").getLabelCount());
  }

  @Test
  public void testPutMovieList() {
    stubFor(put(urlEqualTo("/movielist")).withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("true")));

    assertTrue(remoteMovieListAccess.putMovieList(new MovieList()));
  }

  @AfterEach
  public void teardown() {
    wireMockServer.stop();
  }
}