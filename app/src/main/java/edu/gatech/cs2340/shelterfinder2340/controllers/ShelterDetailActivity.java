package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Shelter;

public class ShelterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);

        ActionBar tb = getSupportActionBar();

        Shelter st = (Shelter) getIntent().getSerializableExtra("shelter");
        if (tb != null) {
            tb.setTitle(st.getShelterName());
        }

        TextView capacity = (TextView) findViewById(R.id.capacity);
        capacity.setText(st.getCapacity());

        TextView gender = (TextView) findViewById(R.id.gender);
        gender.setText(st.getGender());

        TextView longtitude = (TextView) findViewById(R.id.longitude);
        longtitude.setText("" + st.getLongitude());

        TextView latitude = (TextView) findViewById(R.id.latitude);
        latitude.setText("" + st.getLatitude());

        TextView address = (TextView) findViewById(R.id.address);
        address.setText(st.getAddress());

        TextView phone = (TextView) findViewById(R.id.phonenumber);
        phone.setText(st.getPhoneNumber());

    }
}
