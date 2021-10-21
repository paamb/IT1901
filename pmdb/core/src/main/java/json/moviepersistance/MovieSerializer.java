package json.moviepersistance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.IMovie;
import core.IReview;
import java.io.IOException;

/**
 * MovieSerializer class.
 * 
 * 
 */
public class MovieSerializer extends JsonSerializer<IMovie> {

  @Override
  public void serialize(IMovie movie, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();

    gen.writeStringField("title", movie.getTitle());
    gen.writeStringField("description", movie.getDescription());
    gen.writeObjectField("duration", movie.getDuration());
    gen.writeBooleanField("watched", movie.isWatched());

    gen.writeArrayFieldStart("reviews");

    if (movie.getReviews() != null) {
      for (IReview review : movie.getReviews()) {
        gen.writeObject(review);
      }
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
