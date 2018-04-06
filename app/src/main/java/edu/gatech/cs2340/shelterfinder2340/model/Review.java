package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sebastian Hollister on 2/26/2018.
 */

public class Review {
    private String shelterName;
    private User reviewer;
    private int rating;
    private String reviewContent;

    Review(int rating, String reviewText, User reviewer, String shelterName) {
        this.rating = rating;
        this.reviewContent = reviewText;
        this.reviewer = reviewer;
        this.shelterName = shelterName;
    }

    public int getRating() { return rating; }
    public String getReviewContent() {return reviewContent; }
    public User getReviewer() { return reviewer; }
    public String getShelterName() { return shelterName; }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
