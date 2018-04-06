package edu.gatech.cs2340.shelterfinder2340.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by admin on 2/26/18.
 * This class represents a Shelter
 */
public class Shelter{
    private String shelterName;
    private String gender;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private String capacity;
    private final String id;

    private List<Room> roomList;

    List<Reservation> getReserveList() {
        return reserveList;
    }

    /**
     * Set the Shelter's reserveList to the list given
     *
     * @param rsvList the list given
     */
    public void setReserveList(Collection<Reservation> rsvList) {
        this.reserveList = new ArrayList<>();
        reserveList.addAll(rsvList);
    }

    private List<Reservation> reserveList;


    /**
     * Constructor for shelter
     *
     * @param shelterName Shelter's name
     * @param gender      The gender of the shelter
     * @param address     Shelter's address
     * @param phoneNumber Shelter's phone number
     * @param longitude   Shelter's longitude
     * @param latitude    Shelter's latitude
     * @param capacity    Shelter's capacity
     * @param id          Shelter's id
     */
    public Shelter(String shelterName, String gender, String address,
                   String phoneNumber, double longitude, double latitude,
                   String capacity, String id) {
        this.shelterName = shelterName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.capacity = capacity;
        this.id = id;
        reserveList = new ArrayList<>();
        roomList = new ArrayList<>();
    }

    public Shelter(DocumentSnapshot snapshot) {
        this(snapshot.getString("shelterName"), snapshot.getString("gender"),
                snapshot.getString("address"), snapshot.getString("phoneNumber"),
                snapshot.getDouble("longitude"), snapshot.getDouble("latitude"),
                snapshot.getString("capacity"), snapshot.getId());
    }


    //Setters; These methods are for the ShelterCoordinators

    /**
     * Set Shelter's name to the String given
     *
     * @param shelterName the String given
     */
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    /**
     * Set Shelter's gender to the gender given
     * @param gender    the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Set Shelter's address to the current address given
     * @param address   the current address given
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set Shelter's capacity to the given
     * @param capacity  the given capacity
     */
    void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * Set Shelter's phone number to the given number
     *
     * @param phoneNumber the given shelter's phone number
     */
    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Set the roomList variable to the list given
     *
     * @param list the given list
     */
    public void setRoomList(List<Room> list) {
        roomList = new ArrayList<>();
        roomList.addAll(list);
    }
    //Getters

    /**
     * @return Shelter's name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * @return Shelter's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return Shelter's address
     */
    public CharSequence getAddress() {
        return address;
    }

    /**
     * @return Shelter's capacity
     */
    public CharSequence getCapacity() {
        return capacity;
    }

    /**
     * @return Shelter's phoneNumber
     */
    public CharSequence getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return Shelter's latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return Shelter's longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return Shelter's id
     */
    public String getId() { return id;
    }

    /**
     * @return Shelter's roomList
     */
    public List<Room> getRoomList() {
        return roomList;
    }

//    public void releaseReservation(Reservation reservation) {
//        Room room = reservation.getResRoom();
//        HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
//        int numRes = reservation.getNumRooms();
//        for (Room r: roomList) {
//            if (r.compareRoomType(room)) {
//                r.setNumVacancies(r.getNumVacancies() + numRes);
//            }
//        }
//        hp.releaseReservation(reservation);
//        for (Reservation rsv : reserveList) {
//            if (rsv.equals(reservation)) {
//                reserveList.remove(rsv);
//            }
//        }
//    }

    public void setReserveListFromSnapshot(DocumentSnapshot snapshot) {
        if (snapshot.get("reserveList") != null) {
            //noinspection unchecked
            @SuppressWarnings("unchecked") Iterable<Object> preReserveList
                    = (ArrayList<Object>) snapshot.get("reserveList");
            Collection<Reservation> reservationList = new ArrayList<>();
            for (Object o : preReserveList) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> preReservation
                        = (HashMap<String, Object>) o;
                int numRoom = ((Long) preReservation
                        .get("numRooms")).intValue();
                String resOwnerId = (String) preReservation.get("resOwnerId");
                @SuppressWarnings("unchecked") HashMap<String, Object> preRoom
                        = (HashMap<String, Object>)
                        preReservation.get("resRoom");
                int numVacancies =
                        ((Long) preRoom.get("numVacancies")).intValue();
                String roomType = (String) preRoom.get("roomType");
                String roomShelterName = (String) preRoom.get("shelterName");
                Room resRoom =
                        new Room(numVacancies, roomType, roomShelterName);
                Reservation res = new Reservation(resOwnerId, numRoom, resRoom);
                reservationList.add(res);
            }
            this.reserveList.addAll(reservationList);
        }
    }

    public void setRoomListFromSnapshot(DocumentSnapshot snapshot) {
        if (snapshot.get("roomList") != null) {
            @SuppressWarnings("unchecked") Iterable<Object> preRoomList =
                    (ArrayList<Object>) snapshot.get("roomList");
            List<Room> roomList = new ArrayList<>();
            for (Object o : preRoomList) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> preRoom = (HashMap<String, Object>) o;
                int numVacancies =
                        ((Long) preRoom.get("numVacancies")).intValue();
                String roomType = (String) preRoom.get("roomType");
                String roomShelterName = (String) preRoom.get("shelterName");
                Room room = new Room(numVacancies, roomType, roomShelterName);
                roomList.add(room);
            }
            this.setRoomList(roomList);
        }
    }

    /**
     * Release the list of given reservations from the Shelter
     * @param reservations  the list of reservations to release
     */
    public void releaseReservations(List<Reservation> reservations) {
        HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
        Reservation reservation;
        Room room;
        Collection<Reservation> backupReserve = new ArrayList<>();
        backupReserve.addAll(reserveList);
        for(int j = 0; j < reservations.size(); j++) {
            reservation = reservations.get(j);
            room = reservation.getResRoom();
            int numRes = reservation.getNumRooms();
            for (Room r : roomList) {
                if (r.compareRoomType(room)) {
                    Log.d("numRes", numRes + "");
                    r.setNumVacancies(r.getNumVacancies() + numRes);
                }
            }
            hp.releaseReservation(reservation);

            for (Reservation reservationBackUp : backupReserve) {
                if (reservationBackUp.equals(reservation)) {
                    reserveList.remove(reservationBackUp);
                }
            }
        }
    }

    /**
     * Create a reservation using the reserver, number of rooms reserved, and a Room object
     * @param reserver  the HomelessPerson who made the reservation
     * @param num       the number of rooms reserved
     * @param room      the Room object used
     */
    public void createReservation(HomelessPerson reserver, int num, Room room) {
        //create reservation
        //add reservation to Shelter's reservation list
        //add reservation to User's reservation list
        if (num > 0) {
            Reservation res = new Reservation(reserver, room, num);
            reserver.setHasReservation(true);
            reserver.addReservation(res);
            reserveList.add(res);
            room.reserveBeds(num);
        }
    }

    /**
     * @return if the Shelter is reserved out
     */
    public boolean reservedOut() {
        boolean ro = true;
        for (Room r: roomList) {
            ro = ro && (r.reservedOut());
        }
        return ro;
    }

    /**
     * Calculate the vacancies in the Shelter
     * @return the number of vacant beds
     */
    public int calculateVacancies() {
        int vac = 0;
        for (Room r: roomList) {
            vac = vac + r.calculateBeds();
        }
        return vac;
    }

    @Override
    public String toString() {
        return this.shelterName;
    }

}
