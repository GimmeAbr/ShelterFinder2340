package edu.gatech.cs2340.shelterfinder2340.model;


public class Reservation {
    final String resOwnerId;
    private final Room resRoom;
    private final String date;
    private final int numRooms;
    private String id = "";

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

// --Commented out by Inspection START (4/14/2018 10:04 PM):
//    /*---------- Getters ------------*/
//    public String getDate() {
//        return date;
//    }
// --Commented out by Inspection STOP (4/14/2018 10:04 PM)

    public Room getResRoom() {
        return resRoom;
    }

    public int getNumRooms() {
        return numRooms;
    }

    private String getResOwnerId() {
        return resOwnerId;
    }

    public String getId() { return id;}

// --Commented out by Inspection START (4/14/2018 10:04 PM):
//    /*---------- Setters ------------*/
//    public void setId(String id) { this.id = id; }
// --Commented out by Inspection STOP (4/14/2018 10:04 PM)

    /*---------- Helpers ------------*/
    @Override
    public boolean equals(Object r) {
        return r != null && r instanceof Reservation && ((Reservation) r).getId().equals("") && (((Reservation) r).getResOwnerId().equals(resOwnerId) && (((Reservation) r).getResRoom().getShelterName().equals(resRoom.getShelterName())) && (((Reservation) r).getResRoom().getRoomType().equals(resRoom.getRoomType())));
    }

}
