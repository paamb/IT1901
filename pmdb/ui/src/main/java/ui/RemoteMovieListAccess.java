package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.MovieList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import json.moviepersistance.MovieStorage;

/**
 * Class for Remote access, used when server is running.
 */
public class RemoteMovieListAccess implements MovieListAccess {
  
  private URI baseUri;

  private ObjectMapper objectMapper;

  public RemoteMovieListAccess(URI baseUri) {
    this.baseUri = baseUri; 
    objectMapper = MovieStorage.createObjectMapper();
  }

  /**
   * Constructs GET-request and retrieves MovieList from server.
   * 
   * @return MovieList.
   */
  public MovieList getMovieList() {
    HttpRequest request = HttpRequest.newBuilder(baseUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder().build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), MovieList.class);
    } catch (Exception e) {
      throw new RuntimeException("Server is not running: " + e);
    }
  }

  /**
   * Constructs PUT-request and sends MovieList to server.
   * 
   * @param movieList to be put.
   * @return boolean, successfull put or not.
   */
  public boolean putMovieList(MovieList movieList) {
    try {
      String jsonBody = objectMapper.writeValueAsString(movieList);
      HttpRequest request = HttpRequest.newBuilder(baseUri)
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(jsonBody))
          .build();
      final HttpResponse<String> response = HttpClient.newBuilder().build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), Boolean.class);
    } catch (Exception e) {
      throw new RuntimeException("Server is not running: " + e);
    }
  }
}
