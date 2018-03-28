package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;

/**
 * Created by admin on 2/26/18.
 */

public class Shelter{
    private String shelterName, gender, address, phoneNumber, capacity;
    private double longitude, latitude;
    private int vacancies, id;
    private List<Room> roomList;

    public List<ReservationBarLayout> getBars() {
        return bars;
    }

    ArrayList<ReservationBarLayout> bars;

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
        roomList = new ArrayList<>();
        setInitialRooms();
    }

    public Shelter (String shelterName, String gender, String capacity, String address, String phoneNumber, double longitude, double latitude) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.capacity = capacity;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        roomList = new ArrayList<>();
        setInitialRooms();
    }

    public void setRoomList(List<Room> r) {
        roomList.addAll(r);
    }

    public List<Room> getRoomList() {
        return roomList;
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

    public List<ReservationBarLayout> setBarsList(Context context) {
        bars = new ArrayList<>();
        for (Room room: roomList) {
            ReservationBarLayout rb = new ReservationBarLayout(context, room);
            bars.add(rb);
        }
        // Collaborates with Room objects
        return bars;
    }

    public String getVacancies() {
        String s = "";
        for (Room r: roomList) {
            s = s + r.toString();
            s = s + ".";
        }
        return s;
    }

    public void updateVacancies(int cap, String type) {
        for (Room r: roomList) {
            if (r.getRoomType().equals(type)) {
                r.reserveRoom(cap);
                return;
            }
        }
    }

    public void releaseByList(List<Room> resList) {
        for (Room r: resList) {
            for (Room e: roomList) {
                if (e.getRoomType().equals(r.getRoomType())) {
                    e.releaseRoom(r.getNumVacancies());
                }
            }
        }
    }

    public boolean reservedOut() {
        boolean ro = true;
        for (Room r: roomList) {
            ro = ro && (r.reservedOut());
        }
        return ro;
    }

    // ///////////////////////////////////Deprecate after firestore works
    public void setInitialRooms() {
        roomList.add(new Room(10, gender.toUpperCase(), shelterName));
    }
///////////////////////////////////////////////////////////////////////////////
}
