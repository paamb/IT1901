package core;
import java.util.Collection;
import java.util.ArrayList;

public class Label {

    private final String label;
    private String color;
    private String description;
    private Collection<Movie> movies;

    public Label(String label, String color, String description, Collection<Movie> movies){
        
        if (!validLabel(label)){
            throw new IllegalArgumentException("Invalid label name, can only contain letters");
        }
        this.label = label;
        this.color = color;
        this.description = description; 
        this.movies = new ArrayList<>(movies);
    }

    /**
     * 
     * @param label
     * @return wheter label is valid. Can only contain letters.
     */
    private boolean validLabel(String label){
        return label.matches("[A-Za-z]+");
    }

    public String (){
        return label;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Collection<Movie> getMovies(){
        return new ArrayList<>(movies);
    }

    public void removeMovie(Movie movie){
        movies.remove(movie);
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }
}
