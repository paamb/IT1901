package json.moviepersistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.IMovie;
import core.IReview;
import core.Movie;
import java.io.IOException;
import java.util.ArrayList;

/**
 * MovieDeserializer class.
 * 
 * 
 */
public class MovieDeserializer extends JsonDeserializer<IMovie> {

  @Override
  public IMovie deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectNode movieNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) movieNode);
  }

  IMovie deserialize(JsonNode movieNode) {
    try {
      TextNode titleNode = (TextNode) movieNode.get("title");
      String title = titleNode.asText();

      TextNode descriptionNode = (TextNode) movieNode.get("description");
      String description = descriptionNode.asText();

      IntNode durationNode = (IntNode) movieNode.get("duration");
      int duration = durationNode.asInt();

      BooleanNode watchedNode = (BooleanNode) movieNode.get("watched");
      boolean watched = watchedNode.asBoolean();

      IMovie newMovie =
          new Movie(title, description, duration, watched, new ArrayList<>(), new ArrayList<>());

      ArrayNode reviewsNode = (ArrayNode) movieNode.get("reviews");
      ReviewDeserializer reviewDeserializer = new ReviewDeserializer();
      for (JsonNode reviewNode : reviewsNode) {
        IReview review = reviewDeserializer.deserialize(reviewNode);
        newMovie.addReview(review);
      }

      return newMovie;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
