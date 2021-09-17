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
        this.movie = movie;
    }

    public IMovie getMovie() {
        return movie;
    }

    public void setComment(String comment) {
        if(comment.length() > IReview.maxCommentLength) {
            throw new IllegalArgumentException("Comment is too long, max comment lengt: " + String.valueOf(Review.maxCommentLength));
        }
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setRating(int rating) {
        if(rating > IReview.maxRating || rating < IReview.minRating) {
            throw new IllegalArgumentException("Rating mut be more than 0 and less than 11.");
        }
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setWhenWatched(LocalDate whenWatched) {
        if(whenWatched.isAfter(LocalDate.now())) {
            throw new IllegalStateException("You cannot set 'when watched' to in the future.");
        }
        this.whenWatched = whenWatched;
    }

    public LocalDate getWhenWatched() {
        return whenWatched;
    }

    public String toString() {
        return("Movie: " + movie.toString() + "\n" + 
            "Rating: " + String.valueOf(rating) + "\n" + 
            "Comment: " + comment + "\n" + 
            "Watched: " + whenWatched.toString());
    }

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(2020, 06, 14);
        String comment1 = "This is a comment";
        int rating1 = 3;

        Review review = new Review(new Movie(), comment1, rating1, date1);
        System.out.println(review.toString());

        LocalDate illegalDate = LocalDate.of(2022, 01, 01);
        int illegalRating1 = 0;
        int illegalRating2 = 15;

        try{
            review.setWhenWatched(illegalDate);
        } catch (IllegalStateException e) {
            System.out.println("Action failed successfully: " + e);
        }

        try{
            review.setRating(illegalRating1);
        } catch (IllegalArgumentException e) {
            System.out.println("Action failed successfully: " + e);
        }

        try{
            review.setRating(illegalRating2);
        } catch (IllegalArgumentException e) {
            System.out.println("Action failed successfully: " + e);
        }
    }
}