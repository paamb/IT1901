package json.moviepersistance;

import com.fasterxml.jackson.databind.module.SimpleModule;
import core.ILabel;
import core.IMovie;
import core.IReview;
import core.MovieList;

/**
 * MovieModule class.
 * 
 * 
 */
public class MovieModule extends SimpleModule {
  /**
   * Initializing the movieModule with Serializers and Deserializers.
   */
  public MovieModule() {
    addDeserializer(IMovie.class, new MovieDeserializer());
    addSerializer(IMovie.class, new MovieSerializer());
    addSerializer(MovieList.class, new MovieListSerializer());
    addDeserializer(MovieList.class, new MovieListDeserializer());
    addSerializer(IReview.class, new ReviewSerializer());
    addDeserializer(ILabel.class, new LabelDeserializer());
    addSerializer(ILabel.class, new LabelSerializer());

  }
}
