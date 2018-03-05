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
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;

public class Login_Success extends AppCompatActivity {

    private TextView display;
    private ListView shelterListView;
    private ArrayAdapter<Shelter> shelterAdapter;
    private List<Shelter> shelterList;
    private Shelter[] shelterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
            }
        });

       display = (TextView) findViewById(R.id.displayID);
//        Intent intent = getIntent();
//        String id = intent.getStringExtra("id");
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Set<String> set = preferences.getStringSet(id, null);
//        List<String> convertedSet = new ArrayList<>();
        // convertedSet.addAll(set);
        display.setText("Welcome!");

        parseCSV();
        Log.d("Success:", "CSV parsed");

        Shelter testSt = new Shelter("test shelter5", "female", 12, "21133", 1.0, 2.0);
        Shelter testSt2 = new Shelter("test shelter6", "female", 12, "21133", 1.0, 2.0);
        Shelter testSt3 = new Shelter("test shelter7", "female", 12, "21133", 1.0, 2.0);
        Shelter testSt4 = new Shelter("test shelter8", "female", 12, "21133", 1.0, 2.0);
        shelterList = new ArrayList<Shelter>();
        shelterList.add(testSt);
        shelterList.add(testSt2);
        shelterList.add(testSt3);
        shelterList.add(testSt4);
        ShelterDao dao = new ShelterDao();
        dao.saveShelters(shelterList);
        List<Shelter> shelters = dao.getShelters();
        Log.d("debug", "Shelters pulled: " + shelters.size());
        shelterArray = new Shelter[shelterList.size()];

        for (int i = 0; i < shelterList.size(); i++) {
            shelterArray[i] = shelterList.get(i);
        }
        shelterListView = (ListView) findViewById(R.id.shelter_list);
        shelterAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterArray);
        shelterListView.setAdapter(shelterAdapter);

        shelterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shelter st = shelterList.get(i);
                Intent intent = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                intent.putExtra("shelter", st);
                startActivity(intent);
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

    private void parseCSV() {
        Log.d("Flag1","in CSV");
        InputStream csvStream = getResources().openRawResource(R.raw.shelters);
        BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream));
        List<Shelter> shelterList = new ArrayList<>();
        try {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String shelterName = data[1];
                int capacity = Integer.parseInt(data[2]);
                String gender = data[3];
                double longitude = Double.valueOf(data[4]);
                double latitude = Double.valueOf(data[5]);
                String address = data[6];
                String phoneNumber = data[7];
                Shelter newShelter = new Shelter(shelterName, gender, capacity, phoneNumber, longitude, latitude);
                shelterList.add(newShelter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShelterDao dao = new ShelterDao();
        dao.saveShelters(shelterList);
    }

}
