package edu.gatech.cs2340.shelterfinder2340.model;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void saveShelters(List<Shelter> shelters) {
        Log.d("debug", "about to save shelters");
        /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference shelterRef = ref.child("shelters");
        Map<String, Shelter> shelterMap = new HashMap<>(); */
        for (Shelter shelter: shelters) {
            Map<String, Object> shelterMap = new HashMap<>();
                shelterMap.put("shelterName", shelter.getShelterName());
                shelterMap.put("gender", shelter.getGender());
                shelterMap.put("capacity", shelter.getCapacity());
                shelterMap.put("phoneNumber", shelter.getPhoneNumber());
                shelterMap.put("longitude", shelter.getLongitude());
                shelterMap.put("latitude", shelter.getLatitude());
                shelterMap.put("address", shelter.getAddress());
            db.collection("shelters")
                    .add(shelterMap)
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
    }
    public void getShelters(OnCompleteListener<QuerySnapshot> listener) {
        Task<QuerySnapshot> task = db.collection("shelters").get();
        task.addOnCompleteListener(listener);
    }

    public boolean isDone() {
        return isDone;
    }
}
