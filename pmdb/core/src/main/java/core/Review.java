package core;

import java.time.LocalDate;

public class Review implements IReview {

    private String comment;
    private int rating;
    private LocalDate whenWatched;

    public Review(String comment, int rating, LocalDate whenWatched) {
        setComment(comment);
        setRating(rating);
        setWhenWatched(whenWatched);
    }

    public void setComment(String comment) {
        if (!IReview.isValidComment(comment)) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        if (!IReview.isValidRating(rating)) {
            throw new IllegalArgumentException("Rating must be at least 1 and at most 10.");
        }
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setWhenWatched(LocalDate whenWatched) {
        if (!IReview.isValidWhenWatched(whenWatched)) {
            throw new IllegalArgumentException("When Watched cannot be null or in the future.");
        }
        this.whenWatched = whenWatched;
    }

    public LocalDate getWhenWatched() {
        return whenWatched;
    }

    public String toString() {
        return ("Rating: " + String.valueOf(rating) + "\n" + 
            "Comment: " + comment + "\n" + 
            "Watched: " + whenWatched.toString());
    }
}