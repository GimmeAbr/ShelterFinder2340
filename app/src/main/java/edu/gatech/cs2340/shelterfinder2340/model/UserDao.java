package edu.gatech.cs2340.shelterfinder2340.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.shelterfinder2340.controllers.Login_Success;

/**
 * Created by Sylvia Li on 2018/3/10.
 */

public class UserDao {
    boolean isDone = false;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void saveHomelessPerson(HomelessPerson user) {
        Log.d("debug", "about to save homeless person");
        Map<String, Object> homelessMap = new HashMap<>();
        homelessMap.put("name", user.getName());
        homelessMap.put("id", user.getId());
        //homelessMap.put("gender", user.getGender());
        homelessMap.put("hasReservation", user.hasReservation());
        homelessMap.put("reservationList", user.getReserveList());
        db.collection("homeless")
                .add(homelessMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("debug", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("debug", "Error adding document", e);
                    }
                });
    }

    public void queryHomelessUser(String id) {
        Log.d("Id", id);
        db.collection("homeless")
                .whereEqualTo("id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // TODO: Make this work; right now it is null
                    HomelessPerson hp = task.getResult().getDocuments().get(0).toObject(HomelessPerson.class);
                    Model.getInstance().set_currentUser(hp);
                    Log.d("Grabbed User", hp.getId() + " => " + hp.getName());
                } else {
                    Log.d("Oh No", "Not successful");
                }
            }
        });
    }
    public boolean isDone() {
        return isDone;
    }
}
