package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.shelterfinder2340.controllers.Login_Success;

/**
 * Created by Sylvia Li on 2018/3/10.
 */

public class UserDao {
    boolean isDone = false;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void saveHomelessPerson(HomelessPerson hp) {
        Log.d("debug", "about to save homeless person");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference homelessRef = ref.child("homeless");
        homelessRef.child("").setValue(hp);
    }

    public void queryHomelessUser(final String id, final Context context) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference homelessRef = ref.child("homeless");
        final List<HomelessPerson> homelessList = new ArrayList<>();
        homelessRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot snapshot: children) {
                    String uid = snapshot.child("id").getValue(String.class);
                    if (uid.equals(id)) {
                        String gender = snapshot.child("gender").getValue(String.class);
                        boolean enabledReservation = snapshot.child("res").getValue(Boolean.class);
                        String name = snapshot.child("name").getValue(String.class);
                        Log.d("HomelessGender: ", gender);
                        Log.d("Enabled: ", enabledReservation + "");
                        Log.d("NameHomeless", name);
                        Intent myIntent = new Intent(context, Login_Success.class);
                        myIntent.putExtra("homelessName", name);
                        myIntent.putExtra("homelessGender", gender);
                        myIntent.putExtra("homelessRes", enabledReservation);
                        myIntent.putExtra("homelessId", id);
                        myIntent.putExtra("Label", "start");
                        //String shelterInterest = snapshot.child("shelters").getValue(String.class);
                        context.startActivity(myIntent);
//                        if (shelterInterest.length() > 0) {
//
//                        } else {
//                            hp.setShelterList(new ArrayList<Shelter>());
//                        }

                    }
                }
                isDone = true;
                Log.d("debug", "Successful in loading");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });
    }
    public boolean isDone() {
        return isDone;
    }
}
