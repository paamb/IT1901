package json.moviepersistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.MovieList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * MovieStorage class.
 * 
 * 
 */
public class MovieStorage {
  private String fileName = "MovieList.json";
  private File file;
  private ObjectMapper mapper;

  /**
   * The movie storage initialization. Creating a new file and creating the object mapper.
   */
  public MovieStorage() {
    file = new File(fileName);
    createObjectMapper();
  }

  /**
   * Sets the storagefile, for loading and saving.
   * 
   * @param file the file to be set
   */
  public void setFile(File file) {
    if (file == null) {
      throw new IllegalArgumentException("FileName cannot be null.");
    }
    this.file = file;
    this.fileName = file.getPath();
  }

  /**
   * Saves the movie list to json file.
   * 
   * @param movieList the movielist object to be saved.
   */
  public void saveMovieList(MovieList movieList) throws IOException {
    try (FileWriter fileWriter =
        new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)) {
      mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Loads movielist from json file.
   * 
   * @return a new movie list.
   * @throws IOException when reading from json file fails.
   */
  public MovieList loadMovieList() throws IOException {
    if (file.exists() && file.length() != 0) {
      try (Reader fileReader =
          new FileReader(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)) {
        return (MovieList) mapper.readValue(fileReader, MovieList.class);
      }
    } else {
      return new MovieList();
    }
  }

  public MovieList readMovieList(Reader reader) throws IOException {
    return mapper.readValue(reader, MovieList.class);
  }

  // private void setObjectMapper() {
  //   mapper = new ObjectMapper().registerModule(new MovieModule());
  // }


  public void createObjectMapper() {
    mapper = new ObjectMapper().registerModule(new MovieModule());
  }

  public ObjectMapper getObjectMapper() {
    return mapper;
  }
}
