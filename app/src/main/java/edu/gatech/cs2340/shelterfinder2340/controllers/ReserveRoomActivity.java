package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.shelterfinder2340.R;

public class ReserveRoomActivity extends AppCompatActivity {

    TextView shelterName;
    ScrollView scrollView;
    Button reserveButton;
    Button addButton;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shelterName = findViewById(R.id.shelter_name_reserve);
        scrollView = findViewById(R.id.scroll_view);
        reserveButton = findViewById(R.id.reserveBtn);
        addButton = findViewById(R.id.add_room);
        layout = findViewById(R.id.linearLayout);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner roomSpinner = new Spinner(getApplicationContext());
                EditText numInput = new EditText(getApplicationContext());
                numInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                layout.addView(roomSpinner);
                layout.addView(numInput);
            }
        });
        


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
