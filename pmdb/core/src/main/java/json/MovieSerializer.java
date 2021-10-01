package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.IMovie;

public class MovieSerializer extends JsonSerializer<IMovie>{

    @Override
    public void serialize(IMovie movie, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("title", movie.getTitle());
        gen.writeStringField("description", movie.getDescription());
        gen.writeObjectField("duration", movie.getDuration());
        gen.writeBooleanField("watched", movie.isWatched());

        gen.writeEndObject();      
    }


    
}
