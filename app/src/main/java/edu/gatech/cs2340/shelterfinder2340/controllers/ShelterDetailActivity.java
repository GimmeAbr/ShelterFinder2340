package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;



public class ShelterDetailActivity extends AppCompatActivity {
    private boolean homelessRes;
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        ActionBar tb = getSupportActionBar();

        /**
         * Get the current shelter from the model
         */
        model = Model.getInstance();
        Shelter currentShelter = model.getCurrentShelter();
        HomelessPerson hp = model.getCurrentUser();
        Shelter shelter = model.getCurrentShelter();

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
        if (!hp.hasReservation()) {
            if (hp.getReservedShelter().getShelterName().equals(shelter.getShelterName())) {
                reserveButton.setText("Release");
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        shelter.releaseByList(hp.getReserveList());
                        hp.setRes(true);
                        hp.releaseRooms();
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
        } else {
            if (!shelter.reservedOut()) {
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), ReservationActivity.class);
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


        /**
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them, whatever the user wants
         */


    }
}
