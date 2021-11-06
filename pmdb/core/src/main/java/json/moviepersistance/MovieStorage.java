package json.moviepersistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ILabel;
import core.IMovie;
import core.IReview;
import core.Label;
import core.Movie;
import core.MovieList;
import core.Review;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
        new FileWriter(Paths.get(fileName).toFile(), StandardCharsets.UTF_8)) {
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

  private void createObjectMapper() {
    mapper = new ObjectMapper().registerModule(new MovieModule());
    // labelmapper = new ObjectMapper().re
  }

  public ObjectMapper getObjectMapper() {
    return mapper;
  }

  public static void main(String[] args) throws IOException {
    MovieList movieList = new MovieList();
    ArrayList<IReview> reviews =
        new ArrayList<IReview>(Arrays.asList(new Review("Teit", 1, LocalDate.of(2000, 1, 1)),
            new Review("Bra", 8, LocalDate.of(2001, 2, 2))));
    Movie movie1 =
        new Movie("Up", "Komedie", 1, true, Arrays.asList(reviews.get(0)), Arrays.asList());
    Movie movie2 =
        new Movie("Batman", "Action", 43, false, Arrays.asList(reviews.get(1)), Arrays.asList());
    movie1.addLabel(new Label("Action", "#FFFFFF"));
    movie1.addLabel(new Label("Crime", "#EEEEEE"));
    movieList.addMovie(movie1);
    movieList.addMovie(movie2);

    MovieStorage storage = new MovieStorage();
    storage.saveMovieList(movieList);

    MovieList movieList2 = storage.loadMovieList();

    for (IMovie movie : movieList.getMovies()) {
      for (ILabel label : movie.getLabels()) {
        System.out.println(label.getTitle());
      }
    }
  }
}
