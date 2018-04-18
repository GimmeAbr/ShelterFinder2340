package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;

import edu.gatech.cs2340.shelterfinder2340.model.ShelterDao;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

/**
 * This class is used for displaying the current shelter's details
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

        currentShelter.setRoomList(currentShelter.getRoomList());
        /*
         * Set all of the text fields based on shelter data
         */
        TextView capacity = findViewById(R.id.capacity);
        capacity.setText(currentShelter.getCapacity());

        TextView gender = findViewById(R.id.gender);
        gender.setText(currentShelter.getGender());

        TextView longitude = findViewById(R.id.longitude);
        longitude.setText(String.format(Locale.US, "%f", currentShelter.getLongitude()));

        TextView latitude = findViewById(R.id.latitude);
        latitude.setText(String.format(Locale.US, "%f", currentShelter.getLatitude()));

        TextView address = findViewById(R.id.address);
        address.setText(currentShelter.getAddress());

        TextView phone = findViewById(R.id.phonenumber);
        phone.setText(currentShelter.getPhoneNumber());

        TextView vacancies = findViewById(R.id.vacancies);
        vacancies.setText(String.format(Locale.US, "%d", currentShelter.calculateVacancies()));

        Linkify.addLinks(address, Linkify.ALL);
        Linkify.addLinks(phone, Linkify.ALL);

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
                        slideOutToActivity(ReserveRoomActivity.class);
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
            if (hp.compareShelter(currentShelter)) {
                // hp.compareShelter(currentShelter)
                reserveButton.setClickable(true);
                reserveButton.setEnabled(true);
                reserveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                reserveButton.setText("Release All Rooms");
                reserveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentShelter.releaseReservations(hp.getReserveList());
                        ShelterDao sDao = new ShelterDao();
                        sDao.updateShelter(currentShelter);
                        UserDao userDao = new UserDao();
                        userDao.saveHomelessPerson((HomelessPerson) Model.getInstance()
                                .get_currentUser());
                        slideOutToActivity(Login_Success.class);
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

    }

    private void slideOutToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //Intent i = new Intent(getApplicationContext(), FilterActivity.class);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        startActivity(intent);
    }
}
