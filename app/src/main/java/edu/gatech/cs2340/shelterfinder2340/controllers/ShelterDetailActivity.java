package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.DialogInterface;
import android.content.Intent;
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
import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;

public class ShelterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        // We can change this bit later. An admin / sheltercoordinator can visit this page too
        final HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
        final Shelter shelter = Model.getInstance().getCurrentShelter();
        ActionBar tb = getSupportActionBar();

        if (tb != null) {
            tb.setTitle(Model.getInstance().getCurrentShelter().getShelterName());
        }
        final Button reserveButton = findViewById(R.id.reserveButton);

        // isRes() indicates whether the HomelessPerson is allowed to reserve
        if (!hp.isRes()) {
            if (hp.checkShelter(shelter.getShelterName())) {
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
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), ReservationActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }


        /**
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them, whatever the user wants
         */



        // Sets up all the information of the shelters

        TextView capacity = (TextView) findViewById(R.id.capacity);
        capacity.setText(shelter.getCapacity());

        TextView gender = (TextView) findViewById(R.id.gender);
        gender.setText(shelter.getGender());

        TextView longtitude = (TextView) findViewById(R.id.longitude);
        longtitude.setText(shelter.getLongitude() + "");

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText(shelter.getLatitude() + "");

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(shelter.getAddress());

        TextView phone = (TextView) findViewById(R.id.phonenumber);
        phone.setText(shelter.getPhoneNumber());

        TextView vacancies = findViewById(R.id.vacancies);
        vacancies.setText(shelter.getVacancies());

        TextView reserved = findViewById(R.id.reservedText);
        String i = reserved.getText().toString();
        i = i + hp.getResString(shelter.getShelterName());
        reserved.setText(i);
    }
}
