package ui;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class RemoteMovieListAccessTest {

  private WireMockConfiguration wireMockConfig;
  private WireMockServer wireMockServer;

  private RemoteMovieListAccess remoteMovieListAccess;

  @BeforeEach
  public void setupAndStartWireMockServer() throws URISyntaxException {
    wireMockConfig = WireMockConfiguration.wireMockConfig().port(8999);
    wireMockServer = new WireMockServer(wireMockConfig.portNumber());
    wireMockServer.start();

    WireMock.configureFor("localhost", wireMockConfig.portNumber());
    URI baseUri = new URI("http://localhost:" + wireMockServer.port() + "/movielist");
    remoteMovieListAccess = new RemoteMovieListAccess(baseUri);
  }

  @Test
  @DisplayName("Test")
  public void testGetMovieList() {

  }

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }
}
