package json;

import java.nio.file.Paths;

import core.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;
public class Storage {
    public void save(Movie movie){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Paths.get("movies.json").toFile(), movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
