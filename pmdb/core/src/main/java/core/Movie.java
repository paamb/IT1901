package core;

import java.time.LocalTime;
import java.util.Collection;
import java.util.ArrayList;

public class Movie implements IMovie{

    private String title;
    private String description;
    private LocalTime duration;
    private boolean watched;
    private Collection<Label> labels;

    public Movie(String title, String description, LocalTime duration, Collection<Label> lables){
        if (!validMovieTitle(title)){
            throw new IllegalArgumentException("Movie title contains illegal characters");
        }
        setTitle(title);
        setDescription(description);
        setDuration(duration);
        setLabels(lables);
    }

    /**
     * @param title of the movie
     * @return whether movie title is valid. Can only contain letters, numbers, and special characters "?", "." and "!".
     */
    private boolean validMovieTitle(String title){
        return title.matches("[A-Za-z0-9?.!]+");
    }

    public void setWatched(){
        this.watched = true;
    }

    public boolean isWatched(){
        return watched;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDuration(LocalTime duration){
        this.duration = duration;
    }

    public LocalTime getDuration(){
        return duration;
    }
    public void addLabel(Label label){
        this.labels.add(label);
    }

    public void removeLabel(Label label){
        labels.remove(label);
    }

    public void removeLabel(String label){
        labels.removeIf(x -> x.getLabel().equals(label));
    }

    public void setLabels(Collection<Label> labels){
        this.labels = new ArrayList<>(labels);
    }

    public Collection<Label> getLabels(){
        return new ArrayList<>(labels);
    }

    @Override
    public String toString() {
        String s = String.format("Movie: " + getTitle() + "\n" + 
                                "Description: " + getDescription() + "\n"+
                                "Duration: " + getDuration().toString() + "\n"+
                                "Watched: " + (isWatched() ? "Yes" : "No"));
        
        if (labels.size() == 0){
            return s;
        }

        else{
            s += "\nLabels: ";
            for (Label label : labels) {
                s += label.getLabel() + ", ";
            }
        }
        
        return s.substring(0, s.length()-2);
    }
    public static void main(String[] args) {
        LocalTime time = LocalTime.of(2, 30);
        Collection<Label> labels = new ArrayList<>();
        Movie movie = new Movie("Interstellar", "Mann paa planet", time, labels);
        Label mlabel = new Label("Ai");
        Label xlabel = new Label("Au");
        movie.addLabel(mlabel);
        movie.addLabel(xlabel);
        System.out.println(movie);
    }
}
