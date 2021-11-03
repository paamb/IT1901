package json.moviepersistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.ILabel;
import core.LabelList;
import java.io.IOException;

/**
 * ReviewDeserializer class.
 * 
 * 
 */
public class LabelListDeserializer extends JsonDeserializer<LabelList> {
  private LabelDeserializer labelDeserializer = new LabelDeserializer();
  private LabelList labelList = new LabelList();

  @Override
  public LabelList deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectNode labelListNode = p.getCodec().readTree(p);
    return deserialize((JsonNode) labelListNode);
  }

  LabelList deserialize(JsonNode labelListNode) {
    try {
      boolean hasLabels = labelListNode instanceof ArrayNode;
      if (hasLabels) {
        for (JsonNode labelNode : ((ArrayNode) labelListNode)) {
          ILabel label = labelDeserializer.deserialize(labelNode);
          labelList.addLabel(label);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return labelList;
  }
}
