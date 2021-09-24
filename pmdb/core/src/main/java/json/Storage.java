package json;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.Movie;
import core.WatchList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Storage {
    private Collection<Movie> movieList;
    private File file;

    public Storage(){
        file = new File("movies.json");
    }
    public void save(WatchList watchList){
        movieList = watchList.getMovies();

        try {
            ObjectMapper mapper = new ObjectMapper();
            FileWriter fileWriter = new FileWriter(file, false);
            SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

            for(Movie storedMovie : movieList){
                seqWriter.write(storedMovie);
            }

            seqWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Movie> load(WatchList watchList){
        movieList = new ArrayList<>();

        if(file.length() != 0){
            try{
                ObjectMapper mapper = new ObjectMapper();
                List<ObjectNode> deserializedMovies = mapper.readValue(Paths.get("movies.json").toFile(), new TypeReference<List<ObjectNode>>(){});
                
                for (ObjectNode m : deserializedMovies) {
                    Movie newMovie = new Movie();

                    JsonNode durationText = m.get("duration");
                    if (durationText instanceof JsonNode){
                        int hour = durationText.get("hour").asInt();
                        int minute = durationText.get("minute").asInt();
                        int second = durationText.get("second").asInt();
                        newMovie.setDuration(LocalTime.of(hour, minute, second));
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
        }
        return movieList;
    }
}
