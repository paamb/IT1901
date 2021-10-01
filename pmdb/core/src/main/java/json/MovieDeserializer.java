package json;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.IMovie;
import core.Movie;

public class MovieDeserializer extends JsonDeserializer<IMovie>{
    
    @Override
    public IMovie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectNode movieNode = p.getCodec().readTree(p);

        IMovie newMovie = new Movie();

        JsonNode durationText = movieNode.get("duration");
        if (durationText instanceof TextNode){
            int hour = durationText.get("hour").asInt();
            int minute = durationText.get("minute").asInt();
            newMovie.setDuration(LocalTime.of(hour, minute));
        }

        JsonNode titleNode = movieNode.get("title");
        if(titleNode instanceof TextNode){
            newMovie.setTitle(titleNode.asText());
        }

        JsonNode descriptionNode = movieNode.get("description");
        if(descriptionNode instanceof TextNode){
            newMovie.setDescription(descriptionNode.textValue());
        }

        JsonNode watchedNode = movieNode.get("watched");
        if(watchedNode instanceof BooleanNode){
            newMovie.setWatched(watchedNode.booleanValue());
        }


        return newMovie;
    }

    public Collection<IMovie> deserialize(String filename){
    Collection<IMovie> movieList = new ArrayList<>();
    try{
        ObjectMapper mapper = new ObjectMapper();
        List<ObjectNode> deserializedMovies = mapper.readValue(Paths.get(filename).toFile(), new TypeReference<List<ObjectNode>>(){});
        
        for (ObjectNode m : deserializedMovies) {
            IMovie newMovie = new Movie();

            JsonNode durationText = m.get("duration");
            if (durationText instanceof JsonNode){
                int hour = durationText.get("hour").asInt();
                int minute = durationText.get("minute").asInt();
                newMovie.setDuration(LocalTime.of(hour, minute));
            }

            JsonNode titleNode = m.get("title");
            if(titleNode instanceof JsonNode){
                newMovie.setTitle(titleNode.asText());
            }

            JsonNode descriptionNode = m.get("description");
            if(descriptionNode instanceof JsonNode){
                newMovie.setDescription(descriptionNode.textValue());
            }

            JsonNode watchedNode = m.get("watched");
            if(watchedNode instanceof JsonNode){
                newMovie.setWatched(watchedNode.booleanValue());
            }

            movieList.add(newMovie);
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return movieList;
}

        
}
