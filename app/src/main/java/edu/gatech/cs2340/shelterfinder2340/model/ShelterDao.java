package edu.gatech.cs2340.shelterfinder2340.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Peter on 3/5/2018.
 */

public class ShelterDao {
    boolean isDone = false;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void saveShelters(List<Shelter> shelters) {
        Log.d("debug", "about to save shelters");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference shelterRef = ref.child("shelters");
        Map<String, Shelter> shelterMap = new HashMap<>();
        for(Shelter shelter: shelters) {
            shelterRef.child(shelter.getShelterName()).setValue(shelter);
        }
    }
    public List<Shelter> getShelters() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference shelterRef = ref.child("shelters");
        final List<Shelter> shelters = new ArrayList<>();
        shelterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot snapshot: children) {
                    String shelterName = snapshot.child("shelterName").getValue(String.class);
                    String phoneNumber = snapshot.child("phoneNumber").getValue(String.class);
                    String address = (String) snapshot.child("address").getValue();
                    String capacity = (String) snapshot.child("capacity").getValue();
                    String gender = (String) snapshot.child("gender").getValue();
                    double latitude = (double) snapshot.child("latitude").getValue();
                    double longitude = (double) snapshot.child("longitude").getValue();
                    Shelter shelter = new Shelter(shelterName, gender, capacity, address, phoneNumber, longitude, latitude,0);
                    shelters.add(shelter);
                }
                isDone = true;
                Log.d("debug", "Successful in loading");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
        return shelters;
    }
    public boolean isDone() {
        return isDone;
    }
}
