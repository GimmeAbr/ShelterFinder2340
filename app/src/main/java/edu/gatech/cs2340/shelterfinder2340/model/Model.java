package edu.gatech.cs2340.shelterfinder2340.model;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent the app's global state. Holds the data that should be shared between
 * views in the app
 */
public class Model {

    //-----------------------------------Static data-----------------------------------

    /** Singleton instance */
    private static final Model _instance = new Model();

    /**
     * Returns the singleton instance of the model class
     * @return the static instance of the model
     */
    public static Model getInstance() {
        return _instance;
    }

    /** the current user */
    private User _currentUser;

    /** the current shelter*/
    private Shelter _currentShelter;

    /** holds the list of all shelters */
    private List<Shelter> _shelters;

    /** the ShelterQuery object*/
    private ShelterQuery _query;

    /**
     * Constructs a new instance of the model class
     */
    private Model() {
        _shelters = new ArrayList<>();

    }

    /**
     * Gets the current logged in user in the application
     * @return the current logged in user
     */
    public User get_currentUser() {
        return _currentUser;
    }

    /**
     * Sets the the current logged in user in the application
     * @param _currentUser the current logged in user
     */
    public void set_currentUser(User _currentUser) {
        this._currentUser = _currentUser;
    }


    /**
     * Sets the current user in the application
     * @param user the current logged in user
     */
    public void setCurrentUser(User user) {
        _currentUser = user;
    }

    /**
     * Sets the current shelter the user is viewing in the application
     * @param shelter the selected shelter
     */
    public void setCurrentShelter(Shelter shelter) {
        _currentShelter = shelter;
    }

    /**
     * Sets the list of shelters in the current view of the application
     * @param shelterList the current list of shelters
     */
    public void setSheltersList(List<Shelter> shelterList) {
        _shelters = shelterList;
    }

    /**
     * Gets the current logged in user in the app
     * @return a list of the courses in the app
     */
    public HomelessPerson getCurrentUser(){
        return (HomelessPerson) _currentUser;
    }

    /**
     * Gets a list of shelters used in the application
     * @return a list of shelters in to be seen in the view
     */
    public List<Shelter> getShelters() {
        return _shelters;
    }

//    public boolean addShelter(Shelter shelter) {
//        for (Shelter s : _shelters ) {
//            if (s.equals(shelter)) return false;
//        }
//        _shelters.add(shelter);
//        return true;
//    }


    /**
     * Getter for the selected shelter
     * @return  the currently selected shelter
     */
    public Shelter getCurrentShelter() {
        return _currentShelter;
    }

//    public Shelter getShelterById (String id) {
//        for (Shelter s : _shelters ) {
//            if (s.getId().equals(id)) return s;
//        }
//        return theNullShelter;
//    }



    //-----------------------------------Helpers----------------------------------

    /**
     * Returns the query of shelters that has been retrieved from the database
     * @return a shelter query of shelters from the database
     */
    public ShelterQuery get_query() {
        return _query;
    }

    /**
     * Sets the shelter query to a query ShelterQuery that has been retrieved from database
     * @param _query the shelter query object that has been retrieved from database
     */
    public void set_query(ShelterQuery _query) {
        this._query = _query;
    }

}
