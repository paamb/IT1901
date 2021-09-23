package json;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import core.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
public class Storage {
    private Collection<Movie> movies;
    public Storage(){
        movies = new ArrayList<>();
    }
    public void save(Movie movie){
        //TODO This saves a new array every time it is called.
        try {
            File file = new File("movies.json");
            ObjectMapper mapper = new ObjectMapper();
            FileWriter fileWriter = new FileWriter(file, true);
            SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
            seqWriter.write(movie);
            seqWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
