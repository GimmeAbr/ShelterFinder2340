package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Admin;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

public class LoginFacebookDetails extends AppCompatActivity {

    private EditText _name;
    private Spinner _userTypeSpinner;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private Activity thisOne;
    private Spinner _genderSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button _register;
        setContentView(R.layout.activity_login_facebook_details);
        mAuth = FirebaseAuth.getInstance();
        _name = findViewById(R.id.name_fb);
        _register = findViewById(R.id.finish_btn);
        _userTypeSpinner = findViewById(R.id.spinner_fb);
        thisOne = this;
        _genderSpinner = findViewById(R.id.genderSpinner_fb);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> spinnerValues = Arrays.asList("Admin", "User", "Shelter Coordinator");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _userTypeSpinner.setAdapter(adapter);

        List<String> genderValues = Arrays.asList("Male", "Female");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, genderValues);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _genderSpinner.setAdapter(genderAdapter);


        Button cancel = findViewById(R.id.cancel_register);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOutToActivity(WelcomeActivity.class);
            }
        });
        _register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(_name.getText().toString().equals("") || _name.getText().toString().equals(""))) {
                    // myIntent.putExtra("id", _id.getText().toString());
                    // List<String> values = Arrays.asList(_name.getText().toString(),_password.getText().toString(),_userTypeSpinner.getSelectedItem().toString() );
                    // Set<String> set = new HashSet<>();
                    // set.addAll(values);
                    // editor.putStringSet(_id.getText().toString(), set);
                    // editor.commit();
                    FirebaseUser user = mAuth.getCurrentUser();

                    final String attribute = _userTypeSpinner.getSelectedItem().toString();
                    final String gender = _genderSpinner.getSelectedItem().toString();
                    final String name = _name.getText().toString();
                    if (attribute.equals("User")) {
                        final HomelessPerson hp = new HomelessPerson(name, gender, user.getUid());
                        hp.setHasReservation(false);
                        UserDao dao = new UserDao();
                        dao.saveHomelessPerson(hp);
                        Model.getInstance().set_currentUser(hp);

                    } else if (attribute.equals("Admin")) {
                        Admin admin = new Admin(name, user.getUid());
                        UserDao dao = new UserDao();
                        dao.saveAdmin(admin);
                        Model.getInstance().set_currentUser(admin);
                    }
                    updateUI(user);

                } else {
                    Toast error_toast = Toast.makeText(getApplicationContext(), "No field can be left blank", Toast.LENGTH_LONG);
                    error_toast.show();
                    _name.setText("");
                    _name.setText("");
                }

            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (Model.getInstance().get_currentUser().getAttribute().equals("Admin")) {
                //TODO: Switch to admin
                fadeOutToActivity(Login_Success.class);
            } else {
                fadeOutToActivity(Login_Success.class);
            }
        }
    }
    private void fadeOutToActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getApplicationContext(), activity);
        //Intent i = new Intent(getApplicationContext(), FilterActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
