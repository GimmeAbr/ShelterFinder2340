package edu.gatech.cs2340.shelterfinder2340.model;
import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;


/**
 * Created by robertwaters on 1/5/17.
 *
 * This is our facade to the Model.  We are using a Singleton design pattern to allow
 * access to the model from each controller.
 *
 *
 */

public class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all courses */
    private List<Shelter> _shelters;

    /** the ShelterQuery object*/
    private ShelterQuery _query;

    public List<ReservationBarLayout> getBars() {
        return bars;
    }

    public void setBars(List<ReservationBarLayout> bars) {
        this.bars = bars;
    }

    private List<ReservationBarLayout> bars;

    private Model() {
        _shelters = new ArrayList<>();

        //comment this out after full app developed -- for homework leave in
        loadDummyData();
    }

    /** the current user */
     private User _currentUser;

     /** the current shelter*/
     private Shelter _currentShelter;

     /** Null Object pattern, returned when no course is found */
    private final Shelter theNullShelter = new Shelter("No Such Shelter", "", "", "", "",0,0,0);


    public User get_currentUser() {
        return _currentUser;
    }

    public void set_currentUser(User _currentUser) {
        this._currentUser = _currentUser;
    }


    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    private void loadDummyData() {
        _shelters.add(new Shelter("Objects and Design", "","","","", 0,0,0));
    }

    public void loadShelters() {
        ShelterDao dao = new ShelterDao();
        _shelters  = dao.getShelters();
    }

    /**
     * get the courses
     * @return a list of the courses in the app
     */
    public List<Shelter> getShelters() { return _shelters; }

    /**
     * add a course to the app.  checks if the course is already entered
     *
     * uses O(n) linear search for course
     *
     * @param shelter  the course to be added
     * @return true if added, false if a duplicate
     */
    public boolean addShelter(Shelter shelter) {
        for (Shelter s : _shelters ) {
            if (s.equals(shelter)) return false;
        }
        _shelters.add(shelter);
        return true;
    }

    /**
     *
     * @return  the currently selected course
     */
    public Shelter getCurrentShelter() { return _currentShelter;}

    public void setCurrentShelter(Shelter shelter) { _currentShelter = shelter; }

    /**
     * Return a course that has matching number.
     * This uses an O(n) linear search.
     *
     * @param id the id of the course to find
     * @return  the course with that number or the NullCourse if no such number exists.
     *
     */
    public Shelter getShelterById (int id) {
        for (Shelter s : _shelters ) {
            if (s.getId() == id) return s;
        }
        return theNullShelter;
    }

    public Shelter getShelterByName(String name) {
        for (Shelter s : _shelters ) {
            if (s.getShelterName().equals(name)) {
                return s;
            }
        }
        return theNullShelter;
    }

    public ShelterQuery get_query() {
        return _query;
    }

    public void set_query(ShelterQuery _query) {
        this._query = _query;
    }

}
