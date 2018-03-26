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

        /**
         * Handle button click based on the reservation status of the user
         */
        Button reserveButton = findViewById(R.id.reserveButton);
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set onclick --> Be able to reserve a place
                homelessRes = false;
                // Use hashmaps to store different types and values?
            }
        });

    }
}
