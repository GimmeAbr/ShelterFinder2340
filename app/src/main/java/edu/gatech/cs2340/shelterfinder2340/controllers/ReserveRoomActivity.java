package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

public class ReserveRoomActivity extends AppCompatActivity {


    private LinearLayout layout;
    private ReserveRoomActivity thisObj;
    private Shelter shelter;
    private Map<String,Room> roomMap;
    private Spinner firstInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView shelterName;
        Button reserveButton;
        Button addButton;
        Spinner firstSpinner;
        Button cancelButton;
        thisObj = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shelterName = findViewById(R.id.shelter_name_reserve);
        //shelterName = findViewById(R.id.shelter_name_reserve);
        reserveButton = findViewById(R.id.reserveBtn);
        addButton = findViewById(R.id.add_room);
        layout = findViewById(R.id.linearLayout);
        roomMap = new HashMap<>();
        firstSpinner = findViewById(R.id.RoomTypes);
        firstInput = findViewById(R.id.NumData);
        cancelButton = findViewById(R.id.cancel_reserve);

        shelter = Model.getInstance().getCurrentShelter();
        toolbar.setTitle(shelter.getShelterName());
        for (Room d: shelter.getRoomList()) {
            roomMap.put(d.getRoomType(),d);
        }
        //shelterName.setText(shelter.getShelterName());
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(thisObj.getApplicationContext(),
                android.R.layout.simple_list_item_1,  Arrays.copyOf(roomMap.keySet().toArray(), roomMap.keySet().toArray().length, String[].class));
        firstSpinner.setAdapter(adapter);

        Integer[] numArray = new Integer[shelter.getRoomList().get(0).getNumVacancies()+1];
        for (int i = 0; i <=shelter.getRoomList().get(0).getNumVacancies(); i++) {
            numArray[i] = i;
        }

        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
                android.R.layout.simple_list_item_1,  numArray);

        firstInput.setAdapter(adapterInt);
        firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = (String) adapterView.getSelectedItem();
                Room selected = roomMap.get(selectedString);
                Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                for (int j = 0; j < numArray.length; j++) {
                        numArray[j] = j;
                }
                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  numArray);
                firstInput.setAdapter(adapterInt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String selectedString = (String) adapterView.getSelectedItem();
                Room selected = roomMap.get(selectedString);
                Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                for (int j = 0; j <=shelter.getRoomList().get(0).getNumVacancies(); j++) {
                    numArray[j] = j;
                }
                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
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
                    numArray[i] = i;
                }

                ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
                        android.R.layout.simple_list_item_1,  numArray);

                numInput.setAdapter(adapterInt);

                roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedString = (String) adapterView.getSelectedItem();
                        Room selected = roomMap.get(selectedString);
                        Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                        for (int j = 0; j < numArray.length; j++) {
                            numArray[j] = j;
                        }
                        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
                                android.R.layout.simple_list_item_1,  numArray);
                        numInput.setAdapter(adapterInt);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        String selectedString = (String) adapterView.getSelectedItem();
                        Room selected = roomMap.get(selectedString);
                        Integer[] numArray = new Integer[selected.getNumVacancies()+1];
                        for (int j = 0; j < numArray.length; j++) {
                            numArray[j] = j;
                        }
                        ArrayAdapter<Integer> adapterInt = new ArrayAdapter<>(thisObj.getApplicationContext(),
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
                ((HomelessPerson) (Model.getInstance().get_currentUser())).setHasReservation(true);

                for (int i = 0; i < layout.getChildCount(); i++) {
                    LinearLayout linearLayout = (LinearLayout) layout.getChildAt(i);
                    Spinner roomStringSpn = (Spinner)linearLayout.getChildAt(0);
                    Spinner numToReserveSpn = (Spinner)linearLayout.getChildAt(1);
                    @SuppressWarnings("SuspiciousMethodCalls") Room reserved = roomMap.get(roomStringSpn.getSelectedItem());
                    Integer numReserved = Integer.valueOf(numToReserveSpn.getSelectedItem().toString());
                    shelter.createReservation(Model.getInstance().getCurrentUser(), numReserved, reserved);
                    // TODO: UPDATE EVERY FUCKING THING
                    ShelterDao sDao = new ShelterDao();
                    sDao.updateShelter(shelter);
                    UserDao userDao = new UserDao();
                    userDao.saveHomelessPerson((HomelessPerson)Model.getInstance().get_currentUser());
                }
                fadeOutToActivity(Login_Success.class);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOutToActivity(ShelterDetailActivity.class);
                finish();
            }
        });
    }

    private void fadeOutToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //Intent i = new Intent(getApplicationContext(), FilterActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
    }

}
