package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/30/18.
 */

public class Reservation {
    HomelessPerson resOwner;
    Room resRoom;
    String date;

    /*--------------- Constructors ----------------------*/

    public Reservation(HomelessPerson resOwner, Room resRoom, String date) {
        this.resOwner = resOwner;
        this.resRoom = resRoom;
        this.date = date;
    }

    public Reservation(HomelessPerson resOwner, Room resRoom) {
        this(resOwner, resRoom, "");
    }

    /*---------- Getters ------------*/
    public String getDate() {
        return date;
    }

    public Room getResRoom() {
        return resRoom;
    }

    /*----------- Actions --------------*/
    public void releaseReservation() {
        /**
         * TODO: the user releases the reservation and so does the shelter
         */
        resOwner.releaseRes(this);
        Room room = resRoom;
        Shelter shelter = Model.getInstance().getShelterByName(room.getShelterName());
        if (!shelter.equals(Model.theNullShelter)) {
            shelter.releaseReservation(this);
        }
    }


}
