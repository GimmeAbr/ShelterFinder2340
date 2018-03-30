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
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterQuery;
<<<<<<< HEAD
import edu.gatech.cs2340.shelterfinder2340.model.User;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;
=======
>>>>>>> sylvia-stuff

public class Login_Success extends AppCompatActivity {

    private TextView display;
    private ListView shelterListView;
    private View mProgressView;
    private ArrayAdapter<Shelter> shelterAdapter;
    private List<Shelter> shelterList;
    private List<Shelter> backupShelters;
<<<<<<< HEAD
    private User user1;
    private Toolbar toolbar;
    private Login_Success login_success;
=======
>>>>>>> sylvia-stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        login_success = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);
<<<<<<< HEAD

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        display = (TextView) findViewById(R.id.displayID);
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
=======
        // This sets the toolbar of the activity as the toolbar defined in the xml
        // So that the appbarlayout can work
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final FirebaseUser user = mAuth.getCurrentUser();

        // Logout
        Button logoutButton = findViewById(R.id.logout_button);
>>>>>>> sylvia-stuff
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });

<<<<<<< HEAD
        // Loads Shelters
        // TODO: load from firebase and show spinner based off that
        shelterList = new ArrayList<Shelter>();
        backupShelters = new ArrayList<Shelter>();
        showProgress(true);
        ShelterDao dao = new ShelterDao();
        dao.getShelters(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                   // Load each shelter from a successful task

                    final List<Shelter> shelterListLoaded = new ArrayList<>();
                    for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                        Shelter shelter = snapshot.toObject(Shelter.class);
                        shelterListLoaded.add(shelter);
                    }

                    shelterAdapter = new ArrayAdapter<Shelter>(login_success, android.R.layout.simple_list_item_1, shelterListLoaded);
                    shelterListView.setAdapter(shelterAdapter);
                    shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Shelter st = shelterListLoaded.get(i);
                            Model.getInstance().setCurrentShelter(st);
                            Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                            startActivity(intent);
                        }
                    });
                    showProgress(false);

                } else {

                }
=======
        display = (TextView) findViewById(R.id.displayID);

        // shelterList is the list we use for the listView, like, everything in the listView comes from shelterList
        // backupShelters is a list used specifically for searching; backupShelter is a copy of shelterList
        // so that ShelterQuery can loop through every shelter
        shelterList = new ArrayList<Shelter>();
        backupShelters = new ArrayList<Shelter>();

        //ShelterDao dao = new ShelterDao();
        //shelterList = dao.getShelters();
        shelterList = parseCSV();
        backupShelters.addAll(shelterList);
        //backupShelters.addAll(shelterList);

        // get the previous intent
        // because both FilterActivity and LoginActivity can come back to Login_Success
        // so we need the Intent to act as a Label
        Intent prevIntent = getIntent();
        Bundle prevExtra = prevIntent.getExtras();
        if (prevExtra != null) {
            if (prevExtra.getString("Label").equals("search")) {
                ShelterQuery query = Model.getInstance().get_query();
                shelterList = query.filterShelter(shelterList);
            }
        }

        // Set up the listrView
        shelterListView = findViewById(R.id.shelter_list);
        shelterAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterList);
        shelterListView.setAdapter(shelterAdapter);

        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = shelterList.get(i);
                Model.getInstance().setCurrentShelter(st);
                // Going into ShelterDetailActivity
                Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(intent);
>>>>>>> sylvia-stuff
            }
        });
        FloatingActionButton search = (FloatingActionButton) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Going into FilterActivity
                Intent i = new Intent(getApplicationContext(), FilterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    // This methods reads CSV file and gives a list of shelters created from the file
    // It works.
    private List<Shelter> parseCSV() {
        Log.d("Flag1", "in CSV");
        InputStream csvStream = getResources().openRawResource(R.raw.shelters);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream));
        List<Shelter> shelterList = new ArrayList<>();
        try {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = new String[9];
                line = line.replaceAll(",,", ", N/A,");
                int i = 0;
                while (line.contains("\"") || line.contains(",")) {
                    if (line.charAt(0) == '\"') {
                        Log.d("Line with quotes", line);
                        Log.d("index of quote", line.indexOf("\"") + "");
                        line = line.substring(1);
                        data[i] = line.substring(0, line.indexOf('\"'));
                        Log.d("Data", data[i]);
                        Log.d("the rest", line);
                    } else {
                        data[i] = line.substring(0, line.indexOf(","));
                        Log.d("Data", data[i]);
                        Log.d("the rest", line);
                    }
                    line = line.replaceFirst(data[i], "");
                    line = line.substring(line.indexOf(",") + 1);
                    i = i + 1;
                }
                data[8] = line;
                for (int j = 0; j < data.length; j++) {
                    Log.d("Number " + j, data[j]);
                }
                String shelterName = data[1];
                String capacity = data[2];
                String gender = data[3];
                double longitude = Double.valueOf(data[4]);
                double latitude = Double.valueOf(data[5]);
                String address = data[6];
                String phoneNumber = data[8];
                Shelter newShelter = new Shelter(shelterName, gender, address, phoneNumber, longitude, latitude, capacity);
                shelterList.add(newShelter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shelterList;
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
