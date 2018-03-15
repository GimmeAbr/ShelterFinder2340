package edu.gatech.cs2340.shelterfinder2340.model;

import android.os.Parcelable;

/**
 * Created by Sylvia Li on 2018/3/7.
 */

public class ShelterCoordinator extends User {
    private Shelter workShelter;
    public ShelterCoordinator(String userName, String passWord, String name, Shelter workShelter) {
        super(userName, passWord, 0);
        this.workShelter = workShelter;
    }

    public void changeShelterInfo(String update, ShelterLabels label) {
        if (label.equals(ShelterLabels.NAME)) {
            workShelter.setShelterName(update);
        } else if (label.equals(ShelterLabels.ADDRESS)) {
            workShelter.setAddress(update);
        } else if (label.equals(ShelterLabels.CAPACITY)) {
            workShelter.setCapacity(0);
        } else if (label.equals(ShelterLabels.GENDER)) {
            workShelter.setGender(update);
        } else if (label.equals(ShelterLabels.PHONENUMBER)) {
            workShelter.setPhoneNumber(update);
        } else if (label.equals(ShelterLabels.LATITUDE)) {
            workShelter.setLatitude(Double.valueOf(update));
        } else {
            workShelter.setLongitude(Double.valueOf(update));
        }
    }
}
