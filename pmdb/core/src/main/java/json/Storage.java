package json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collection;

import core.IMovie;
import core.MovieList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

public class Storage {
    String fileName;
    private File file;
    private ObjectMapper mapper;

     /**
      * 
     * 
     * @param fileName The name the file you want to save/load from.
     */
    public Storage(String fileName){
        this.fileName = fileName;
        file = new File(fileName);
        createObjectMapper();
    }

    /**
      * 
     * 
     * @param movieList
     */
    public void save(Collection<IMovie> movieList){
        try {
            ObjectMapper mapper = new ObjectMapper();
            FileWriter fileWriter = new FileWriter(file, false);
            SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

            for(IMovie storedMovie : movieList){
                seqWriter.write(storedMovie);
            }

            seqWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public MovieList loadMovies() throws IOException{
        //TODO
        // List<ObjectNode> deserializedMovies = mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<ObjectNode>>(){});
        Reader fileReader = new FileReader(Paths.get(fileName).toFile(), StandardCharsets.UTF_8);
        return mapper.readValue(fileReader, MovieList.class);

    }

    private void createObjectMapper(){
        mapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper(){
        return mapper;
    }
}
