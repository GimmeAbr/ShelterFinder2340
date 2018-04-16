package edu.gatech.cs2340.shelterfinder2340.model;

/**
 * Class for a ShelterCoordinator Created by Sylvia Li on 2018/3/7.
 */

public class ShelterCoordinator extends User {
    private Shelter workShelter;

    /**
     * Constructor for a ShelterCoordinator
     * @param userName the username of a ShelterCoordinator
     * @param passWord the password of a ShelterCoordinator
     * @param name the name of a ShelterCoordinator
     * @param workShelter the Shelter of a ShelterCoordinator
     */
    public ShelterCoordinator(String userName, String passWord, String name, Shelter workShelter) {
        super(userName, passWord, "0");
        this.workShelter = workShelter;
        this.setAttribute("Coordinator");
    }

    /**
     * Method to change the information of a coordinator's shelter
     * @param update the actual update
     * @param label the label to update
     */
    public void changeShelterInfo(String update, ShelterLabels label) {
        if (label.equals(ShelterLabels.NAME)) {
            workShelter.setShelterName(update);
        } else if (label.equals(ShelterLabels.ADDRESS)) {
            workShelter.setAddress(update);
        } else if (label.equals(ShelterLabels.CAPACITY)) {
            workShelter.setCapacity("");
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
