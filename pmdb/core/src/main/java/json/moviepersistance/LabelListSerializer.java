package json.moviepersistance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.ILabel;
import core.LabelList;
import java.io.IOException;

public class LabelListSerializer extends JsonSerializer<LabelList> {

  @Override
  public void serialize(LabelList labels, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeArrayFieldStart("labels");

    if (labels.getLabels() != null) {
      for (ILabel label : labels.getLabels()) {
        gen.writeObject(label);
      }
    }

    gen.writeEndArray();
    gen.writeEndObject();
  }

}
