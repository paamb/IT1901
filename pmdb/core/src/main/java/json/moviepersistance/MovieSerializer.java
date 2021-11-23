package json.moviepersistance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.ILabel;
import core.IMovie;
import core.IReview;
import java.io.IOException;
import java.util.Iterator;

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
    gen.writeNumberField("duration", movie.getDuration());
    gen.writeBooleanField("watched", movie.isWatched());

    gen.writeArrayFieldStart("labels");

    for (Iterator<ILabel> labels = movie.labelIterator(); labels.hasNext(); ) {
      gen.writeString(labels.next().getTitle());
    }
    
    gen.writeEndArray();

    gen.writeArrayFieldStart("reviews");

    for (Iterator<IReview> reviews = movie.reviewIterator(); reviews.hasNext();) {
      gen.writeObject(reviews.next());
    }
    
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
