package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.IMovie;
import core.MovieList;

public class MovieListSerializer extends JsonSerializer<MovieList> {

    @Override
    public void serialize(MovieList movieList, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for(IMovie movie : movieList){
            gen.writeObject(movie);
        }   
        gen.writeEndObject();
    }
}
