package core;

import java.time.LocalDate;

public interface IReview {

    /**
     * A reviews comments max length, used in setComment(String comment)
     */
    public final static int maxCommentLength = 500;

    /**
     * Minimum rating, used in setRating(int rating)
     */
    public final static int minRating = 1;
    
    /**
     * Max rating, used in setRating
     */
    public final static int maxRating = 10;

    /**
     * 
     * @return  The movie of this review
     */
    public IMovie getMovie();

    /**
     * Sets this reviews movie
     * 
     * @param movie
     */
    public void setMovie(IMovie movie);

    /**
     * 
     * @return  This reviews comment
     */
    public String getComment();

    /**
     * Sets this reviews comment
     * 
     * @param comment
     * 
     * @throws IllegalArgumentException when comment is too long
     */
    public void setComment(String comment) throws IllegalArgumentException;

    /**
     * 
     * @return  returns the rating of this reviews movie
     */
    public int getRating();

    /**
     * Sets this movies rating
     * 
     * @param rating
     * @throws IllegalArgumentException when rating is smaller lower than 1 or higher than 10
     */
    public void setRating(int rating) throws IllegalArgumentException;

    /**
     * 
     * @return returns when the movie was watched
     */
    public LocalDate getWhenWatched();

    /**
     * Sets when this movie was watched
     * 
     * @param whenWatched
     * @throws IllegalStateException if the argument is in the future
     */
    public void setWhenWatched(LocalDate whenWatched) throws IllegalStateException;

    /**
     * 
     * @return  returns a stringified version of object
     */
    public String toString();
}
