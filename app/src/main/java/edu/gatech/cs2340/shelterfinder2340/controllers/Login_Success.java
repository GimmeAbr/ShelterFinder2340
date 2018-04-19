package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
//import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;
//import edu.gatech.cs2340.shelterfinder2340.model.User;
//import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

/**
 * This Activity is used to display the Login_Success screen
 */
public class Login_Success extends AppCompatActivity {

    /** ListView Object */
    private ListView shelterListView;
    /** Loader View while shelter loads */
    private View mProgressView;
    /** the content of the shelter list view */
    private List<Shelter> shelterList;
    /** The current context*/
    private Login_Success login_success;
    /** The current context*/
    private final Model model = Model.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        login_success = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);


        /*
         * Step one is to set instantiate the view variables with UI view objects
         */
        /* The top part of the UI */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shelterListView = findViewById(R.id.shelter_list);
        mProgressView = findViewById(R.id.login_progress2);

        // Authentication -> getting the current user
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser user = mAuth.getCurrentUser();
        final Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.getInstance().set_query(null);
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });

        // Loads Shelters
        shelterList = new ArrayList<>();
        showProgress(true);
        ShelterDao dao = new ShelterDao();

        dao.getShelters(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Load each shelter from a successful task
                    final List <Shelter> shelterListLoaded = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        Log.e("poop", String.valueOf(null == snapshot));
                        if (snapshot.exists()) {
                            String shelterName = snapshot.getString("shelterName");
                            String gender = snapshot.getString("gender");
                            String address = snapshot.getString("address");
                            String phoneNumber = snapshot.getString("phoneNumber");
                            double longitude = snapshot.getDouble("longitude");
                            double latitude = snapshot.getDouble("latitude");
                            String capacity = snapshot.getString("capacity");
                            String id = snapshot.getId();
                            Shelter shelter = new Shelter(shelterName, gender, address,
                                    phoneNumber, longitude, latitude, capacity, id);

                            if (snapshot.get("reserveList") != null) {
                                Iterable<Object> preReserveList =
                                        (ArrayList<Object>) snapshot.get("reserveList");
                                List<Reservation> reservationList = new ArrayList<>();
                                for (Object o : preReserveList) {
                                    HashMap<String, Object> preReservation =
                                            (HashMap<String, Object>) o;
                                    int numRoom =
                                            ((Long) preReservation.get("numRooms")).intValue();
                                    String resOwnerId = (String) preReservation.get("resOwnerId");
                                    HashMap<String, Object> preRoom =
                                            (HashMap<String, Object>)
                                                    preReservation.get("resRoom");
                                    int numVacancies =
                                            ((Long) preRoom.get("numVacancies")).intValue();
                                    String roomType = (String) preRoom.get("roomType");
                                    String roomShelterName = (String) preRoom.get("shelterName");
                                    String date = (String) preRoom.get("date");
                                    Room resRoom =
                                            new Room(numVacancies, roomType, roomShelterName);
                                    Reservation res =
                                            new Reservation(resOwnerId, numRoom, resRoom, date);
                                    reservationList.add(res);
                                }
                                shelter.setReserveList(reservationList);
                            }
                            if (snapshot.get("roomList") != null) {
                                Iterable<Object> preRoomList =
                                        (ArrayList<Object>) snapshot.get("roomList");
                                List<Room> roomList = new ArrayList<>();
                                for (Object o : preRoomList) {
                                    HashMap<String, Object> preRoom = (HashMap<String, Object>) o;
                                    int numVacancies =
                                            ((Long) preRoom.get("numVacancies")).intValue();
                                    String roomType = (String) preRoom.get("roomType");
                                    String roomShelterName = (String) preRoom.get("shelterName");
                                    Room room = new Room(numVacancies, roomType, roomShelterName);
                                    roomList.add(room);
                                }
                                shelter.setRoomList(roomList);
                            }

                            shelterListLoaded.add(shelter);
                        }
                    }
                    //Now shelterList is populated set changes in the model
                    model.setSheltersList(shelterListLoaded);
                    showProgress(false);
                    populateListView();
                } else {
                    Log.d("Failure", "Failed to load Shelters");
                    Toast.makeText(login_success, "Sorry, we cannot load shelters" +
                            " at this moment", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton search = findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOutToActivity(FilterActivity.class);
                //finish();
            }
        });

        FloatingActionButton mapSearch = findViewById(R.id.map_button);
        mapSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("shelters", (ArrayList<Shelter>)shelterList);
                startActivity(i);
            }
        });

    }


    private void populateListView () {
        ShelterQuery query = model.get_query();
        /* Adapter to populate the shelterListView */
        SimpleAdapter shelterAdapter;
        HashMap<String, Object> shelterReservePair;
        List<Shelter> shelterList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> shelterReserveAll = new ArrayList<>();
        if (query == null) {
            shelterList = model.getShelters();
        } else {
            shelterList = query.filterShelter(model.getShelters());
        }

        for (Shelter s : shelterList) {
            shelterReservePair = new HashMap<>();
            shelterReservePair.put("shelter", s);
            shelterReservePair.put("reserved", s.getReserveDisplay(model.getCurrentUser()));
            shelterReserveAll.add(shelterReservePair);
        }
        /*
         * sa = new SimpleAdapter(this, list,
         R.layout.twolines,
         new String[] { "line1","line2" },
         new int[] {R.id.line_a, R.id.line_b});
         ((ListView)findViewById(R.id.list)).setAdapter(sa);
         */
        shelterAdapter = new SimpleAdapter(this, shelterReserveAll, R.layout.reserve_list_display,
                new String[]{"shelter", "reserved"}, new int[]{R.id.line_a, R.id.line_b});

        shelterListView.setAdapter(shelterAdapter);
        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = model.getShelters().get(i);
                model.setCurrentShelter(st);
                slideOutToActivity(ShelterDetailActivity.class);
                //finish();
            }
        });
    }

    private void slideOutToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        //Intent i = new Intent(getApplicationContext(), FilterActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void fadeOutToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        //Intent i = new Intent(getApplicationContext(), FilterActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            shelterListView.setVisibility(show ? View.GONE : View.VISIBLE);
            shelterListView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    shelterListView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            shelterListView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}