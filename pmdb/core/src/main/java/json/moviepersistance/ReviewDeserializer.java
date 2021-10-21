package json.moviepersistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.IReview;
import core.Review;
import java.io.IOException;
import java.time.LocalDate;

/**
 * ReviewDeserializer class.
 * 
 * 
 */
public class ReviewDeserializer extends JsonDeserializer<IReview> {

  @Override
  public IReview deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectNode reviewNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) reviewNode);
  }

  IReview deserialize(JsonNode reviewNode) {
    try {
      TextNode commentNode = (TextNode) reviewNode.get("comment");
      String comment = commentNode.asText();

      IntNode ratingNode = (IntNode) reviewNode.get("rating");
      int rating = ratingNode.asInt();

      TextNode whenWatchedNode = (TextNode) reviewNode.get("whenWatched");
      String[] whenWatchedArray = whenWatchedNode.asText().split("-");
      int year = Integer.parseInt(whenWatchedArray[0]);
      int month = Integer.parseInt(whenWatchedArray[1]);
      int day = Integer.parseInt(whenWatchedArray[2]);
      LocalDate whenWatched = LocalDate.of(year, month, day);

      return new Review(comment, rating, whenWatched);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
