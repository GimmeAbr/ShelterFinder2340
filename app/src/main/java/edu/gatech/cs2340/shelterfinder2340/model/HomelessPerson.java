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
    public HomelessPerson(String userName, String passWord, String name) {
        super(userName, passWord, name);
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


    public Review submitReview(int rating, String reviewText) {
        return new Review(rating, reviewText, this);
    }

    public void updateReviewText(Review orReview, String text) {
        orReview.setReviewContent(text);
    }

    public void updateRating(Review orReview, int rating) {
        orReview.setRating(rating);
    }

    public void markInterest(Shelter shelter) {
        shelterList.add(shelter);
    }

}
