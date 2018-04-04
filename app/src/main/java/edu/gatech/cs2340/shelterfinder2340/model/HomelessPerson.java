package edu.gatech.cs2340.shelterfinder2340.model;

import android.location.Location;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sylvia Li on 2018/3/7.
 */

public class HomelessPerson extends User {

    private List<Shelter> shelterInterests;
    private String gender;
    private boolean hasReservation;

    private ArrayList<Reservation> reserveList;


    //------------------------------- Constructors -------------------------------
    public HomelessPerson( String name, String username, String password, String gender, String id) {
        super(name, username, password, id);
        this.setGender(gender);
        shelterInterests = new ArrayList<Shelter>();
        reserveList = new ArrayList<Reservation>();
        this.setAttribute("Homeless");
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
    public boolean getHasReservation() {
        return hasReservation;
    }
    public List<Shelter> getShelterInterests() {
        return shelterInterests;
    }
    public List<Reservation> getReserveList() {
        return reserveList;
    }

    //------------------------------- Setters -------------------------------
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setHasReservation(boolean res) {
        this.hasReservation = res;
    }
    public void setShelterInterests(List<Shelter> shelterList) { this.shelterInterests = shelterList; }
    public void setReserveList(ArrayList<Reservation> reserveList) {
        this.reserveList = reserveList;
    }

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

    public void addReservation(Reservation res) {
        reserveList.add(res);
    }

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

    public void releaseAllReservations() {
        reserveList.clear();
        hasReservation = false;
    }
}
