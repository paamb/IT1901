package json.moviepersistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.ILabel;
import core.IMovie;
import core.MovieList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * MovieListDeserializer class.
 * 
 * 
 */
public class MovieListDeserializer extends JsonDeserializer<MovieList> {
  private MovieDeserializer movieDeserializer = new MovieDeserializer();
  private LabelDeserializer labelDeserializer = new LabelDeserializer();

  @Override
  public MovieList deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    MovieList movieList = new MovieList();
    Map<String, ILabel> labelsHash = new HashMap<>();
    TreeNode treeNode = p.getCodec().readTree(p);
    if (treeNode instanceof ObjectNode objectNode) {
      JsonNode labelsNode = objectNode.get("labels");
      boolean haslabels = labelsNode instanceof ArrayNode;
      if (haslabels) {
        for (JsonNode labelNode : ((ArrayNode) labelsNode)) {
          ILabel label = labelDeserializer.deserialize(labelNode);
          labelsHash.put(label.getTitle(), label);
        }
      }

      JsonNode movieListNode = objectNode.get("movies");
      boolean hasMovies = movieListNode instanceof ArrayNode;
      if (hasMovies) {
        for (JsonNode movieNode : ((ArrayNode) movieListNode)) {
          IMovie movie = movieDeserializer.deserialize(movieNode);
          ArrayNode labels = (ArrayNode) movieNode.get("labels");

          for (JsonNode labelNode : labels) {
            if (labelsHash.get(labelNode.asText()) != null) {
              movie.addLabel(labelsHash.get(labelNode.asText()));
            }
          }
          movieList.addMovie(movie);
        }
      }


    }
    return movieList;
  }

}
