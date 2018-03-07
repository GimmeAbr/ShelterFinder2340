package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sylvia Li on 2018/3/7.
 */

public class HomelessPerson extends User {
    public HomelessPerson(String userName, String passWord) {
        super(userName, passWord);
    }

    public Review submitReview(int rating, String reviewText, User reviewer) {
        return new Review(rating, reviewText, reviewer);
    }

    public void updateReviewText(Review orReview, String text) {
        orReview.updateReviewContent(text);
    }

    public void updateRating(Review orReview, int rating) {
        orReview.updateRating(rating);
    }
}
