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
    private Collection<IReview> reviews;

    public Movie(){
        this.labels = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Movie(String title, String description, LocalTime duration, boolean watched, Collection<IReview> reviews){
        this(title, description, duration, watched, new ArrayList<>(), reviews);
    }

    public Movie(String title, String description, LocalTime duration, boolean watched, Collection<Label> labels, Collection<IReview> reviews){
        setTitle(title);
        setDescription(description);
        setDuration(duration);
        setWatched(watched);
        setLabels(labels);
        setReviews(reviews);
    }

    public void setWatched(boolean watched){
        this.watched = watched;
    }

    public boolean isWatched(){
        return watched;
    }

    public void setDescription(String description){
        if(!IMovie.isValidDescription(description)){
            throw new IllegalArgumentException("Illegal movie description");
        }
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setTitle(String title){
        if (!IMovie.isValidTitle(title)){
            throw new IllegalArgumentException("Illegal movie title");
        }
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDuration(LocalTime duration){
        if(!IMovie.isValidDuration(duration)){
            throw new IllegalArgumentException("Illegal movie duration");
        }
        this.duration = duration;
    }

    public LocalTime getDuration(){
        return duration;
    }

    public void setLabels(Collection<Label> labels){
        for (Label label : labels){
            int count = 0;
            for (Label label2 : labels){
                if (label == label2){
                    count++;
                }
                if (count > 1){
                    throw new IllegalArgumentException("Duplicate labels not allowed");
                }
            }
        }
        this.labels = new ArrayList<>(labels);
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

    public Collection<Label> getLabels(){
        return new ArrayList<>(labels);
    }

    public void setReviews(Collection<IReview> reviews) {
        for(IReview review : reviews){
            int count = 0;
            for(IReview review2 : reviews){
                if(review == review2){
                    count++;
                }
                if (count > 1){
                    throw new IllegalArgumentException("Duplicate reviews not allowed");
                }
            }
        }
        this.reviews = new ArrayList<>(reviews);
    }

    public void addReview(IReview review){
        if(review == null){
            throw new IllegalArgumentException("Review cannot be null");
        }
        if(reviews.contains(review)){
            throw new IllegalStateException("Duplicate review not allowed");
        }
        reviews.add(review);
    }

    public void removeReview(IReview review){
        reviews.remove(review);
    }

    public Collection<IReview> getReviews(){
        return new ArrayList<>(reviews);
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
