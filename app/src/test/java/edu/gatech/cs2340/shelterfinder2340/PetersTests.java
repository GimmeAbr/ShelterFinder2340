package edu.gatech.cs2340.shelterfinder2340;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Peter on 4/16/2018.
 */

public class PetersTests {


    private Shelter maleShelter;
    private Shelter femaleShelter;

    private HomelessPerson peter = new HomelessPerson("Peter Lebedev", "1234@gmail.com", "2340test", "Male", "asCcomRAemTvmkDJwjcEnxAd1AF3");
    private Room room1;
    private Room room2;
    private Room room3;
    private Reservation res1Result;
    private Reservation res2Result;
    private Reservation res3Result;

    /*
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
     */
    @Before
    public void setUp() {
        maleShelter= new Shelter("MaleShelter", "Men", "Sunset Boulevard", "123", 1.5, 2.6, "93 Men", "Abhj");
        femaleShelter = new Shelter("FemaleShelter", "Women", "Sunset Boulevard", "123", 1.5, 2.6, "93 Men", "Abhj");
        room1 = new Room(200,"type", "MaleShelter");
        room2 = new Room(140,"type", "FemaleShelter");
        room3 = new Room(210,"type", "FemaleShelter");
        maleShelter.setRoomList(Arrays.asList(room1));
        femaleShelter.setRoomList(Arrays.asList(room2,room3));

        res1Result = new Reservation(peter, room1, 6);
        res2Result = new Reservation(peter, room2, 5);
        res3Result = new Reservation(peter, room3, 9);
    }
    @Test
    public void testMaleReserve() {
        maleShelter.createReservation(peter,6, room1);
        assertEquals(maleShelter.getReserveList().get(0),res1Result);
        assertEquals(194, room1.getNumVacancies());
    }
    @Test
    public void testReserveWithMoreThanOneRoom() {
        femaleShelter.createReservation(peter,5, room2);
        assertEquals(femaleShelter.getReserveList().get(0),res2Result);
        assertEquals(135, room2.getNumVacancies());
    }
    @Test
    public void testReserveTwoRooms() {
        femaleShelter.createReservation(peter,5, room2);
        femaleShelter.createReservation(peter,9 , room3);
        assertEquals(femaleShelter.getReserveList().get(0),res2Result);
        assertEquals(femaleShelter.getReserveList().get(1),res3Result);
        assertEquals(135, room2.getNumVacancies());
        assertEquals(201, room3.getNumVacancies());
    }


}
