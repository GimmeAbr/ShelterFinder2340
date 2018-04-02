package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/30/18.
 */

public class Reservation {
    HomelessPerson resOwner;
    Room resRoom;
    String date;
    int numRooms;
    int id;

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


//    /*----------- Actions --------------*/
//    public void releaseReservation() {
//        /**
//         * TODO: the user releases the reservation and so does the shelter
//         */
//        resOwner.releaseRes(this);
//        Room room = resRoom;
//        Shelter shelter = Model.getInstance().getShelterByName(room.getShelterName());
//        if (!shelter.equals(Model.theNullShelter)) {
//            shelter.releaseReservation(this);
//        }
//    }


}
