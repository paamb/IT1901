package json.moviepersistance;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.MovieList;

public class MovieStorage {
    private String fileName = "MovieList.json";
    private File file;
    private ObjectMapper mapper;

     /**
      * 
     * 
     * @param fileName The name the file you want to save/load from.
     */
    public MovieStorage(){
        file = new File(fileName);
        createObjectMapper();
    }

    /**
     * Sets the storagefile, for loading and saving
     * 
     * @param fileName
     */
    public void setFile(File file){
        if(file == null){
            throw new IllegalArgumentException("FileName cannot be null.");
        }
        this.file = file;
        this.fileName = file.getPath(); 
    }

    /**
      * 
     * 
     * @param movieList
     */
    public void saveMovies(MovieList movieList) throws IOException{
        try{
            FileWriter fileWriter = new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8);
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }

    public MovieList loadMovies() throws IOException{
        if(file.exists() && file.length() != 0){
            try(Reader fileReader = new FileReader(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)){
                return (MovieList) mapper.readValue(fileReader, MovieList.class);
            }
        }else{
            return new MovieList();
        }
    }

    private void createObjectMapper(){
        mapper = new ObjectMapper().registerModule(new MovieModule());
    }

    public ObjectMapper getObjectMapper(){
        return mapper;
    }
}
