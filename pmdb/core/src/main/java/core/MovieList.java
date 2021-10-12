package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MovieList implements Iterable<IMovie>{

    private Collection<IMovie> movieList;
    
    public MovieList() {
        movieList = new ArrayList<>();
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

    public Collection<IMovie> getSortedMoviesByTitle(Collection<IMovie> movies){
        List<IMovie> sortedMovieList = new ArrayList<>(movies);
        Collections.sort(sortedMovieList, new MovieTitleComparator());
        return sortedMovieList;
    }
    
    public Collection<IMovie> getSortedMoviesOnSeen(Collection<IMovie> movies){
        List<IMovie> sortedMovieList = new ArrayList<>(movies);
        Collections.sort(sortedMovieList, new MovieSeenComparator());
        return sortedMovieList;
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
