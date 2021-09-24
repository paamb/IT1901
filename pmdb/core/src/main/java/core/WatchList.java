package core;

import java.util.ArrayList;
import java.util.Collection;

public class WatchList {
    private Collection<Movie> movies = new ArrayList<Movie>();

    public void addMovie(Movie movie){
        if(getMovie(movie.getTitle()) == null){
            movies.add(movie);
        } else {
            throw new IllegalStateException("This movie-title is already in use.");
        }
    }
    public void removeMovie(Movie movie){
        movies.remove(movie);
    }
    public Collection<Movie> getMovies(){
        return new ArrayList<Movie>(movies);
    }
    /**
     * 
     * @param title
     * @return Movie with matching title, if there is no such movie, return null
     */
    public Movie getMovie(String title){
        return movies.stream()
            .filter(m -> m.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }
}
