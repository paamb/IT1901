package json.moviepersistance;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.IReview;

public class ReviewSerializer extends JsonSerializer<IReview>{

    @Override
    public void serialize(IReview review, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("comment", review.getComment());
        gen.writeNumberField("rating", review.getRating());
        gen.writeStringField("whenWatched", review.getWhenWatched().toString());

        gen.writeEndObject();
    }
}
