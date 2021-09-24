package core;

import java.util.ArrayList;
import java.util.Collection;

import json.Storage;

public class WatchList {

    String fileName = "WatchList.json";
    private Collection<Movie> movieList;
    private Storage storage;
    private Collection<Movie> deserializedMovieList;

    public WatchList(){
        storage = new Storage(fileName);
        deserializedMovieList = storage.load();
        movieList = new ArrayList<>(deserializedMovieList);
    }

    public void addMovie(Movie movie){
        if(getMovie(movie.getTitle()) == null){
            movieList.add(movie);
            storage.save(getMovies());
    } else {
        throw new IllegalStateException("This movie-title is already in use.");
    }
    
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
    /**
     * 
     * @param title
     * @return Movie with matching title, if there is no such movie, return null
     */
    public Movie getMovie(String title){
        return movieList.stream()
            .filter(m -> m.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String toString() {
        String returnString = "";
        for (Movie movie : movieList) {
            returnString += movie.getTitle() + "\n";
        }
        return returnString;
    }
}
