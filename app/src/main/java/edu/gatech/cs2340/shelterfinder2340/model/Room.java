package edu.gatech.cs2340.shelterfinder2340.model;

/*
 * Created by Sylvia Li on 2018/3/26.
 **/

import java.io.Serializable;

/**
 * Class representing a type of room available at a Shelter
 * Users have a roo
 */
public class Room implements Serializable{
    private final int initialCap;

    public void setNumVacancies(int numVacancies) {
        this.numVacancies = numVacancies;
    }

    private int numVacancies;
    private final String roomType;

    private final String shelterName;

    public int getNumVacancies() {
        return numVacancies;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getShelterName() {
        return shelterName;
    }


    public Room(int numVacancies, String roomType, String shelterName) {
        this.initialCap = numVacancies;
        this.numVacancies = numVacancies;
        this.roomType = roomType;
        this.shelterName = shelterName;
    }

    public boolean reserveBeds(int numSpots) {
        if (numVacancies >= numSpots) {
            numVacancies = numVacancies - numSpots;
            return true;
        }
        return false;
    }

    /*public boolean canReserve(int numSpots) {
        if (numVacancies >= numSpots) {
            return true;
        }
        return false;
    } */

    public void releaseBeds(int numSpots) {
        this.numVacancies = this.numVacancies + numSpots;
        if (numVacancies >= initialCap) {
            numVacancies = initialCap;
        }
    }

    @Override
    public String toString() {
        return "" + numVacancies + " " + roomType + "room(s)";
    }

    public boolean reservedOut() {
        return numVacancies == 0;
    }

}