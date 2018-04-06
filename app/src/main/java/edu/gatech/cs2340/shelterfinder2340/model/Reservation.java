package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by Mya on 3/30/18.
 * This class represents the reservations made by HomelessPerson
 * and stored in both the user and the shelter
 */

public class Reservation {
    private final String resOwnerId;
    private final Room resRoom;
    private final int numRooms;
    private final String id = "";

    /*--------------- Constructors ----------------------*/

    /**
     * Constructor of Reservation
     *
     * @param resOwnerId the Id of the HomelessPerson who made the Reservation
     * @param numRooms   the number of rooms reserved
     * @param resRoom    the Room object that gets reserved
     */
    public Reservation(String resOwnerId, int numRooms, Room resRoom) {
        this.resOwnerId = resOwnerId;
        this.resRoom = resRoom;
        this.numRooms = numRooms;
    }

    /**
     * The Other Constructor
     *
     * @param resOwner the HomelessPerson who made the Reservation
     * @param resRoom  the Room object that gets reserved
     * @param numRooms the number of rooms reserved
     */
    Reservation(HomelessPerson resOwner, Room resRoom, int numRooms) {
        this(resOwner.getId(), numRooms, resRoom);
    }

    /*---------- Getters ------------*/

    /**
     * Get the resRoom Object
     *
     * @return resRoom
     */
    public Room getResRoom() {
        return resRoom;
    }

    int getNumRooms() {
        return numRooms;
    }

    private String getResOwnerId() {
        return resOwnerId;
    }

    /**
     * Return the Id of the Reservation
     * @return id
     */
    public String getId() { return id;}

    /*---------- Helpers ------------*/

    public boolean judgeShelter(Shelter shelter) {
        String shelterName = shelter.getShelterName();
        return (resRoom.getShelterName().equals(shelterName));
    }

    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof Reservation)) {
            return false;
        }
        if (!("".equals(((Reservation) r).getId()))) {
            return id.equals(((Reservation) r).getId());
        } else {
            Room room = ((Reservation) r).getResRoom();
            return (resOwnerId.equals(((Reservation) r).getResOwnerId())
                    && room.equals(resRoom));
        }
    }

}
