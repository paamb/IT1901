package json;

import com.fasterxml.jackson.databind.module.SimpleModule;

import core.IMovie;

public class MovieModule extends SimpleModule{
    public MovieModule(){
        addDeserializer(IMovie.class, new MovieDeserializer());
        addSerializer(IMovie.class, new MovieSerializer());
    }
}
