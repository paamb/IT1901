package json.moviepersistance;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import core.IReview;
import core.Review;

public class ReviewDeserializer extends JsonDeserializer<IReview>{

    @Override
    public IReview deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectNode reviewNode = p.getCodec().readTree(p);
        return deserialize((JsonNode) reviewNode);
    }

    IReview deserialize(JsonNode reviewNode){
        try{
            JsonNode commentNode = reviewNode.get("comment");
            String comment = "";
            if (commentNode instanceof ObjectNode){
                comment = commentNode.asText();
            }
            JsonNode ratingNode = reviewNode.get("rating");
            int rating = 1;
            if(ratingNode instanceof ObjectNode){
                rating = ratingNode.asInt();
            }
            JsonNode whenWatchedNode = reviewNode.get("whenWatched");
            LocalDate whenWatched = null;
            if(whenWatchedNode instanceof ObjectNode){
                int year = whenWatchedNode.get("year").asInt();
                int month = whenWatchedNode.get("month").asInt();
                int dayOfMonth = whenWatchedNode.get("day").asInt();
                whenWatched = LocalDate.of(year, month, dayOfMonth);
            }
            System.out.println(rating);
            return new Review(comment, rating, whenWatched);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
