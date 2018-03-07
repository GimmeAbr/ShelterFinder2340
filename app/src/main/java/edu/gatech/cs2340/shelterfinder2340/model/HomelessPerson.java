package edu.gatech.cs2340.shelterfinder2340.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylvia Li on 2018/3/7.
 */

public class HomelessPerson extends User {

    private List<Shelter> shelterList;
    private Location currentLocation;
    public HomelessPerson(String userName, String passWord) {
        super(userName, passWord);
        shelterList = new ArrayList<>();
    }

    public List<Shelter> getShelterList() {
        return shelterList;
    }

    public void setShelterList(List<Shelter> shelterList) {
        this.shelterList = shelterList;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
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

    public void markInterest(Shelter shelter) {
        shelterList.add(shelter);
    }

}
