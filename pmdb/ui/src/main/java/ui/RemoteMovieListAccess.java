package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.MovieList;
import json.moviepersistance.MovieStorage;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

public class RemoteMovieListAccess {
  
  private URI baseUri;

  private ObjectMapper objectMapper;

  public RemoteMovieListAccess(URI baseUri) {
    this.baseUri = baseUri; 
    objectMapper = MovieStorage.createObjectMapper();
  }

  public MovieList getMovieList() {
    HttpRequest request = HttpRequest.newBuilder(baseUri)
        .header("Accept", "application/json")
        .GET()
        .build();
    try {
      HttpResponse<String> response = HttpClient.newBuilder().build()
          .send(request, HttpResponse.BodyHandlers.ofString());
      return objectMapper.readValue(response.body(), MovieList.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
