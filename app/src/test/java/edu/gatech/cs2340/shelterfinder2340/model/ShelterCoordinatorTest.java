package edu.gatech.cs2340.shelterfinder2340.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ashwinchidambaram on 4/16/18.
 */
public class ShelterCoordinatorTest {
    @Test
    public void changeShelterInfo() throws Exception {
        Shelter shelter1 = new Shelter("DemoShelter", "female", "DemoAddress", "1011011100", 1, 1, "100", "1");
        ShelterCoordinator shelt = new ShelterCoordinator("userName1", "password1", "password", shelter1);

        shelt.changeShelterInfo("DemoShelter2", ShelterLabels.NAME);
        assertEquals("DemoShelter2", shelter1.getShelterName());


        shelt.changeShelterInfo("NewAddress", ShelterLabels.ADDRESS );
        assertEquals("NewAddress", shelter1.getAddress());

        shelt.changeShelterInfo("10000", ShelterLabels.CAPACITY);
        assertEquals("10000", shelter1.getCapacity());

        shelt.changeShelterInfo("male", ShelterLabels.GENDER);
        assertEquals("male", shelter1.getGender());


        shelt.changeShelterInfo("8008009999", ShelterLabels.PHONENUMBER);
        assertEquals("8008009999", shelter1.getPhoneNumber());

        shelt.changeShelterInfo("100", ShelterLabels.LATITUDE);
        assertEquals(100, shelter1.getLatitude(), 1.0);

        shelt.changeShelterInfo("100", ShelterLabels.LONGITUDE);
        assertEquals(100, shelter1.getLongitude(), 1.0);

        shelt.changeShelterInfo("", ShelterLabels.LONGITUDE);
        assertEquals(0, shelter1.getLongitude(), 1.0);
    }

}