package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import java.util.Arrays;
import edu.gatech.cs2340.shelterfinder2340.R;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Set;
import java.util.HashSet;
public class RegisterActivity extends AppCompatActivity {

    //UI references

    private EditText _name;
    private EditText _id;
    private EditText _password;
    private Spinner _userTypeSpinner;
    private Button _register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _name = findViewById(R.id.name);
        _id = findViewById(R.id.id);
        _password = findViewById(R.id.password);
        _register = findViewById(R.id.registrationButton);
        _userTypeSpinner = findViewById(R.id.spinner);

        List<String> spinnerVals = Arrays.asList("Admin", "User");

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, spinnerVals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _userTypeSpinner.setAdapter(adapter);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        Button cancel = (Button) findViewById(R.id.cancel_register);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(cancelIntent);
            }
        });

        _register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(_name.getText().toString().equals("") || _id.getText().toString().equals("") || _password.getText().toString().equals(""))) {

                    Intent myIntent = new Intent(RegisterActivity.this, Login_Success.class);
                    myIntent.putExtra("id", _id.getText().toString());
                    List<String> vals = Arrays.asList(_name.getText().toString(),_password.getText().toString(),_userTypeSpinner.getSelectedItem().toString() );
                    Set<String> set = new HashSet<>();
                    set.addAll(vals);
                    editor.putStringSet(_id.getText().toString(), set);
                    editor.commit();

                    startActivity(myIntent);


                } else {
                    Toast error_toast = Toast.makeText(getApplicationContext(), "No field can be left blank", Toast.LENGTH_LONG);
                    error_toast.show();
                    _name.setText("");
                    _id.setText("");
                    _password.setText("");

                }

            }
        });

    }
}
