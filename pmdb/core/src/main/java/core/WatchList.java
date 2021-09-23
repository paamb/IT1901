package core;

import java.util.ArrayList;
import java.util.Collection;

import java.time.LocalTime;
import java.util.Arrays;


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
    public Movie getMovie(String title){
        // for (Movie movie : movies) {
        //     if(movie.getTitle().equals(title)){
        //         return movie;
        //     }
        // }
        return movies.stream().filter(m -> m.getTitle().equals(title)).findAny().get();
    }

    public static void main(String[] args) {
        String title1 = "title1";
        String title2 = "title2";
        String description = "initDescription";
        LocalTime duration = LocalTime.of(02, 00);
        Label label = new Label("initLabel");
        Collection<Label> labels = new ArrayList<Label>(Arrays.asList(label));

        Movie movie1 = new Movie(title1, description, duration, labels);
        Movie movie2 = new Movie(title2, description, duration, labels);
    }
}
