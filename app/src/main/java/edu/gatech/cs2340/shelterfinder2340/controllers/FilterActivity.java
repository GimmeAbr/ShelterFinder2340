package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import edu.gatech.cs2340.shelterfinder2340.R;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText nameText = findViewById(R.id.shelterName);
        CheckBox family = findViewById(R.id.familiesButton);
        CheckBox young = findViewById(R.id.youngAdultButton);
        CheckBox any = findViewById(R.id.anyoneButton);
        CheckBox children = findViewById(R.id.childrenButton);
        RadioGroup genderG = findViewById(R.id.genderGroup);
        genderG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });

        Button searchB = findViewById(R.id.searchButton);

    }

}
