package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;
import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;

/**
 * Created by admin on 2/26/18.
 */
public class Shelter{
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

    private ArrayList<Room> roomList;
    private List<Reservation> reserveList;
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

    public String getVacancies() {
        String s = "";
        for (Room r: roomList) {
            s = s + r.toString();
            s = s + ".";
        }
        return s;
    }

    public void reserveBedss(int cap, String type) {
        for (Room r: roomList) {
            if (r.getRoomType().equals(type)) {
                r.reserveBeds(cap);
                return;
            }
        }
    }

    public void releaseByList(List<Room> resList) {
        for (Room r: resList) {
            for (Room e: roomList) {
                if (e.getRoomType().equals(r.getRoomType())) {
                    e.releaseBeds(r.getNumVacancies());
                }
            }
        }
    }

    public void releaseReservation(Reservation reservation) {
        // TODO: Release Room based on Reservation object; Maybe write something in the Reservation class that compares roomType
        Room room = reservation.getResRoom();
        HomelessPerson hp = reservation.getResOwner();
        int numRes = reservation.getNumRooms();
        for (Room r: roomList) {
            if (r.getRoomType().equals(room.getRoomType())) {
                r.releaseBeds(numRes);
            }
        }

        hp.releaseReservation(reservation);
        for (int i = 0; i < reserveList.size(); i++) {
            if(reserveList.get(i).getId().equals(reservation.getId())) {
                reserveList.remove(i);
            }
        }


    }

    public void createReservation(HomelessPerson reserver, int num, Room room) {
        //create reservation
        //add reservation to SHelter's reservation list
        //add reservation to User's reservation list
        if (num > 0) {
            Reservation res = new Reservation(reserver, num, room, Calendar.getInstance().getTime().toString());
            reserver.setReservation(true);
            reserver.addReservation(res);
            reserveList.add(res);
            room.reserveBeds(num);
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
