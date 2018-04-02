package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/30/18.
 */

public class Reservation {
    HomelessPerson resOwner;
    Room resRoom;
    String date;
    int numRooms;
    String id = "";

    /*--------------- Constructors ----------------------*/

    public Reservation(HomelessPerson resOwner, int numRooms, Room resRoom, String date) {
        this.resOwner = resOwner;
        this.resRoom = resRoom;
        this.numRooms = numRooms;
        this.date = date;
    }

    public Reservation(HomelessPerson resOwner, Room resRoom, int numRooms) {
        this(resOwner, numRooms, resRoom, "");
    }

    /*---------- Getters ------------*/
    public String getDate() {
        return date;
    }

    public Room getResRoom() {
        return resRoom;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public HomelessPerson getResOwner() { return resOwner; }

    public String getId() { return id;}

    /*---------- Setters ------------*/
    public void setId(String id) { this.id = id; }

    /*---------- Helpers ------------*/
    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof Reservation)) {
            return false;
        }
        if (((Reservation) r).getId().equals("")) {
            return (((Reservation) r).getResOwner().getId().equals(resOwner.getId())
            && (((Reservation) r).getResRoom().getShelterName().equals(resRoom.getShelterName()))
            && (((Reservation) r).getResRoom().getRoomType().equals(resRoom.getNumVacancies())));
        }
        return false;
    }

}