package core;

import java.time.LocalTime;
import java.util.Collection;

public interface IMovie {
    /**
     * Minimum title length
     */
    public final static int minTitleLength = 1;

    /**
     * Maximum title length
     */
    public final static int maxTitleLength = 50;

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
}
