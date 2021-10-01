package json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import core.IMovie;
import core.MovieList;

public class MovieListDeserializer extends JsonDeserializer<MovieList>{
    private MovieDeserializer movieDeserializer = new MovieDeserializer();
    private MovieList movieList = new MovieList();
    @Override
    public MovieList deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = p.getCodec().readTree(p);
        if(treeNode instanceof ObjectNode objectNode){
            JsonNode movieListNode = objectNode.get("movies");
            boolean hasMovies = movieListNode instanceof ArrayNode;
            if(hasMovies){
                for(JsonNode movieNode : ((ArrayNode) movieListNode)){
                    IMovie movie = movieDeserializer.deserialize(movieNode);
                    movieList.addMovie(movie);
                }
            }
        }
        return movieList;
    }
    
}
