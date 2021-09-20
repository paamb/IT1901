package json;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import core.Movie;

import com.fasterxml.jackson.databind.ObjectMapper;
public class Storage {
    public void save(Movie movie){
        try {
            // create a map
            Map<String, Object> map = new HashMap<>();
            map.put("name", "John Deo");
            map.put("email", "john.doe@example.com");
            map.put("roles", new String[]{"Member", "Admin"});
            map.put("admin", true);
        
            // create object mapper instance
            ObjectMapper mapper = new ObjectMapper();
        
            // convert map to JSON file
            mapper.writeValue(Paths.get("movies.json").toFile(), movie);
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
