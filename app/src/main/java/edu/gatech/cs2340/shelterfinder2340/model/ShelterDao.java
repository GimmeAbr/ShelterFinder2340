package edu.gatech.cs2340.shelterfinder2340.model;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ShelterDao {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // For future use
    @SuppressWarnings("unused")
    /**
     * this method saves the list of shelters to firebase
     */
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
                shelterMap.put("roomList", shelter.getRoomList());
                shelterMap.put("reserveList", shelter.getReserveList());
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

    /**
     * Gets the list of shelters
     * The oncomplete listern made retrivies the adata in a async call
     * @param listener - the object which tells firebase what to do with the data when its loaded
     */
    public void getShelters(OnCompleteListener<QuerySnapshot> listener) {
        Task<QuerySnapshot> task = db.collection("shelters").get();
        task.addOnCompleteListener(listener);
    }

    /**
     * updates the current shelter
     * @param shelter - sheter to update
     */
    public void updateShelter(Shelter shelter) {
        db.collection("shelters").document(shelter.getId()).set(shelter);
//        for (Room r : shelter.getRoomList()) {
//            DocumentReference roomRef = db.collection("shelters")
//                    .document(shelter.getId()).collection("roomList").document(r.getRoomType());
//            roomRef.update("vacancy", r.getNumVacancies());
//        }
//
//        for (Reservation reservation : shelter.getReserveList()) {
//            DocumentReference resRef = db.collection("shelters")
//                    .document(shelter.getId()).collection("reserveList").document(reservation.getId());
//            resRef.set(reservation);
//        }
    }

}
