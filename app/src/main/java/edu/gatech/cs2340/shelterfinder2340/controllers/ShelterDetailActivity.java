package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Room;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;

import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;


public class ShelterDetailActivity extends AppCompatActivity {
    private boolean homelessRes;
    Model model = Model.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        ActionBar tb = getSupportActionBar();

        /**
         * Get the current shelter from the model
         */
        final Shelter currentShelter = model.getCurrentShelter();
        final HomelessPerson hp = model.getCurrentUser();

        /**
         * Set all of the text fields based on shelter data
         */
        TextView capacity = (TextView) findViewById(R.id.capacity);
        capacity.setText(currentShelter.getShelterName());

        TextView gender = (TextView) findViewById(R.id.gender);
        gender.setText(currentShelter.getGender());

        TextView longtitude = (TextView) findViewById(R.id.longitude);
        longtitude.setText("" + currentShelter.getLongitude());

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("" + currentShelter.getLatitude());

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(currentShelter.getAddress());

        TextView phone = (TextView) findViewById(R.id.phonenumber);
        phone.setText(currentShelter.getPhoneNumber());
        final Button reserveButton = findViewById(R.id.reserveButton);

        // isRes() indicates whether the HomelessPerson is allowed to reserve
//        if (!hp.hasReservation()) {
//            if (hp.getReservedShelter().getShelterName().equals(currentShelter.getShelterName())) {
//                reserveButton.setText("Release");
//                reserveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        model.getCurrentShelter().releaseByList(hp.getReserveList());
//                        hp.setReservation(true);
//                        hp.releaseRooms();
//                        Intent i = new Intent(getApplicationContext(), Login_Success.class);
//                        startActivity(i);
//                        finish();
//                    }
//                });
//            } else {
//                reserveButton.setClickable(false);
//                reserveButton.setEnabled(false);
//                reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
//            }
//        } else {
//            if (!currentShelter.reservedOut()) {
//                reserveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent i = new Intent(getApplicationContext(), ReservationActivity.class);
//                        startActivity(i);
//                        finish();
//                    }
//                });
//            } else {
//                reserveButton.setClickable(false);
//                reserveButton.setEnabled(false);
//                reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
//            }
//
//        }


        /**
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them, whatever the user wants
         */

        if (tb != null) {
            tb.setTitle(Model.getInstance().getCurrentShelter().getShelterName());
        }
        // isRes() indicates whether the HomelessPerson is allowed to reserve
        if (((HomelessPerson)Model.getInstance().get_currentUser()).getHasReservation()) {
            reserveButton.setClickable(false);
            reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
        } else {
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //List<Room> roomList = Model.getInstance().getCurrentShelter().getRoomList();
                    List<Room> roomList = new ArrayList<>();
                    roomList.add(new Room(4,"Deluxe", Model.getInstance().getCurrentShelter().getShelterName()));
                    roomList.add(new Room(2,"Lesure", Model.getInstance().getCurrentShelter().getShelterName()));
                    roomList.add(new Room(7,"Crap", Model.getInstance().getCurrentShelter().getShelterName()));
                    Model.getInstance().getCurrentShelter().setRoomList(roomList);
                    Intent intent = new Intent(getApplicationContext(), ReserveRoomActivity.class);
                    startActivity(intent);
                }
            });
        }


        /**
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them, whatever the user wants
         */


//        reserveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Set onclick --> Be able to reserve a place
//
//                // Create a whole new AlertDialog box
//                // With a layout that allows users to pick the numbers of rooms to reserve
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Reserve at Shelter");
//                builder.setView(R.layout.reservation_content);
//                ListView reserveList = findViewById(R.id.reserveList);
//                //shelterAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterList);
//                // BarsList is a list of ReservationBarLayout
//                // which is a costum layout that enables you to reserve rooms...??
//                Model.getInstance().getCurrentShelter().setBarsList(getApplicationContext());
//                List<ReservationBarLayout> barsList = Model.getInstance().getCurrentShelter().getBars();
//                Model.getInstance().setBars(barsList);
//                ArrayAdapter<ReservationBarLayout> bars = new ArrayAdapter<ReservationBarLayout>(getApplicationContext(), android.R.layout.simple_list_item_1, barsList);
//                reserveList.setAdapter(bars);
//                builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // you update the vacancies however
//                        ////////////////////////////////////////
//                        ((HomelessPerson) Model.getInstance().get_currentUser()).setReservation(false);
//                        Log.d("Selected", Model.getInstance().getBars().get(0).getType());
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
    }
}
