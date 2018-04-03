package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;
import edu.gatech.cs2340.shelterfinder2340.model.User;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

public class Login_Success extends AppCompatActivity {

    /** ListView Object */
    private ListView shelterListView;
    /** Loader View while shelter loads */
    private View mProgressView;
    /** Adapter to populate the shelterListView */
    private ArrayAdapter<Shelter> shelterAdapter;
    /** the content of the shelter list view */
    private List<Shelter> shelterList;
    /** The top part of the UI */
    private Toolbar toolbar;
    /** The current context*/
    private Login_Success login_success;
    /** The current context*/
    private final Model model = Model.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        login_success = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);


        /**
         * Step one is to set instantiate the view variables with UI view objects
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shelterListView = (ListView) findViewById(R.id.shelter_list);
        mProgressView = findViewById(R.id.login_progress2);

//        // Loads Data from the Filter View With Intent Data
//        Intent prev1Intent = getIntent();
//        Bundle prev1Extra = prev1Intent.getExtras();
//        if (prev1Extra != null) {
//            if (prev1Extra.getString("Label").equals("search")) {
//                ShelterQuery query = Model.getInstance().get_query();
//                shelterList = query.filterShelter(shelterList);
//            }
//        }

        // Authentication -> getting the current user
        // TODO: load current user here
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });

        // Loads Shelters
        // TODO: load from firebase and show spinner based off that
        shelterList = new ArrayList<Shelter>();
        showProgress(true);
        ShelterDao dao = new ShelterDao();

        dao.getShelters(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Load each shelter from a successful task
                    final List <Shelter> shelterListLoaded = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        Shelter shelter = snapshot.toObject(Shelter.class);
                        shelter.setId(snapshot.getId());
                        // TODO: Remove when done testing reserve shelter screen
                        //Intent intent = new Intent(login_success,ReserveRoomActivity.class);
                        //intent.putExtra("shelter",(Serializable) shelter);
                        //Model.getInstance().setCurrentShelter(shelter);
//                        List<Room> roomList = new ArrayList<>();
//                        roomList.add(new Room(4,"Deluxe", shelter.getShelterName()));
//                        roomList.add(new Room(2,"Lesure", shelter.getShelterName()));
//                        roomList.add(new Room(7,"Crap", shelter.getShelterName()));
//                        shelter.setRoomList(roomList);
//                        startActivity(intent);
                        shelterListLoaded.add(shelter);
                    }
                    //Now shelterList is populated set changes in the model
                    model.setSheltersList(shelterListLoaded);
                    showProgress(false);
                    populateListView();
                } else {
                    /**
                     * TODO: insert code for on fail
                     */
                }
            }
        });






        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    private void populateListView () {
        shelterAdapter = new ArrayAdapter<Shelter>(login_success, android.R.layout.simple_list_item_1, model.getShelters());
        shelterListView.setAdapter(shelterAdapter);
        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = model.getShelters().get(i);
                model.setCurrentShelter(st);
                Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(intent);
            }
        });
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