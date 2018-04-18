package edu.gatech.cs2340.shelterfinder2340;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Sebastian Hollister on 4/16/2018.
 */

public class CalculateVacanciesTest {
    private int calculatedVac;
    private Shelter maleShelter;
    private Shelter womenShelter;
    private Shelter childShelter;
    private Shelter familyShelter;
    private Room maleShelterRoom;
    private Room femaleShelterRoom;
    private Room childShelterRoom;
    private Room familyShelterRoom;
    private HomelessPerson seb = new HomelessPerson("Seb", "male", "2340test");
    private HomelessPerson amy = new HomelessPerson("amy", "username", "password");


    @Before
    public void setUp() {
        maleShelter = new Shelter("MaleShelter", "Men", "howell", "123", 1.5, 2.6, "100 Men", "abhb");
        womenShelter = new Shelter("womenShelter", "women", "123Street", "111", 2.2, 2.3, "100 women", "dfdf");
        childShelter = new Shelter("childShelter", "child", "456Street", "222", 24, 44, "100 Children", "Fdf");
        familyShelter = new Shelter("familyShelter", "families", "789Street", "333", 1.2, 33, "20 Families", "asdf");
        maleShelterRoom = new Room(100, "type", "MaleShelter");
        femaleShelterRoom = new Room(305, "type", "womenShelter");
        childShelterRoom = new Room(45, "type", "childShelter");
        familyShelterRoom = new Room(21, "type", "familyShelter");
        maleShelter.setRoomList(Arrays.asList(maleShelterRoom));
        womenShelter.setRoomList(Arrays.asList(femaleShelterRoom));
        childShelter.setRoomList(Arrays.asList(childShelterRoom));
        familyShelter.setRoomList(Arrays.asList(familyShelterRoom));

    }

    @Test
    public void testMaleVacCount() {
        maleShelter.createReservation(seb, 23, maleShelterRoom);
        //calculatedVac = maleShelter.calculateVacancies();
        assertEquals(100 - 23, maleShelter.calculateVacancies());
    }

    @Test
    public void testFemaleVacCount() {
        womenShelter.createReservation(seb, 9, femaleShelterRoom);
        calculatedVac = womenShelter.calculateVacancies();
        assertEquals(305 - 9, calculatedVac);
    }

    @Test
    public void testChildVacCount() {
        childShelter.createReservation(seb, 32, childShelterRoom);
        calculatedVac = childShelter.calculateVacancies();
        assertEquals( 45 - 32, calculatedVac);
    }

    @Test
    public void testFamilyVacCount() {
        familyShelter.createReservation(seb, 4, familyShelterRoom);
        calculatedVac = familyShelter.calculateVacancies();
        assertEquals(21 -  4, calculatedVac);
    }

    @Test
    public void testMultipleReservations() {
        womenShelter.createReservation(seb, 2, femaleShelterRoom );
        womenShelter.createReservation(amy, 4, femaleShelterRoom);
        calculatedVac = womenShelter.calculateVacancies();
        assertEquals(299, calculatedVac);
    }

    @Test
    public void testFullCapacity() {
        familyShelter.createReservation(seb, 21, familyShelterRoom);
        calculatedVac = familyShelter.calculateVacancies();
        assertEquals(0, calculatedVac);
    }

}
