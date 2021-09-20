package core;

import java.time.LocalTime;
import java.util.Collection;
import java.util.ArrayList;

public class Movie implements IMovie{

    private final String title;
    private final String description;
    private final LocalTime duration;
    private boolean watched;
    private Collection<Label> labels;

    public Movie(String title, String description, LocalTime duration, Collection<Label> lables){
        if (!validMovieTitle(title)){
            throw new IllegalArgumentException("Movie title contains illegal characters");
        }
        this.title = title;
        this.description = description;
        this.duration = duration;
        labels = new ArrayList<>(labels);
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

    public void addLabel(Label label){
        this.labels.add(label);
    }

    public void removeLabel(Label label){
        labels.remove(label);
    }

    public void removeLabel(String label){
        labels.removeIf(x -> x.getLabel().equals(label));
    }

    public Collection<Label> getLabels(){
        return new ArrayList<>(labels);
    }

    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }

    public LocalTime getDuration(){
        return duration;
    }

    @Override
    public String toString() {
        String s = String.format("Movie: %1$s\nDescription: %2$s\nDuration: %3$s\nWatched: %4$s\nLabels: "
                                ,getTitle(),getDescription(),getDuration().toString(),isWatched() ? "Yes" : "No");
        
        for (Label label : labels) {
            s += label.getLabel() + ", ";
        }
        return s.substring(0, s.length()-2);
    }
}
