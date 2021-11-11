package json.moviepersistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.ILabel;
import core.Label;
import java.io.IOException;

/**
 * LabelDeserilaizer class.
 */
public class LabelDeserializer extends JsonDeserializer<ILabel> {

  @Override
  public ILabel deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectNode labelNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) labelNode);
  }

  ILabel deserialize(JsonNode labelNode) {
    try {
      TextNode titleNode = (TextNode) labelNode.get("title");
      String title = titleNode.asText();

      TextNode colorNode = (TextNode) labelNode.get("color");
      String color = colorNode.asText();

      return new Label(title, color);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
