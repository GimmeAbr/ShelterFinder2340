package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Sebastian Hollister on 2/26/2018.
 */

public class Review {
    private String shelterName;
    public String getShelterName() { return shelterName; }

    private User reviewer;
    public User getReviewer() { return reviewer; }

    private int rating;
    public int getRating() { return rating; }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    private String reviewContent;
    public String getReviewContent() {return reviewContent; }

    public Review(int rating, String reviewText, User reviewer) {
        this.rating = rating;
        this.reviewContent = reviewText;
        this.reviewer = reviewer;
    }

//    public void updateReviewContent(String text) {this.reviewContent = text;}
//    public void updateRating(int rating) { this.rating = rating; }
}
