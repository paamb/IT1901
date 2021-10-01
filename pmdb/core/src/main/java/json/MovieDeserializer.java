package json;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import core.IMovie;
import core.Movie;

public class MovieDeserializer extends JsonDeserializer<IMovie>{
    
    @Override
    public IMovie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectNode movieNode = p.getCodec().readTree(p);
        return deserialize((JsonNode) movieNode);
    }

    IMovie deserialize(JsonNode movieNode){
        try{
            IMovie newMovie = new Movie();
            JsonNode durationText = movieNode.get("duration");
            if (durationText instanceof ObjectNode){
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
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
