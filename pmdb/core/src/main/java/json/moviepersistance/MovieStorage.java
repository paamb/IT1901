package json.moviepersistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.LabelList;
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
  // private ObjectMapper labelmapper;

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
        new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8, true)) {
      mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
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

  // public void saveLabelList(LabelList labelList) throws IOException {
  // try (FileWriter fileWriter =
  // new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)) {
  // mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, movieList);
  // }
  // }


  private void createObjectMapper() {
    mapper = new ObjectMapper().registerModule(new MovieModule());
    // labelmapper = new ObjectMapper().re
  }

  public ObjectMapper getObjectMapper() {
    return mapper;
  }
}
