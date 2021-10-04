package core;

import java.time.LocalDate;

public class Review implements IReview {

    private IMovie movie;
    private String comment;
    private int rating;
    private LocalDate whenWatched;

    public Review(IMovie movie, String comment, int rating, LocalDate whenWatched) {
        setMovie(movie);
        setComment(comment);
        setRating(rating);
        setWhenWatched(whenWatched);
    }

    public void setMovie(IMovie movie) {
        if (movie == null) {
            throw new IllegalStateException("Movie cannot be null");
        }
        this.movie = movie;
    }

    public IMovie getMovie() {
        return movie;
    }

    public void setComment(String comment) {
        if (comment.length() > IReview.maxCommentLength) {
            throw new IllegalArgumentException(
                    "Comment is too long, max comment lengt: " + String.valueOf(IReview.maxCommentLength));
        }
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        if (rating > IReview.maxRating || rating < IReview.minRating) {
            throw new IllegalArgumentException("Rating must be more than 0 and less than 11.");
        }
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setWhenWatched(LocalDate whenWatched) {
        if (whenWatched.isAfter(LocalDate.now())) {
            throw new IllegalStateException("You cannot set 'when watched' to in the future.");
        }
        this.whenWatched = whenWatched;
    }

    public LocalDate getWhenWatched() {
        return whenWatched;
    }

    public String toString() {
        return ("Movie: " + movie.toString() + "\n" + 
            "Rating: " + String.valueOf(rating) + "\n" + 
            "Comment: " + comment + "\n" + 
            "Watched: " + whenWatched.toString());
    }
}