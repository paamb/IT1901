package json.moviepersistance;


import com.fasterxml.jackson.databind.module.SimpleModule;
import core.IMovie;
import core.IReview;
import core.MovieList;

public class MovieModule extends SimpleModule{
    public MovieModule(){
        addDeserializer(IMovie.class, new MovieDeserializer());
        addSerializer(IMovie.class, new MovieSerializer());
        addSerializer(MovieList.class, new MovieListSerializer());
        addDeserializer(MovieList.class, new MovieListDeserializer());
        addSerializer(IReview.class, new ReviewSerializer());
    }
}
