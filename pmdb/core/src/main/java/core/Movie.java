package core;

import java.time.LocalTime;
import java.util.Collection;
import java.util.ArrayList;

public class Movie implements IMovie{

    private String title;
    private String description;
    private LocalTime duration;
    private boolean watched;
    private Collection<IReview> reviews;

    public Movie(String title, String description, LocalTime duration, boolean watched, Collection<IReview> reviews){
        setTitle(title);
        setDescription(description);
        setDuration(duration);
        setWatched(watched);
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
        
        if (reviews.size() == 0){
            return s;
        }

        else{
            s += "\nRating from reviews: ";
            for (IReview review : reviews) {
                s += review.getRating() + ", ";
            }
        }
        
        return s.substring(0, s.length()-2);
    }
}
