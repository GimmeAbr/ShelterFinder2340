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
import edu.gatech.cs2340.shelterfinder2340.model.Reservation;
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

        currentShelter.setRoomList(currentShelter.getRoomList());
        /**
         * Set all of the text fields based on shelter data
         */
        TextView capacity = (TextView) findViewById(R.id.capacity);
        capacity.setText(currentShelter.getCapacity());

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

        TextView vacancies = findViewById(R.id.vacancies);
        vacancies.setText(currentShelter.calculateVacancies() + "");

        final Button reserveButton = findViewById(R.id.reserveButton);

        /**
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them, whatever the user wants
         */
        // TODO: Check if the person has reserved; if so, which shelter
        if (!hp.getHasReservation()) {
            if (!currentShelter.reservedOut()) {
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), ReserveRoomActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            } else {
                reserveButton.setClickable(false);
                reserveButton.setEnabled(false);
                reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
            }
        } else {
            // hp does have a reservation
            if (hp.getReserveList().get(0).getResRoom().getShelterName().equals(currentShelter.getShelterName())) {
                reserveButton.setText("Release");
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (Reservation reservation: hp.getReserveList()) {
                            currentShelter.releaseReservation(reservation);
                        }
                        hp.releaseAllReservations();
                        hp.setHasReservation(false);
                        Intent i = new Intent(getApplicationContext(), Login_Success.class);
                        startActivity(i);
                        finish();
                    }
                });
            } else {
                reserveButton.setClickable(false);
                reserveButton.setEnabled(false);
                reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
            }
        }

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
                    List<Room> roomList = Model.getInstance().getCurrentShelter().getRoomList();
//                    List<Room> roomList = new ArrayList<>();
//                    roomList.add(new Room(4,"Deluxe", Model.getInstance().getCurrentShelter().getShelterName()));
//                    roomList.add(new Room(2,"Lesure", Model.getInstance().getCurrentShelter().getShelterName()));
//                    roomList.add(new Room(7,"Crap", Model.getInstance().getCurrentShelter().getShelterName()));
                    Model.getInstance().getCurrentShelter().setRoomList(roomList);
                    Intent intent = new Intent(getApplicationContext(), ReserveRoomActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
