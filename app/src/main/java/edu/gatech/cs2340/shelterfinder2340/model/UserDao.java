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
import com.google.firebase.firestore.DocumentSnapshot;
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
        db.collection("homeless").document(user.getId()).set(user);
    }

    public void queryHomelessUser(final String id) {
        Log.d("Id", id);
        db.collection("homeless").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // TODO: Make this work; retrieve reservation list correctly
                    Log.d("Id from user", task.getResult().getString("name"));
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        HomelessPerson hp = snapshot.toObject(HomelessPerson.class);
                        Model.getInstance().set_currentUser(hp);
                        Log.d("Grabbed User", hp.getId() + " => " + hp.getName());
                    }
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
