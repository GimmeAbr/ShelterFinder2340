package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
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
    private CheckBox family;
    private CheckBox young;
    private CheckBox any;
    private CheckBox child;

    private boolean isMale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText nameText = findViewById(R.id.shelterName);
        nameText.setText("");
        nameText.setHint("Name of the shelter");

        family = findViewById(R.id.familiesButton);
        young = findViewById(R.id.youngAdultButton);
        any = findViewById(R.id.anyoneButton);
        child = findViewById(R.id.childrenButton);
        RadioGroup genderG = findViewById(R.id.genderGroup);
        genderG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.femaleButton) {
                    isMale = false;
                } else {
                    isMale = true;
                }
            }
        });

        Button searchB = findViewById(R.id.searchButton);
        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasFamily = family.isChecked();
                boolean hasYoung = young.isChecked();
                boolean hasAny = any.isChecked();
                boolean hasChild = child.isChecked();
                String stName = nameText.getText().toString();
                Intent searchIntent = new Intent(getApplicationContext(), Login_Success.class);
                searchIntent.putExtra("family", hasFamily);
                searchIntent.putExtra("young", hasYoung);
                searchIntent.putExtra("children", hasChild);
                searchIntent.putExtra("any", hasAny);
                searchIntent.putExtra("name", stName);
                searchIntent.putExtra("isMale", isMale);
                searchIntent.putExtra("Label", "search");
                startActivity(searchIntent);
                finish();
            }
        });
        Button cancelB = findViewById(R.id.cancelButton);
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), Login_Success.class);
                startActivity(myIntent);
                finish();
            }
        });

    }

}
