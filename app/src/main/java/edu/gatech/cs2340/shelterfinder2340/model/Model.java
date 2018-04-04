package edu.gatech.cs2340.shelterfinder2340.model;
import android.support.annotation.NonNull;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import edu.gatech.cs2340.shelterfinder2340.R;

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

    //-----------------------------------Static data-----------------------------------

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    //-----------------------------------Instance data-----------------------------------

    /** the current user */
    private User _currentUser;

    /** the current shelter*/
    private Shelter _currentShelter;

    /** Null Object pattern, returned when no course is found */
    public static final Shelter theNullShelter = new Shelter("No Such Shelter", "", "", "",0,0, "" ,"");

    /** holds the list of all shelters */
    private List<Shelter> _shelters;

    /** holds a list of the filtered shelters*/
    private ArrayList<Shelter> _filteredShelters;


    //-----------------------------------Constructor-----------------------------------


    /** the ShelterQuery object*/
    private ShelterQuery _query;

    private List<ReservationBarLayout> bars;

    private Model() {
        _shelters = new ArrayList<>();
        //comment this out after full app developed -- for homework leave in
    }


    //-----------------------------------Setters-----------------------------------

    public User get_currentUser() {
        return _currentUser;
    }

    public void set_currentUser(User _currentUser) {
        this._currentUser = _currentUser;
    }


    public void setCurrentShelter(Shelter shelter) {
        _currentShelter = shelter;
    }

    public void setSheltersList(List<Shelter> shelterList) {
        _shelters = shelterList;
    }

    public void setCurrentUser(User user) {
        _currentUser = user;
    }


    public void setBars(List<ReservationBarLayout> bars) {
        this.bars = bars;
    }



    //-----------------------------------Getters----------------------------------

    /**
     * This loads all the shelters from the database
     * Should be called at the beginning of the application
     */
    public void loadShelters() {
        ShelterDao dao = new ShelterDao();
        dao.getShelters(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(DocumentSnapshot d : task.getResult().getDocuments()) {
                        Shelter s = d.toObject(Shelter.class);
                        _shelters.add(s);
                    }
                }
            }
        });
    }




    /**
     * get the courses
     * @return a list of the courses in the app
     */
    public HomelessPerson getCurrentUser(){ return (HomelessPerson) _currentUser; }

    /**
     * get the courses
     * @return a list of the courses in the app
     */
    public List<Shelter> getShelters() { return _shelters; }

    public boolean addShelter(Shelter shelter) {
        for (Shelter s : _shelters ) {
            if (s.equals(shelter)) return false;
        }
        _shelters.add(shelter);
        return true;
    }


    /**
     * Getter for the selected shelter
     * @return  the currently selected course
     */
    public Shelter getCurrentShelter() { return _currentShelter;}



    /**
     * Return a shelter that has matching number.
     * This uses an O(n) linear search.
     * @param id the id of the course to find
     * @return  the course with that number or the NullCourse if no such number exists.
     *
     */
    public Shelter getShelterById (int id) {
        for (Shelter s : _shelters ) {
            if (s.getId().equals(id)) return s;
        }
        return theNullShelter;
    }

    /**
     * Returns a shelter that has a matching name
     * This uses an O(n) linear search.
     * @param name the id of the course to find
     * @return  the shelter with that number or the NullCourse if no such number exists.
     *
     */
    public Shelter getShelterByName(String name) {
        for (Shelter s : _shelters ) {
            if (s.getShelterName().equals(name)) {
                return s;
            }
        }
        return theNullShelter;
    }


    public List<ReservationBarLayout> getBars() {
        return bars;
    }
    //-----------------------------------Helpers----------------------------------

    /**
     * populate the model with some dummy data.  The full app would not require this.
     * comment out when adding new courses functionality is present.
     */
//    private void loadDummyData() {
//        _shelters = new ArrayList<Shelter>();
//        _shelters.add(new Shelter("Shelter4", "","","",0, 0,"",0));
//        _shelters.add(new Shelter("Shelter4", "","","",0, 0,"",0));
//        _shelters.add(new Shelter("Shelter3", "","","",0, 0,"",0));
//        _shelters.add(new Shelter("Shelter4", "","","",0, 0,"",0));
//
//    }

    private void loadDummyUser() {
        _currentUser = new User("Mya", "myaetsang@gmail.com", "password", "1");
    }
    public ShelterQuery get_query() {
        return _query;
    }

    public void set_query(ShelterQuery _query) {
        this._query = _query;
    }

}
