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

    public Movie(){
        this.labels = new ArrayList<>();
    }

    public Movie(String title, String description, LocalTime duration, boolean watched){
        this(title, description, duration, watched, new ArrayList<>());
    }

    public Movie(String title, String description, LocalTime duration, boolean watched, Collection<Label> labels){
        setTitle(title);
        setDescription(description);
        setDuration(duration);
        setWatched(watched);
        setLabels(labels);
    }

    /**
     * @param title of the movie
     * @return whether a movie title is longer than 1 and shorter than 50 characters.
     */
    private boolean validMovieTitle(String title){
        return title.length() >= IMovie.minTitleLength && title.length() <= IMovie.maxTitleLength;
    }

    public void setWatched(boolean watched){
        this.watched = watched;
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
            throw new IllegalArgumentException("Illegal movie title");
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
    
        this.labels = new ArrayList<>(labels);
    }

    public Collection<Label> getLabels(){
        if(labels != null){
            return new ArrayList<>(labels);
        }
        return new ArrayList<>();
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
