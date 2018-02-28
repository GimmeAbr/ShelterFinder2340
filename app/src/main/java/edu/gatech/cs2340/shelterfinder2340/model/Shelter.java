package edu.gatech.cs2340.shelterfinder2340.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by admin on 2/26/18.
 */

public class Shelter {
    private String shelterName, gender, address;
    private int capacity, phoneNumber;
    private double longitude, latitude;

    public Shelter (String shelterName, String gender, int capacity, int phoneNumber, double longitude, double latitude) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //Setters
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    //Getters
    public String getShelterName() {
        return shelterName;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return shelterName;
    }

}
