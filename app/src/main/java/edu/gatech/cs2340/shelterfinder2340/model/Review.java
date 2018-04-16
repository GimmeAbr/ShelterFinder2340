package edu.gatech.cs2340.shelterfinder2340.model;

public class Review {
    private final String shelterName;
    private final User reviewer;
    private int rating;
    private String reviewContent;

    /**
     * Constructor for a Review
     *
     * @param rating int representing rating of shelter
     * @param reviewText string containing review text
     * @param reviewer user who is reviewer
     * @param shelterName string of shelterName
     */
    public Review(int rating, String reviewText, User reviewer, String shelterName) {
        this.rating = rating;
        this.reviewContent = reviewText;
        this.reviewer = reviewer;
        this.shelterName = shelterName;

    }

    /**
     * getter to get rating
     * @return int representing rating
     */
    public int getRating() { return rating; }

    /**
     * getter for review content
     * @return string of review content
     */
    public String getReviewContent() {return reviewContent; }

    /**
     * getter for reviewer
     * @return type user who wrote the review
     */
    public User getReviewer() { return reviewer; }

    /**
     * getter for shelterName
     * @return shelterName in string
     */
    public String getShelterName() { return shelterName; }

    /**
     * setter for rating
     * @param rating int to set rating to
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * setter for reviewContent
     * @param reviewContent string containing review text
     */
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
