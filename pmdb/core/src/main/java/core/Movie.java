package core;

import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Movie implements IMovie{

    private String title;
    private String description;
    private LocalTime duration;
    private boolean watched;
    private Collection<Label> labels;

    public Movie(String title, String description, LocalTime duration, Collection<Label> labels){
        setTitle(title);
        setDescription(description);
        setDuration(duration);
        setLabels(labels);
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
        if (!validMovieTitle(title)){
            throw new IllegalArgumentException("Movie title contains illegal characters");
        }
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
        if(labels.contains(label)) {
            throw new IllegalStateException("Duplicate labels not allowed");
        }
        labels.add(label);
    }

    public void removeLabel(Label label){
        labels.remove(label);
    }

    public void setLabels(Collection<Label> labels){
        for (Label label1 : labels) {
            int count = 0;
            for (Label label2 : labels) {
                if(label1 == label2) {
                    count++;
                }
                if(count > 1) {
                    throw new IllegalArgumentException("Duplicate labels not allowed");
                }
            }
        }
    
        this.labels = new ArrayList<Label>(labels);
    }

    public Collection<Label> getLabels(){
        return new ArrayList<>(labels);
    }

    @Override
    public String toString() {
        String s = "Movie: " + getTitle() + "\n" + 
                    "Description: " + getDescription() + "\n"+
                    "Duration: " + getDuration().toString() + "\n"+
                    "Watched: " + (isWatched() ? "Yes" : "No");
        
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
}
