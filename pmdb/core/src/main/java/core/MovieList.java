package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import json.Storage;

public class MovieList implements Iterable<IMovie>{

    String fileName = "MovieList.json";
    private Collection<IMovie> movieList;
    private Storage storage;
    private Collection<IMovie> deserializedMovieList;

    public MovieList() {
        storage = new Storage(fileName);
        deserializedMovieList = storage.load();
        movieList = new ArrayList<>(deserializedMovieList);
    }

    public void addMovie(IMovie movie) {
        if(getMovie(movie.getTitle()) == null){
            movieList.add(movie);
            storage.save(getMovies());
    } else {
        throw new IllegalStateException("This movie-title is already in use.");
    }
    }

    public void removeMovie(IMovie movie){
        movieList.remove(movie);
    }

    public Collection<IMovie> getMovies(){
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
    public IMovie getMovie(String title){
        return movieList.stream()
            .filter(m -> m.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String toString() {
        String returnString = "";
        for (IMovie movie : movieList) {
            returnString += movie.getTitle() + "\n";
        }
        return returnString;
    }

    @Override
    public Iterator<IMovie> iterator() {
        return movieList.iterator();
    }
}
