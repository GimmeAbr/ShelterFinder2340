package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

public class ReserveRoomActivity extends AppCompatActivity {

    TextView shelterName;
    ScrollView scrollView;
    Button reserveButton;
    Button addButton;
    LinearLayout layout;
    ReserveRoomActivity thisObj;
    Shelter shelter;
    Map<String,Room> roomMap;
    Spinner firstSpinner;
    Spinner firstInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        thisObj = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shelterName = findViewById(R.id.shelter_name_reserve);
        scrollView = findViewById(R.id.scroll_view);
        reserveButton = findViewById(R.id.reserveBtn);
        addButton = findViewById(R.id.add_room);
        layout = findViewById(R.id.linearLayout);
        roomMap = new HashMap<>();
        firstSpinner = findViewById(R.id.RoomTypes);
        firstInput = findViewById(R.id.NumData);

        shelter = Model.getInstance().getCurrentShelter();
        for (Room d: shelter.getRoomList()) {
            roomMap.put(d.getRoomType(),d);
        }
        shelterName.setText(shelter.getShelterName());
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(thisObj.getApplicationContext(),
                android.R.layout.simple_list_item_1,  Arrays.copyOf(roomMap.keySet().toArray(), roomMap.keySet().toArray().length, String[].class));
        firstSpinner.setAdapter(adapter);

        Integer[] numArray = new Integer[shelter.getRoomList().get(0).getNumVacancies()+1];
        for (int i = 0; i <=shelter.getRoomList().get(0).getNumVacancies(); i++) {
            numArray[i] = new Integer(i);
        }

        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                android.R.layout.simple_list_item_1,  numArray);

        firstInput.setAdapter(adapterInt);
        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = (String) adapterView.getSelectedItem();
                Room selected = roomMap.get(selectedString);
                Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                for (int j = 0; j < numArray.length; j++) {
                        numArray[j] = new Integer(j);
                }
                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  numArray);
                firstInput.setAdapter(adapterInt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String selectedString = (String) adapterView.getSelectedItem();
                Room selected = roomMap.get(selectedString);
                Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                for (int j = 0; j <=shelter.getRoomList().get(0).getNumVacancies(); j++) {
                    numArray[j] = new Integer(j);
                }
                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  numArray);
                firstInput.setAdapter(adapterInt);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner roomSpinner = new Spinner(getApplicationContext());
                final Spinner numInput = new Spinner(getApplicationContext());

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  Arrays.copyOf(roomMap.keySet().toArray(), roomMap.keySet().toArray().length, String[].class));

                Integer[] numArray = new Integer[shelter.getRoomList().get(0).getNumVacancies()+1];
                for (int i = 0; i <numArray.length; i++) {
                    numArray[i] = new Integer(i);
                }

                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  numArray);

                numInput.setAdapter(adapterInt);

                roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedString = (String) adapterView.getSelectedItem();
                        Room selected = roomMap.get(selectedString);
                        Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                        for (int j = 0; j < numArray.length; j++) {
                            numArray[j] = new Integer(j);
                        }
                        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                                android.R.layout.simple_list_item_1,  numArray);
                        numInput.setAdapter(adapterInt);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        String selectedString = (String) adapterView.getSelectedItem();
                        Room selected = roomMap.get(selectedString);
                        Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                        for (int j = 0; j < numArray.length; j++) {
                            numArray[j] = new Integer(j);
                        }
                        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<Integer>(thisObj.getApplicationContext(),
                                android.R.layout.simple_list_item_1,  numArray);
                        numInput.setAdapter(adapterInt);
                    }
                });
                roomSpinner.setAdapter(adapter);
                LinearLayout linearLayout = new LinearLayout(thisObj);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(roomSpinner);
                linearLayout.addView(numInput);
                layout.addView(linearLayout);
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Reservation> reservations = new ArrayList<>();

                for (int i = 0; i < layout.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) layout.getChildAt(i);
                    Spinner roomStringSpn = (Spinner)linearLayout.getChildAt(0);
                    Spinner numToReserveSpn = (Spinner)linearLayout.getChildAt(1);
                    Room reservered = roomMap.get((String)roomStringSpn.getSelectedItem());
                    Integer numReserved = Integer.valueOf(numToReserveSpn.getSelectedItem().toString());
                    Reservation reservation = new Reservation(Model.getInstance().getCurrentUser(),reservered,numReserved);
                    reservations.add(reservation);
                    ((HomelessPerson)Model.getInstance().get_currentUser()).addReservation(reservation);
                    // TODO: UPDATE EVERY FUCKING THING
                    ShelterDao sDao = new ShelterDao();
                    sDao.updateShelter(shelter);
                    UserDao userDao = new UserDao();
                    userDao.saveHomelessPerson((HomelessPerson)Model.getInstance().get_currentUser());
                }
                Intent backIntent = new Intent(getApplicationContext(), Login_Success.class);
                startActivity(backIntent);
                finish();
            }
        });
        // TODO: CANCEL BUTTON

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
