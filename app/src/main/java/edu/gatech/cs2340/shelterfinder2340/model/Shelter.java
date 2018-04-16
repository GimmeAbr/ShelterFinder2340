package edu.gatech.cs2340.shelterfinder2340.model;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

/**
 * A class to represent a Shelter whose rooms can be reserved by a homeless person
 */
public class Shelter {
    private String shelterName;
    private String gender;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private String capacity;
    private final String id;
    private List<Reservation> reserveList;
    private List<Room> roomList;

    /**
     * Constructor for a shelter with all params including an id
     * @param shelterName the name fo the shelter
     * @param gender the gender of the shelter
     * @param address the address of the shelter
     * @param phoneNumber the phone number of the shelter
     * @param longitude the longitude of the shelter
     * @param latitude the latitude of the shelter
     * @param capacity the capacity of the shelter
     * @param id the unique id of the shelter
     */
    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude,  String capacity, String id) {
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

    /**
     * Constructor for a shelter without an id
     * @param shelterName the name of the shelter
     * @param gender the gender of the shelter
     * @param address the address of the shelter
     * @param phoneNumber the phone number of the shelter
     * @param longitude the longitude of the shelter
     * @param latitude the latitude of the shelter
     * @param capacity the capacity of the shelter
     */
    public Shelter (String shelterName, String gender, String address, String phoneNumber, double longitude, double latitude, String capacity) {
        this(shelterName, gender, address, phoneNumber, longitude, latitude, capacity, "");
    }

    /**
     * Constructor for a shelter with no parameters
     */
    public Shelter() {
        this("","","","",0,0,"","");
    }

    /**
     * Gets a list of reservations made at the shelter
     * @return the shelter's list of reservations
     */
    public List<Reservation> getReserveList() {
        return reserveList;
    }

    /**
     * Sets the list of reservations made at a shelter
     * @param reserveList the shelter's list of reservations
     */
    public void setReserveList(List<Reservation> reserveList) {
        this.reserveList = reserveList;
    }

    /**
     * Sets the shelter's name
     * @param shelterName the shelter's name
     */
    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    /**
     * Sets the shelter's gender
     * @param gender the shelter's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the shelter's address
     * @param address the shelter's address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the shelter's initial capacity
     * @param capacity the shelter's capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets the shelter's phone number
     * @param phoneNumber the shelter's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets teh shelter's latitude
     * @param latitude the latitude coordinate
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the shelters longitude
     * @param longitude the longitude coordinate
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

//    public void setId(String id) {this.id = id;}

    /**
     * Gets teh list of different types of rooms the shelter has
     * @param list the list of rooms at a shelter
     */
    public void setRoomList(List<Room> list) {
        roomList = list;
    }

    /**
     * Gets teh shelter's name
     * @return the shelter's name
     */
    public String getShelterName() {
        return shelterName;
    }

    /**
     * Gets the shelter's specified gender
     * @return the shelter's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the shelter's address
     * @return the shelter's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the shelter's initial capacity
     * @return the shelter's capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * Gets the shelter's phone number as a String
     * @return the shelter's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the shelter's latitude
     * @return the latitude coordinate
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Gets the shelter's longitude
     * @return the longitude coordinate
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the shelter's unique id
     * @return the shelter's id
     */
    public String getId() {
        return id;
    }


//    public void reserveBeds(int cap, String type) {
//        for (Room r: roomList) {
//            if (r.getRoomType().equals(type)) {
//                r.reserveBeds(cap);
//                return;
//            }
//        }
//    }

//    public void releaseByList(List<Room> resList) {
//        for (Room r: resList) {
//            for (Room e: roomList) {
//                if (e.getRoomType().equals(r.getRoomType())) {
//                    e.releaseBeds(r.getNumVacancies());
//                }
//            }
//        }
//    }

//    public void releaseReservation(Reservation reservation) {
//        // TODO: Release Room based on Reservation object; Maybe write something in the Reservation class that compares roomType
//        Room room = reservation.getResRoom();
//        HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
//        int numRes = reservation.getNumRooms();
//        for (Room r: roomList) {
//            if (r.getRoomType().equals(room.getRoomType())) {
//                Log.d("numRes", numRes + "");
//                r.setNumVacancies(r.getNumVacancies() + numRes);
//            }
//        }
//        hp.releaseReservation(reservation);
//        for (Reservation rsv : reserveList) {
//            if (rsv.equals(reservation)) {
//                Log.d("Match", reservation.resOwnerId);
//                reserveList.remove(rsv);
//            }
//        }
//    }

    /**
     * Releases a list of reservations by setting each room in the reservation to available
     * @param reservations the list of reservations to be released
     */
    public void releaseReservations(List<Reservation> reservations) {
        for(int j =0; j < reservations.size(); j++){
            Reservation reservation = reservations.get(j);
            Room room = reservation.getResRoom();
            HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
            int numRes = reservation.getNumRooms();
            for (Room r : roomList) {
                if (r.getRoomType().equals(room.getRoomType())) {
                    Log.d("numRes", numRes + "");
                    r.setNumVacancies(r.getNumVacancies() + numRes);
                }
            }
            hp.releaseReservation(reservation);
            for(int i = 0; i < reserveList.size(); i++) {
                Reservation reserveListReservation = reserveList.get(i);
                if (reserveListReservation.equals(reservation)) {
                    Log.d("Match", reservation.resOwnerId);
                    reserveList.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * Creates a reservation object for a homeless person
     * @param hp the homeless person creating the reservation
     * @param num the number of reservations
     * @param room the room in the shelter that should be reserved
     */
    public void createReservation(HomelessPerson hp, int num, Room room) {
        //create reservation
        //add reservation to Shelter's reservation list
        //add reservation to User's reservation list
        if (num > 0) {
            Reservation res = new Reservation(hp, room, num);
            hp.setHasReservation(true);
            hp.addReservation(res);
            reserveList.add(res);
            room.reserveBeds(num);
        }
    }

    /**
     * Checks whether the shelter is completely booked
     * @return true if the shelter has no open rooms
     */
    public boolean reservedOut() {
        boolean ro = true;
        for (Room r: roomList) {
            ro = ro && (r.reservedOut());
        }
        return ro;
    }

//    /**
//     * This method loads the initial roomList based on the csv file
//     */
//    private void loadInitialRoomList() {
//        if (shelterName.contains("Eden")) {
//            roomList.add(new Room(32, "FAMILY", shelterName));
//            roomList.add(new Room(80, "SINGLE", shelterName));
//        } else if (shelterName.contains("Hope")) {
//            roomList.add(new Room(22, "ANYONE", shelterName));
//        } else if (shelterName.contains("Our House")) {
//            roomList.add(new Room(76, "FAMILY", shelterName));
//        } else if (capacity.contains("N/A")) {
//            roomList.add(new Room(0, gender.toUpperCase(), shelterName));
//        } else if (gender.toUpperCase().contains("FAMILY")) {
//            roomList.add(new Room(Integer.valueOf(capacity), "FAMILY", shelterName));
//        } else {
//            Room r = new Room(Integer.valueOf(capacity), gender.toUpperCase().replaceAll("/", " AND "), shelterName);
//            roomList.add(r);
//            Log.d("Room String", r.toString());
//        }
//    }

    /**
     * Calculates the number of open beds at a shelter from all of the room types
     * @return the shelter's vacancies
     */
    public int calculateVacancies() {
        int vac = 0;
        for (Room r: roomList) {
            if (r.getRoomType().toLowerCase().contains("family")) {
                vac += 3 * r.getNumVacancies();
            } else {
                vac += r.getNumVacancies();
            }
        }
        return vac;
    }

    /**
     * Gets the list of rooms within a shelter
     * @return the list of rooms in a shelter
     */
    public List<Room> getRoomList() {
        return roomList;
    }

    @Override
    public String toString() {
        return this.shelterName;
    }

    public boolean equals(Shelter other) {

        if (!(this.shelterName.equals(other.getShelterName()))){
            return false;
        }
        if(!(this.gender.equals(other.getGender()))) {
            return false;
        }
        if(!(this.address.equals(other.getAddress()))) {
            return false;
        }
        if(!(this.phoneNumber.equals(other.getPhoneNumber()))) {
            return false;
        }
        if(!(this.latitude == other.getLatitude())) {
            return false;
        }
        if(!(this.longitude == other.getLongitude())) {
            return false;
        }
        if(!(this.capacity.equals(other.getCapacity()))) {
            return false;
        }
        if(!(this.id.equals(other.getId()))) {
            return false;
        }
        return true;
    }

}
