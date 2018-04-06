package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;

/**
 * ShelterDetailActivity is a class used for displaying a shelter's details
 */
public class ShelterDetailActivity extends AppCompatActivity {
    private final Model model = Model.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        ActionBar tb = getSupportActionBar();

        /*
         * Get the current shelter from the model
         */
        final Shelter currentShelter = model.getCurrentShelter();
        final HomelessPerson hp = model.getCurrentUser();

        // Set all of the text fields based on shelter data
        TextView capacity = findViewById(R.id.capacity);
        capacity.setText(currentShelter.getCapacity());

        TextView gender = findViewById(R.id.gender);
        gender.setText(currentShelter.getGender());

        TextView longtitude = findViewById(R.id.longitude);
        longtitude.setText(String.format(Locale.US, "%f", currentShelter.getLongitude()));

        TextView latitude = findViewById(R.id.latitude);
        latitude.setText(String.format(Locale.US, "%f", currentShelter.getLatitude()));

        TextView address = findViewById(R.id.address);
        address.setText(currentShelter.getAddress());

        TextView phone = findViewById(R.id.phonenumber);
        phone.setText(currentShelter.getPhoneNumber());

        TextView vacancies = findViewById(R.id.vacancies);
        vacancies.setText(String.format(Locale.US, "%d",
                currentShelter.calculateVacancies()));

        final Button reserveButton = findViewById(R.id.reserveButton);

        /*
         * This Block is dedicated to deciding if the HomelessPerson reserved rooms at the place
         *
         * If he/she does reserve a room here, the button should change its text to "Release"
         * And then maybe either release all the rooms or just release some of them,
         * whatever the user wants
         */
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
                setUnableReserve(reserveButton, getApplicationContext());
            }
        } else {
            // hp does have a reservation
            if (hp.getReserveList().isEmpty()) {
                if (hp.hasReservedAtShelter(currentShelter)) {
                    setReserveButton(reserveButton, getApplicationContext());
                    reserveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentShelter.releaseReservations(hp.getReserveList());
                            Model.getInstance().saveEverything();
                            Intent i = new Intent(getApplicationContext(), Login_Success.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
            } else {
                setUnableReserve(reserveButton, getApplicationContext());
            }
        }
        if (tb != null) {
            tb.setTitle(Model.getInstance().getCurrentShelter().getShelterName());
        }
    }

    private static void setReserveButton(Button reserveButton, Context context) {
        reserveButton.setClickable(true);
        reserveButton.setEnabled(true);
        reserveButton.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        reserveButton.setText(R.string.release_reservations);
    }

    private static void setUnableReserve(Button reserveButton, Context context) {
        reserveButton.setClickable(false);
        reserveButton.setEnabled(false);
        reserveButton.setBackgroundColor(context.getResources().getColor(R.color.disable_grey));
    }
}
