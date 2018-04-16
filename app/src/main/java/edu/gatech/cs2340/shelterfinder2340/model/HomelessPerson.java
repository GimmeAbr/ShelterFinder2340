package edu.gatech.cs2340.shelterfinder2340.model;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a homeless person using the app. Extends from User.
 */
public class HomelessPerson extends User {

    private List<Shelter> shelterInterests;
    private String gender;
    private boolean hasReservation;
    private ArrayList<Reservation> reserveList;

    /**
     * HomelessPerson constructor
     * @param name the name of the user
     * @param gender the gender of the user
     * @param uid the id of the user
     */
    public HomelessPerson(String name, String gender, String uid) {
        super(name, "", "", uid);
        this.setGender(gender);
        shelterInterests = new ArrayList<>();
        reserveList = new ArrayList<>();
        this.setAttribute ("Homeless");
    }

    //------------------------------- Getters -------------------------------

    /**
     * Returns the gender of the homeless person
     * @return the homeless person's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns true if the homeless person has rooms reserved at a shelter
     * @return the homeless person's reservation status
     */
    public boolean getHasReservation() {
        return hasReservation;
    }

    /**
     * Gets a list of shelters that the homeless person has marked as interested
     * @return a list of shelters the homeless person is interested in
     */
    public List<Shelter> getShelterInterests() {
        return shelterInterests;
    }

    /**
     * Gets a list of the homeless person's current reservations
     * @return a list of reservation objects the homeless person has made at a shelter
     */
    public List<Reservation> getReserveList() {
        return reserveList;
    }

    /**
     * Sets the homeless person's gender
     * @param gender the gender of the homeless person
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the homeless person's reservation status
     * @param res the homeless person's reservation status
     */
    public void setHasReservation(boolean res) {
        this.hasReservation = res;
    }

    /**
     * Sets the lists of shelters the homeless person is interested in
     * @param shelterList the list of shelters the homeless person is interested in
     */
    public void setShelterInterests(List<Shelter> shelterList) {
        this.shelterInterests = shelterList;
    }

    /**
     * Sets the list of reservations the homeless person has a shelter
     * @param reserveList the list of reservations the homeless person has at a shelter
     */
    public void setReserveList(ArrayList<Reservation> reserveList) {
        this.reserveList = reserveList;
        if (reserveList.size() > 0) {
            hasReservation = true;
        }
    }

    /**
     * Marks that a homeless person is interested in a shelter by adding it to their list
     * @param shelter the shelter the homeless person would like to mark their interest in
     */
    public void markInterest(Shelter shelter) {
        shelterInterests.add(shelter);
    }

    /**
     * Updates a homeless person's rating review of a shelter
     * @param orReview the original review object the homeless person has made for a shelter
     * @param rating the updated rating of the shelter
     */
    public void updateRating(Review orReview, int rating) {
        orReview.setRating(rating);
    }

    /**
     * Updates a homeless person's existing review of a shelter
     * @param orReview the original review object the homeless person has made for a shelter
     * @param text the updated text for the homeless person's review of a shelter
     */
    public void updateReviewText(Review orReview, String text) {
        orReview.setReviewContent(text);
    }

    /**
     * Creates a new review object of a shelter
     * @param rating the integer rating the homeless person has given a shelter
     * @param reviewText the review text the homeless person has given to a shelter
     * @return the review object created from the rating and text the homeless person has provided
     */
    public Review submitReview(int rating, String reviewText) {
        return new Review(rating, reviewText, this, "");
    }

    /**
     * Adds a reservation to a homeless person's list of reservations
     * @param res the reservation to be added to the homeless person's list
     */
    public void addReservation(Reservation res) {
        reserveList.add(res);
        hasReservation = true;
    }

    /**
     * Removes a reservation from the homeless person's list of reservations
     * @param res the reservation to be removed from the homeless person's list
     */
    public void releaseReservation(Reservation res) {
        for (int i = 0; i < reserveList.size(); i++) {
            if(reserveList.get(i).getId().equals(res.getId())) {
                reserveList.remove(i);
            }
        }

        if (reserveList.isEmpty()) {
            hasReservation = false;
        }
    }
}
