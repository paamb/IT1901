package json;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import core.IMovie;
import core.MovieList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

public class Storage {
    private String fileName;
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
    // public void save(Collection<IMovie> movieList){
    //     try {
    //         ObjectMapper mapper = new ObjectMapper();
    //         FileWriter fileWriter = new FileWriter(file, false);
    //         SequenceWriter seqWriter = mapper.writer().writeValuesAsArray(fileWriter);

    //         for(IMovie storedMovie : movieList){
    //             seqWriter.write(storedMovie);
    //         }

    //         seqWriter.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public void saveMovies(MovieList movieList) throws IOException{
        try{
            FileWriter fileWriter = new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8);
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
        }catch(Exception e ){
            e.printStackTrace();
        }

    }
    public MovieList loadMovies() throws IOException{
        if(file.exists()){
            try(Reader fileReader = new FileReader(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)){
                return mapper.readValue(fileReader, MovieList.class);
            }
        }else{
            //TODO
            return new MovieList(new ArrayList<>());
        }
    }

    private void createObjectMapper(){
        mapper = new ObjectMapper().registerModule(new MovieModule());
    }

    public ObjectMapper getObjectMapper(){
        return mapper;
    }
}
