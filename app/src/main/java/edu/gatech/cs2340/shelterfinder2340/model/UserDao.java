package edu.gatech.cs2340.shelterfinder2340.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the class that interacts with Firebase when it comes to updating and reading Users
 * Created by Sylvia Li on 2018/3/10.
 */

public class UserDao {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Save the HomelessPerson given
     *
     * @param user the HomelessPerson you wanna save
     */
    public void saveHomelessPerson(HomelessPerson user) {
        Log.d("debug", "about to save homeless person");
        db.collection("homeless").document(user.getId()).set(user);
    }

    public void saveAdmin(Admin admin) {
        db.collection("admin").document(admin.getId()).set(admin);
    }

    public void queryAdmin(String id) {
        // TODO: query Admin from the "admin" collection here
    }

    public void saveBannedUser(HomelessPerson homelessPerson) {
        db.collection("banned").document(homelessPerson.getId()).set(homelessPerson);
    }

    public void unsaveBannedUser(HomelessPerson homelessPerson) {
        db.collection("banned").document(homelessPerson.getId()).delete();
    }

    public void queryUser(String id) {
        // TODO: Use this in LoginActivity so that we can log in both Users and Admins
        queryHomelessUser(id);
        queryAdmin(id);
    }

    /**
     * Query the HomelessPerson from Firebase
     * @param id    the id of the HomelessPerson
     */
    public void queryHomelessUser(final String id) {
        Log.d("Id", id);
        db.collection("homeless").document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("Id from user", task.getResult().getString("name"));
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                String name = snapshot.getString("name");
                                boolean hasReservation = snapshot.getBoolean("hasReservation");
                                String gender = snapshot.getString("gender");
                                @SuppressWarnings("unchecked")
                                Iterable<Object> preReserveList =
                                        (ArrayList<Object>) snapshot.get("reserveList");
                                ArrayList<Reservation> reservationList = new ArrayList<>();
                                for (Object o : preReserveList) {
                                    @SuppressWarnings("unchecked")
                                    HashMap<String, Object> preReservation = (HashMap<String, Object>) o;
                                    int numRoom = ((Long) preReservation.get("numRooms")).intValue();
                                    String resOwnerId = (String) preReservation.get("resOwnerId");
                                    @SuppressWarnings("unchecked")
                                    HashMap<String, Object> preRoom =
                                            (HashMap<String, Object>) preReservation.get("resRoom");
                                    int numVacancies = ((Long) preRoom.get("numVacancies")).intValue();
                                    String roomType = (String) preRoom.get("roomType");
                                    String roomShelterName = (String) preRoom.get("shelterName");
                                    Room resRoom = new Room(numVacancies, roomType, roomShelterName);
                                    Reservation res = new Reservation(resOwnerId, numRoom, resRoom, "");
                                    reservationList.add(res);
                                }
                                HomelessPerson hp = new HomelessPerson(name, gender, id);
                                hp.setHasReservation(hasReservation);
                                hp.setReserveList(reservationList);
                                Model.getInstance().set_currentUser(hp);
                                Log.d("Grabbed User", hp.getId() + " => " + hp.getName());
                            }
                        } else {
                            Log.d("Oh No", "Not successful");
                        }
                    }
                });
    }

}
