package core;

import java.util.ArrayList;
import java.util.Collection;

import json.Storage;

public class WatchList {
    private Collection<Movie> movies;
    private Storage storage;
    public WatchList(){
        storage = new Storage();
        movies = new ArrayList<>();
    }

    public void addMovie(Movie movie){
        movies.add(movie);
        storage.save(movie);
    }
    public void removeMovie(Movie movie){
        movies.remove(movie);
    }
    public Collection<Movie> getMovies(){
        return new ArrayList<>(movies);
    }
}
