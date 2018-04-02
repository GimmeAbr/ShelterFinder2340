package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

public class Login_Success extends AppCompatActivity {

    private TextView display;
    private ListView shelterListView;
    private ArrayAdapter<Shelter> shelterAdapter;
    private List<Shelter> shelterList;
    private List<Shelter> backupShelters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
                finish();
            }
        });

        Log.d("Flag1", "display");
        display = (TextView) findViewById(R.id.displayID);

        shelterList = new ArrayList<Shelter>();
        backupShelters = new ArrayList<Shelter>();

        //ShelterDao dao = new ShelterDao();
        //shelterList = dao.getShelters();
        shelterList = parseCSV();
        backupShelters.addAll(shelterList);
        //backupShelters.addAll(shelterList);

        Intent prevIntent = getIntent();
        Bundle prevExtra = prevIntent.getExtras();
        if (prevExtra != null) {
            if (prevExtra.getString("Label").equals("search")) {
                ShelterQuery query = Model.getInstance().get_query();
                shelterList = query.filterShelter(shelterList);
            }
        }


        shelterListView = (ListView) findViewById(R.id.shelter_list);
        shelterAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterList);
        shelterListView.setAdapter(shelterAdapter);

        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = shelterList.get(i);
                Model.getInstance().setCurrentShelter(st);
                Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(intent);
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

    private class ShelterAdapter extends ArrayAdapter<Shelter> {
        private Context context;
        private List<Shelter> shelters;

        public ShelterAdapter(Context context, List<Shelter> shelters) {
            super(context, R.layout.shelter_list_content);
            this.context = context;
            this.shelters = shelters;
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.shelter_list_content, parent, false);
            TextView shelterName = (TextView) rowView.findViewById(R.id.shelter_name);
            TextView shelterAddress = (TextView) rowView.findViewById(R.id.shelter_address);
            Shelter st = shelters.get(position);
            shelterName.setText(st.getShelterName());
            shelterAddress.setText(st.getAddress());
            return rowView;
        }

        @Override
        public Shelter getItem(int pos) {
            return shelterList.get(pos);
        }

    }

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

                Shelter newShelter = new Shelter(shelterName, gender, capacity, address, phoneNumber, longitude, latitude);
                shelterList.add(newShelter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shelterList;
    }


}
