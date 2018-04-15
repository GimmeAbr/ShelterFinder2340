package edu.gatech.cs2340.shelterfinder2340.model;

public class Review {
    private final String shelterName;
    private final User reviewer;
    private int rating;
    private String reviewContent;

    public Review(int rating, String reviewText, User reviewer, String shelterName) {
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
