package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/30/18.
 */

public class Reservation {
    String resOwnerId;
    Room resRoom;
    String date;
    int numRooms;
    String id = "";

    /*--------------- Constructors ----------------------*/

    public Reservation(String resOwnerId, int numRooms, Room resRoom, String date) {
        this.resOwnerId = resOwnerId;
        this.resRoom = resRoom;
        this.numRooms = numRooms;
        this.date = date;
    }

    public Reservation(HomelessPerson resOwner, Room resRoom, int numRooms) {
        this(resOwner.getId(), numRooms, resRoom, "");
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

    public String getResOwnerId() {
        return resOwnerId;
    }

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
            return (((Reservation) r).getResOwnerId().equals(resOwnerId)
            && (((Reservation) r).getResRoom().getShelterName().equals(resRoom.getShelterName()))
            && (((Reservation) r).getResRoom().getRoomType().equals(resRoom.getNumVacancies())));
        }
        return false;
    }

}
