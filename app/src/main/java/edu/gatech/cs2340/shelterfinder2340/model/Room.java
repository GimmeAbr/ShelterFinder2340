package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Created by admin on 3/26/18.
 */

public class Room {
    private int numVacancies;
    private String roomType;

    public Room(int numVacancies, String roomType) {
        this.numVacancies = numVacancies;
        this.roomType = roomType;
    }

    public boolean reserveSpot(int numSpots) {
        if (numVacancies >= numSpots) {
            numVacancies = numVacancies - numSpots;
        }
        


    }

}
