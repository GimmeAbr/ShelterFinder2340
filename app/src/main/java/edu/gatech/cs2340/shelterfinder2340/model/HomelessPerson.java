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
    private String gender;
    private String name;
    private boolean res;


    public HomelessPerson(long uid, String gender, String name) {
        super(uid);
        this.gender = gender;
        this.name = name;
        this.res = true;
        shelterList = new ArrayList<>();
    }
    public HomelessPerson(String userName, String passWord) {
        super(userName, passWord, 0);
        shelterList = new ArrayList<>();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
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
