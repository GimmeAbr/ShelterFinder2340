package edu.gatech.cs2340.shelterfinder2340.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylvia Li on 2018/3/7.
 */

public class HomelessPerson extends User {

    private List<Shelter> shelterInterests;
    private Location currentLocation;
    private String gender;
    private boolean hasReservation;
    private Shelter reservedShelter;
    private ArrayList<Room> reserveList;


    //------------------------------- Constructors -------------------------------
    public HomelessPerson( String name, String username, String password, String gender, String id) {
        super(name, username, password, id);
        this.setGender(gender);
        shelterInterests = new ArrayList<Shelter>();
    }
    public HomelessPerson(String name, String gender, String uid) {
        this(name,"","", gender, uid);
    }
    public HomelessPerson(String username, String password) {
        this("", username, password,"", "");
    }

    //------------------------------- Getters -------------------------------
    public String getGender() {
        return gender;
    }
    public boolean hasReservation() {
        return hasReservation;
    }
    public Shelter getReservedShelter() { return reservedShelter; }
    public List<Shelter> getShelterInterests() {
        return shelterInterests;
    }
    public Location getCurrentLocation() { return currentLocation; }

    //------------------------------- Setters -------------------------------
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setReservation(boolean res) {
        this.hasReservation = res;
    }
    public void setReservedShelter(Shelter shelter) { this.reservedShelter = shelter;}
    public void setShelterInterests(List<Shelter> shelterList) { this.shelterInterests = shelterList; }
    public void setCurrentLocation(Location currentLocation) { this.currentLocation = currentLocation; }


    //------------------------------- Actions -------------------------------
    public void markInterest(Shelter shelter) {
        shelterInterests.add(shelter);
    }
    public void updateRating(Review orReview, int rating) {
        orReview.setRating(rating);
    }
    public void updateReviewText(Review orReview, String text) {
        orReview.setReviewContent(text);
    }
    public Review submitReview(int rating, String reviewText) { return new Review(rating, reviewText, this, "");}


    public void reserveRoom(int num, String type, String shelterName) {
        if (num > 0) {
            Room reserve = new Room(num, type, shelterName);
            reserveList.add(reserve);
        }
    }

    public List<Room> getReserveList() {
        return reserveList;
    }

    public void releaseRooms() {
        reserveList.clear();
    }
}
