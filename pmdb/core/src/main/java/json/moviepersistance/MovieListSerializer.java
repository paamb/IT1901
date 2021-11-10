package json.moviepersistance;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.ILabel;
import core.IMovie;
import core.MovieList;
import java.io.IOException;

/**
 * MovieListSerializer class.
 * 
 * 
 */
public class MovieListSerializer extends JsonSerializer<MovieList> {

  @Override
  public void serialize(MovieList movieList, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();

    gen.writeArrayFieldStart("labels");

    if (movieList.getAllLabels() != null) {
      for (ILabel label : movieList.getAllLabels()) {
        gen.writeObject(label);
      }
    }
    gen.writeEndArray();

    gen.writeArrayFieldStart("movies");

    for (IMovie movie : movieList) {
      gen.writeObject(movie);
    }

    gen.writeEndArray();
    gen.writeEndObject();
  }
}
