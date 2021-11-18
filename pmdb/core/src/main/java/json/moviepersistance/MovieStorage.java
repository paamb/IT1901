package json.moviepersistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.MovieList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * MovieStorage class.
 * 
 * 
 */
public class MovieStorage {
  private ObjectMapper mapper;
  private Path filePath = null;

  /**
   * The movie storage initialization. Creating a new file and creating the object mapper.
   */
  public MovieStorage() {
    setObjectMapper();
  }

  /**
   * Sets filepath given String.
   * 
   * @param filePath the string to be appended to path.
   */
  public void setFilePath(String filePath) {
    if (filePath.isEmpty()) {
      throw new IllegalArgumentException("FileName cannot be null.");
    }
    this.filePath = Paths.get(System.getProperty("user.home"), filePath);
  }

  /**
   * Sets filepath given File object.
   * 
   * @param file the file to add to path.
   */
  public void setFilePath(File file) {
    if (file == null || file.getName().isEmpty()) {
      throw new IllegalArgumentException("FileName cannot be null.");
    }
    this.filePath = file.toPath();
  }

  /**
   * Saves the movie list to json file.
   * 
   * @param movieList the movielist object to be saved.
   */
  public void saveMovieList(MovieList movieList) throws IOException, IllegalStateException {
    if (this.filePath == null) {
      throw new IllegalStateException("Filepath is not set.");
    }
    try (FileWriter fileWriter = new FileWriter(filePath.toFile(), StandardCharsets.UTF_8)) {
      mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Loads movielist from json file. If the json file does not exist it returns an empty movieList.
   * 
   * @return a new movie list.
   * @throws IOException when reading from json file fails.
   */
  public MovieList loadMovieList() throws IOException {
    try (Reader fileReader = new FileReader(filePath.toFile(), StandardCharsets.UTF_8)) {
      return mapper.readValue(fileReader, MovieList.class);
    } catch (FileNotFoundException e) {
      return new MovieList();
    }
  }

  public MovieList readMovieList(Reader reader) throws IOException {
    return mapper.readValue(reader, MovieList.class);
  }

  public static ObjectMapper createObjectMapper() {
    return new ObjectMapper().registerModule(new MovieModule());
  }

  private void setObjectMapper() {
    mapper = createObjectMapper();
  }

  public ObjectMapper getObjectMapper() {
    return mapper;
  }
}
