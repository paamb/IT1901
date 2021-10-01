package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MovieList implements Iterable<IMovie>{

    String fileName = "MovieList.json";
    private Collection<IMovie> movieList;

    public MovieList(Collection<IMovie> movieList) {
        this.movieList = new ArrayList<>(movieList);
    }

    public void addMovie(IMovie movie) {
        if(getMovie(movie.getTitle()) == null){
            movieList.add(movie);
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
