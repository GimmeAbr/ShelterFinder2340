package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;

public class Login_Success extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logOutIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(logOutIntent);
            }
        });

        display = (TextView) findViewById(R.id.displayID);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> set = preferences.getStringSet(id, null);
        List<String> convertedSet = new ArrayList<>();
        // convertedSet.addAll(set);
        // display.setText(convertedSet.get(0));
    }
}
