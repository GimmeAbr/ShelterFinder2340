package edu.gatech.cs2340.shelterfinder2340.model;
import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;

import java.util.ArrayList;
import java.util.List;


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

    private Model() {
        _shelters = new ArrayList<>();

        //comment this out after full app developed -- for homework leave in
        loadDummyData();
    }

    /** the current user */
     private User _currentUser;

     /** the current user*/
     private Shelter _currentShelter;

     /** Null Object pattern, returned when no course is found */
    private final Shelter theNullShelter = new Shelter("No Such Course", "", "", "", "",0,0,0);


    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
    private void loadDummyData() {
        _shelters.add(new Shelter("Objects and Design", "","","","", 0,0,0));
    }

    /**
     * get the courses
     * @return a list of the courses in the app
     */
    public List<Shelter> getCourses() { return _shelters; }

    /**
     * add a course to the app.  checks if the course is already entered
     *
     * uses O(n) linear search for course
     *
     * @param course  the course to be added
     * @return true if added, false if a duplicate
     */
    public boolean addCourse(Shelter shelter) {
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
     * @param number the number of the course to find
     * @return  the course with that number or the NullCourse if no such number exists.
     *
     */
    public Shelter getShelterById (int id) {
        for (Shelter s : _shelters ) {
            if (s.getId() == id) return s;
        }
        return theNullShelter;
    }

    /**
     * Return a course that has the matching id
     * This uses a linear O(n) search
     *
     * @param id the id number of the course
     * @return the course with this id or theNullCourse if no such id exists.
     */
    public Shelter getShelterByName(String name) {
        for (Shelter s : _shelters ) {
            if (s.getShelterName().equals(name)) {
                return s;
            }
        }
        return theNullShelter;
    }

}
