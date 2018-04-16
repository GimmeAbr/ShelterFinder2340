package edu.gatech.cs2340.shelterfinder2340;
import org.junit.Before;
import org.testng.annotations.Test;

import java.util.ArrayList;

import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;
import edu.gatech.cs2340.shelterfinder2340.model.Room;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Class for testing the releaseReservation method in HomelessPerson
 */

public class MyaTester {
    public static final int TIMEOUT = 200;
    HomelessPerson hp;
    Room room1, room2, room3, room4;
    Reservation res1, res2, res3, res4;
    @Before
    public void setUp() {
        hp = new HomelessPerson("mya", "female", "123");
        room1 = new Room(10, "single", "Shelter1");
        room2 = new Room(10, "double", "Shelter1");
        room3 = new Room(10, "female", "Shelter1");
        room4 = new Room(10, "family", "Shelter1");
        res1 = new Reservation("123", 5, room1, "July 1");
        res1.setId("res1");
        res2 = new Reservation("123", 5, room2, "July 1");
        res2.setId("res2");
        res3 = new Reservation("123", 5, room3, "July 1");
        res3.setId("res3");
        res4 = new Reservation("123", 5, room4, "July 1");
        res4.setId("res4");

    }
    @Test
    public void reserveListEmpty() {
        setUp();
        assertFalse(hp.getHasReservation());
        assertEquals(hp.getReserveList().size(), 0);

        hp.releaseReservation(res1);

        assertFalse(hp.getHasReservation());
        assertEquals(hp.getReserveList().size(), 0);
    }

    @Test void reserveListPopulated() {
        setUp();
        ArrayList<Reservation> resList = new ArrayList<>();
        resList.add(res1);
        resList.add(res2);
        resList.add(res3);
        resList.add(res4);

        hp.setReserveList(resList);
        assertTrue(hp.getHasReservation());
        assertEquals(4, hp.getReserveList().size());

        hp.releaseReservation(res1);

        assertTrue(hp.getHasReservation());
        assertEquals(3, hp.getReserveList().size());
        for(Reservation res : hp.getReserveList()) {
            assertFalse(res.getId().equals(res1.getId()));
        }
    }

    @Test
    public void reservationNotFound() {
        setUp();
        ArrayList<Reservation> resList = new ArrayList<>();
        resList.add(res2);

        hp.setReserveList(resList);
        assertTrue(hp.getHasReservation());
        assertEquals(1, hp.getReserveList().size());

        hp.releaseReservation(res1);

        assertTrue(hp.getHasReservation());
        assertEquals(1, hp.getReserveList().size());
        assertTrue(hp.getReserveList().get(0).getId().equals("res2"));
    }

    @Test
    public void removeLastReservation() {
        setUp();
        ArrayList<Reservation> resList = new ArrayList<>();
        resList.add(res2);
        hp.setReserveList(resList);
        assertTrue(hp.getHasReservation());
        assertEquals(1, hp.getReserveList().size());

        hp.releaseReservation(res2);

        assertFalse(hp.getHasReservation());
        assertEquals(0, hp.getReserveList().size());
    }

}
