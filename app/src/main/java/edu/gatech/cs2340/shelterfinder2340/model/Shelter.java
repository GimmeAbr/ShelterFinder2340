package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;
import android.content.Context;
import android.util.Log;


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
    private String capacity;
    private String id;

    private List<Room> roomList;

    public List<Reservation> getReserveList() {
        return reserveList;
    }

    public void setReserveList(List<Reservation> reserveList) {
        this.reserveList = reserveList;
    }

    private List<Reservation> reserveList;


    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude,  String capacity, String id) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.capacity = capacity;
        this.id = id;
        reserveList = new ArrayList<>();
        roomList = new ArrayList<>();
    }

    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude, String capacity) {
        this(shelterName, gender, address, phoneNumber, longitude, latitude, capacity, "");
    }

    public Shelter() {
        this("","","","",0,0,"","");
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

    public void setId(String id) {this.id = id;}

    public void setRoomList(List<Room> list) {
        roomList = list;
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

    public String getId() { return id; }

    public List<Room> getRoomList() {
        if (roomList.size() == 0
                || roomList == null) {
            loadInitialRoomList();
        }
        return roomList;
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
        HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
        int numRes = reservation.getNumRooms();
        for (Room r: roomList) {
            if (r.getRoomType().equals(room.getRoomType())) {
                Log.d("numRes", numRes + "");
                r.setNumVacancies(r.getNumVacancies() + numRes);
            }
        }
        hp.releaseReservation(reservation);
        for (Reservation rsv : reserveList) {
            if (rsv.equals(reservation)) {
                Log.d("Match", reservation.resOwnerId);
                reserveList.remove(rsv);
            }
        }
    }
    public void releaseReservations(List<Reservation> reservations) {
        for(int j =0; j < reservations.size(); j++){
            Reservation reservation = reservations.get(j);
            Room room = reservation.getResRoom();
            HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
            int numRes = reservation.getNumRooms();
            for (Room r : roomList) {
                if (r.getRoomType().equals(room.getRoomType())) {
                    Log.d("numRes", numRes + "");
                    r.setNumVacancies(r.getNumVacancies() + numRes);
                }
            }
            hp.releaseReservation(reservation);
            for(int i = 0; i < reserveList.size(); i++) {
                Reservation reserveListReservation = reserveList.get(i);
                if (reserveListReservation.equals(reservation)) {
                    Log.d("Match", reservation.resOwnerId);
                    reserveList.remove(i);
                    i--;
                }
            }
        }
    }

    public void createReservation(HomelessPerson reserver, int num, Room room) {
        //create reservation
        //add reservation to SHelter's reservation list
        //add reservation to User's reservation list
        if (num > 0) {
            Reservation res = new Reservation(reserver, room, num);
            reserver.setHasReservation(true);
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

    /**
     * This method loads the initial roomList based on the csv file
     */
    public void loadInitialRoomList() {
        if (shelterName.contains("Eden")) {
            roomList.add(new Room(32, "FAMILY", shelterName));
            roomList.add(new Room(80, "SINGLE", shelterName));
        } else if (shelterName.contains("Hope")) {
            roomList.add(new Room(22, "ANYONE", shelterName));
        } else if (shelterName.contains("Our House")) {
            roomList.add(new Room(76, "FAMILY", shelterName));
        } else if (capacity.contains("N/A")) {
            roomList.add(new Room(0, gender.toUpperCase(), shelterName));
        } else if (gender.toUpperCase().contains("FAMI")) {
            roomList.add(new Room(Integer.valueOf(capacity), "FAMILY", shelterName));
        } else {
            Room r = new Room(Integer.valueOf(capacity), gender.toUpperCase().replaceAll("/", " AND "), shelterName);
            roomList.add(r);
            Log.d("Room String", r.toString());
        }
    }

    public int calculateVacancies() {
        int vac = 0;
        for (Room r: roomList) {
            if (r.getRoomType().toLowerCase().contains("famil")) {
                vac += 3 * r.getNumVacancies();
            } else {
                vac += r.getNumVacancies();
            }
        }
        return vac;
    }

    @Override
    public String toString() {
        return this.shelterName;
    }

}
