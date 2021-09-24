package json;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.Movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Storage {
    String fileName;
    private Collection<Movie> movieList;
    private File file;

     /**
      * 
     * 
     * @param fileName The name the file you want to save/load from.
     */
    public Storage(String fileName){
        this.fileName = fileName;
        file = new File(fileName);
    }
    public void save(Collection<Movie> movieList){
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

    /**
     * 
     * @return ArrayList with movies from .json file
     */
    public Collection<Movie> load(){
        movieList = new ArrayList<>();

        if(file.length() != 0){
            try{
                ObjectMapper mapper = new ObjectMapper();
                List<ObjectNode> deserializedMovies = mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<ObjectNode>>(){});
                
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
