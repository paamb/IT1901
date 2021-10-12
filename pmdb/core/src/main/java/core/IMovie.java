package core;

import java.time.LocalTime;
import java.util.Collection;

public interface IMovie {

    /**
     * 
     * @param title
     * @return true if title is valid
     */
    public static boolean isValidTitle(String title){
        return (title.length() >= 1 && title.length() <= 50)
            && (title != null);
    }

    /**
     * 
     * @param description
     * @return true if description is valid
     */
    public static boolean isValidDescription(String description){
        return description != null;
    }

    /**
     * 
     * @param duration
     * @return true if duration is valid
     */
    public static boolean isValidDuration(LocalTime duration){
        return duration != null;
    }    

    /**
     * Sets if movie is watched or not
     * 
     * @param watched
     */
    public void setWatched(boolean watched);

    /**
     * 
     * @return whether movie is watched or not
     */
    public boolean isWatched();

    /**
     * Sets this movies description
     * 
     * @param description
     */
    public void setDescription(String description);

    /**
     * 
     * @return this movies description
     */
    public String getDescription();

    /**
     * Sets this movies title
     * 
     * @param title
     * @throws IllegalArgumentException if illegal title
     */
    public void setTitle(String title) throws IllegalArgumentException;

    /**
     * 
     * @return this movies title
     */
    public String getTitle();

    /**
     * sets this movies duration
     * 
     * @param duration
     */
    public void setDuration(LocalTime duration);

    /**
     * 
     * @return this movies duration
     */
    public LocalTime getDuration();

    /**
     * Adds a label to this movie
     * 
     * @param label
     * @throws IllegalStateException if label already is added
     */
    public void addLabel(Label label) throws IllegalStateException;

    /**
     * Removes a label from this movie, if the label is added
     * 
     * @param label
     */
    public void removeLabel(Label label);

    /**
     * Sets a Collection of this movies labels, overrides existing labels
     * 
     * @param labels
     */
    public void setLabels(Collection<Label> labels);

    /**
     * 
     * @return a Collection of this movies labels
     */
    public Collection<Label> getLabels();

    /**
     * Sets a Collection of reviews
     * 
     * @param reviews
     */
    public void setReviews(Collection<IReview> reviews);

    /**
     * Adds a review to this movie
     * 
     * @param review
     */
    public void addReview(IReview review);

    /**
     * Removes a review
     * 
     * @param review
     */
    public void removeReview(IReview review);

    /**
     * 
     * @return a collection of this movies reviews
     */
    public Collection<IReview> getReviews();
}
