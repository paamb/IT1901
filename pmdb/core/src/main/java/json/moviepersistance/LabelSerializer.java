package json.moviepersistance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.ILabel;
import java.io.IOException;

/**
 * LabelSerializer class.
 */
public class LabelSerializer extends JsonSerializer<ILabel> {

  @Override
  public void serialize(ILabel label, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();

    gen.writeStringField("title", label.getTitle());
    gen.writeStringField("color", label.getColor());

    gen.writeEndObject();
  }
}
