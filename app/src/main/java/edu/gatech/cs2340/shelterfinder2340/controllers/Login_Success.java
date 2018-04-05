package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;

/**
 * This activity is used for loading the Login_Success screen
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

        //The top part of the UI
        Toolbar toolbar;

        //Step one is to set instantiate the view variables with UI view objects
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shelterListView = findViewById(R.id.shelter_list);
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
                        if (snapshot.exists()) {
                            Shelter shelter = new Shelter(snapshot);
                            shelter.setReserveListFromSnapshot(snapshot);
                            shelter.setRoomListFromSnapshot(snapshot);
                            shelterListLoaded.add(shelter);
                        }
                    }
                    //Now shelterList is populated set changes in the model
                    model.setSheltersList(shelterListLoaded);
                    showProgress(false);
                    populateListView();
                }
            }
        });

        FloatingActionButton search = findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(i);
                finish();
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
        ShelterQuery query = Model.getInstance().get_query();
        ArrayAdapter<Shelter> shelterAdapter;
        if (query == null) {
            shelterAdapter = new ArrayAdapter<>(login_success,
                    android.R.layout.simple_list_item_1, model.getShelters());
        } else {
            shelterAdapter = new ArrayAdapter<>(login_success,
                    android.R.layout.simple_list_item_1,
                    model.get_filteredShelters());
        }
        shelterListView.setAdapter(shelterAdapter);
        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = model.getShelters().get(i);
                model.setCurrentShelter(st);
                Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(intent);
                //finish();
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