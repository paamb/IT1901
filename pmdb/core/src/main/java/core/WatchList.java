package core;

import java.util.ArrayList;
import java.util.Collection;

public class WatchList {
    private Collection<Movie> movies;

    public void addMovie(Movie movie){
        movies.add(movie);
    }
    public void removeMovie(Movie movie){
        movies.remove(movie);
    }
    public Collection<Movie> getMovies(){
        return new ArrayList<>(movies);
    }
}
