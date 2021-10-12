package json.moviepersistance;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import core.IMovie;
import core.IReview;
import core.Movie;

public class MovieDeserializer extends JsonDeserializer<IMovie>{

    @Override
    public IMovie deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectNode movieNode = p.getCodec().readTree(p);
        return deserialize((JsonNode) movieNode);
    }

    IMovie deserialize(JsonNode movieNode){
        try{
            IMovie newMovie;
            String title;
            String description;
            LocalTime duration;
            boolean watched;

            TextNode titleNode = (TextNode) movieNode.get("title");
            title = titleNode.asText();

            TextNode descriptionNode = (TextNode) movieNode.get("description");
            description = descriptionNode.asText();

            ObjectNode durationText = (ObjectNode) movieNode.get("duration");
            int hour = durationText.get("hour").asInt();
            int minute = durationText.get("minute").asInt();
            duration = LocalTime.of(hour, minute);

            BooleanNode watchedNode = (BooleanNode) movieNode.get("watched");
            watched = watchedNode.booleanValue();

            newMovie = new Movie(title, description, duration, watched, new ArrayList<>());

            ArrayNode reviewsNode = (ArrayNode) movieNode.get("reviews");
            ReviewDeserializer reviewDeserializer = new ReviewDeserializer();
            for(JsonNode reviewNode : reviewsNode){
                IReview review = reviewDeserializer.deserialize(reviewNode);
                newMovie.addReview(review);
            }

            return newMovie;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
