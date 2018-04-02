package edu.gatech.cs2340.shelterfinder2340.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 3/5/2018.
 */

public class ShelterEventListener implements ValueEventListener {
    boolean isDone = false;
    List<Shelter> shelters;
    public ShelterEventListener(){
        shelters = new ArrayList<>();
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for(DataSnapshot snapshot: children) {
            Shelter shelter = snapshot.getValue(Shelter.class);
            shelters.add(shelter);
        }
        isDone = true;
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }
    public List<Shelter> getShelters() {
        return shelters;
    }
}
