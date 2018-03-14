package edu.gatech.cs2340.shelterfinder2340.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2/26/18.
 */

public class Shelter{
    private String shelterName, gender, address, phoneNumber, capacity;
    private double longitude, latitude;
    private int vacancies, id;

    public Shelter() {

    }

    public Shelter (String shelterName, String gender, String capacity, String address, String phoneNumber, double longitude, double latitude, int id) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.id = id;
    }

    public Shelter (String shelterName, String gender, String capacity, String address, String phoneNumber, double longitude, double latitude) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
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

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public String getCapacity() {
        return capacity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId() { return id; }



    @Override
    public String toString() {
        return this.shelterName;
    }

}
