package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;
import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;

/**
 * Created by admin on 2/26/18.
 */
public class Shelter implements Serializable {
    private String shelterName;
    private String gender;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private int vacancies;
    private String capacity;
    private long id;
    List<ReservationBarLayout> bars;

    private List<Room> roomList;
    public List<ReservationBarLayout> getBars() {
        return bars;
    }


    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude,  String capacity, int id) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.capacity = capacity;
        this.id = id;
    }

    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude, String capacity) {
        this(shelterName, gender, address, phoneNumber, longitude, latitude, capacity, 0);
    }

    public Shelter() {
        this("","","","",0,0,"",0);
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

    public void setId(long id) {this.id = id;}

    public void setBars(List<ReservationBarLayout> bars) {
        this.bars = bars;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
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

    public long getId() { return id; }

    public List<Room> getRoomList () {
        return this.roomList;
    }

    public String getVacancies() {
        String s = "";
        for (Room r: roomList) {
            s = s + r.toString();
            s = s + ".";
        }
        return s;
    }

    public void reserveRooms(int cap, String type) {
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

    public void releaseReservation(Reservation reservation) {
        // TODO: Release Room based on Reservation object; Maybe write something in the Reservation class that compares roomType
        Room r = reservation.getResRoom();
        for (Room e: roomList) {
            if (e.getRoomType().equals(r.getRoomType())) {
                e.releaseRoom(r.getNumVacancies());
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

    public List<ReservationBarLayout> setBarsList(Context context) {
        bars = new ArrayList<>();
        for (Room room: roomList) {
            ReservationBarLayout rb = new ReservationBarLayout(context, room);
            bars.add(rb);
        }
        // Collaborates with Room objects
        return bars;
    }



    @Override
    public String toString() {
        return this.shelterName;
    }

}
