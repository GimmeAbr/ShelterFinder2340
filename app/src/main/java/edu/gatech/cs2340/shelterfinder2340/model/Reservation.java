package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/30/18.
 */

public class Reservation {
    HomelessPerson resOwner;
    Room resRoom;
    String date;

    public Reservation(HomelessPerson resOwner, Room resRoom, String date) {
        this.resOwner = resOwner;
        this.resRoom = resRoom;
        this.date = date;
    }


    public Reservation(HomelessPerson resOwner, Room resRoom) {
        this(resOwner, resRoom, "");
    }

    public void releaseReservation() {
        /**
         * TODO: the user releases the reservation
         */

    }


}
