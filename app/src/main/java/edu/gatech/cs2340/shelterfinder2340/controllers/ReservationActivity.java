package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.views.ReservationBarLayout;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        // Set onclick --> Be able to reserve a place

        // With a layout that allows users to pick the numbers of rooms to reserve
        //shelterAdapter = new ArrayAdapter<Shelter>(this, android.R.layout.simple_list_item_1, shelterList);
        // BarsList is a list of ReservationBarLayout
        // which is a custumed layout that enables you to reserve rooms...??
        Model.getInstance().getCurrentShelter().setBarsList(getApplicationContext());
        final List<ReservationBarLayout> barsList = Model.getInstance().getCurrentShelter().getBars();
        Log.d("Shelter", Model.getInstance().getCurrentShelter().toString());
        Model.getInstance().setBars(barsList);
        LinearLayout ln = findViewById(R.id.reserveLinear);
        for (ReservationBarLayout rb: barsList) {
            ln.addView(rb);
        }

        Button reservePos = findViewById(R.id.reservePositive);
        reservePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomelessPerson hp = (HomelessPerson) Model.getInstance().get_currentUser();
                hp.setRes(false);
                // Update current shelter
                String s = "";
                for (ReservationBarLayout r: barsList) {
                    s = s + r.toString();
                    Log.d("Room", r.toString());
                    // r.toString is like this: 3 WOMEN/CHILDREN room(s)
                    // You can update vacancies using this?

                    // reserveRoom function creates a new Room object within the HomelessPerson
                    // So that we know the person reserved here
                    Log.d("Current shelter", Model.getInstance().getCurrentShelter().getShelterName());
                    hp.reserveRoom(r.getSelectedRoom(), r.getType(), Model.getInstance().getCurrentShelter().getShelterName());
                    Model.getInstance().getCurrentShelter().updateVacancies(r.getSelectedRoom(), r.getType());
                }
                Log.d("Reserved", s);

                Intent i = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button cancelReserve = findViewById(R.id.cancelReservation);
        cancelReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShelterDetailActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
