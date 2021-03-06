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

public class ShelterDetailActivity extends AppCompatActivity {

    private boolean homelessRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        ActionBar tb = getSupportActionBar();

        Bundle extras = getIntent().getExtras();
        if (tb != null) {
            tb.setTitle(extras.getString("shelterName"));
        }
        Button reserveButton = findViewById(R.id.reserveButton);
        if (!(extras.getBoolean("homelessRes"))) {
            reserveButton.setClickable(false);
            reserveButton.setBackgroundColor(getResources().getColor(R.color.disable_grey));
        }
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set onclick --> Be able to reserve a place
                homelessRes = false;
                // Use hashmaps to store different types and values?
            }
        });

        TextView capacity = (TextView) findViewById(R.id.capacity);
        capacity.setText(extras.getString("shelterCapacity"));

        TextView gender = (TextView) findViewById(R.id.gender);
        gender.setText(extras.getString("shelterGender"));

        TextView longtitude = (TextView) findViewById(R.id.longitude);
        longtitude.setText("" + extras.get("shelterLongitude"));

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("" + extras.get("shelterLatitude"));

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(extras.getString("shelterAddress"));

        TextView phone = (TextView) findViewById(R.id.phonenumber);
        phone.setText(extras.getString("phoneNumber"));

    }
}
