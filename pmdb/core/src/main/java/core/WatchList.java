package core;

import java.util.ArrayList;
import java.util.Collection;

import json.Storage;

public class WatchList {

    private Collection<Movie> movieList;
    private Storage storage;
    public WatchList(){
        storage = new Storage();
        Collection<Movie> deserializedMovieList = storage.load(this);
        movieList = new ArrayList<>(deserializedMovieList);
    }

    public void addMovie(Movie movie){
        movieList.add(movie);
        storage.save(this);
    }
    public void removeMovie(Movie movie){
        movieList.remove(movie);
    }
    public Collection<Movie> getMovies(){
        return new ArrayList<>(movieList);
    }
    public void clearMovieList(){
        movieList.clear();
    }
}
