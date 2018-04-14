package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Arrays;
import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    //UI references

    private EditText _email;
    private EditText _name;
    private EditText _password;
    private Spinner _userTypeSpinner;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private Activity thisOne;
    private Spinner _genderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button _register;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        _email = findViewById(R.id.email);
        _name = findViewById(R.id.name);
        _password = findViewById(R.id.password);
        _register = findViewById(R.id.registrationButton);
        _userTypeSpinner = findViewById(R.id.spinner);
         thisOne = this;
         _genderSpinner = findViewById(R.id.genderSpinner);

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
                Intent cancelIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(cancelIntent);
            }
        });

        _register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(_name.getText().toString().equals("") || _name.getText().toString().equals("") || _password.getText().toString().equals(""))) {
                    // myIntent.putExtra("id", _id.getText().toString());
                    // List<String> values = Arrays.asList(_name.getText().toString(),_password.getText().toString(),_userTypeSpinner.getSelectedItem().toString() );
                    // Set<String> set = new HashSet<>();
                    // set.addAll(values);
                    // editor.putStringSet(_id.getText().toString(), set);
                    // editor.commit();
                    String email = _email.getText().toString();
                    String password = _password.getText().toString();
                    final String attribute = _userTypeSpinner.getSelectedItem().toString();
                    final String gender = _genderSpinner.getSelectedItem().toString();
                    final String name = _name.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(thisOne, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (attribute.equals("User")) {
                                            final HomelessPerson hp = new HomelessPerson(name, gender, user.getUid());
                                            hp.setHasReservation(false);
                                            UserDao dao = new UserDao();
                                            dao.saveHomelessPerson(hp);
                                            Model.getInstance().set_currentUser(hp);
                                        }
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }

                                    // [START_EXCLUDE]
                                    // [END_EXCLUDE]
                                }
                            });


                } else {
                    Toast error_toast = Toast.makeText(getApplicationContext(), "No field can be left blank", Toast.LENGTH_LONG);
                    error_toast.show();
                    _name.setText("");
                    _name.setText("");
                    _password.setText("");
                }

            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent myIntent = new Intent(RegisterActivity.this, Login_Success.class);
            myIntent.putExtra("Label", "start");
            startActivity(myIntent);
        }
    }

//    private void updateUIHomeless(FirebaseUser user, HomelessPerson hp) {
//        if (user != null) {
//            Intent myIntent = new Intent(RegisterActivity.this, Login_Success.class);
//            myIntent.putExtra("Label", "start");
//            myIntent.putExtra("homelessName", hp.getName());
//            myIntent.putExtra("homelessGender", hp.getGender());
//            myIntent.putExtra("homelessRes", hp.hasReservation());
//            myIntent.putExtra("homelessId", hp.getId());
//            startActivity(myIntent);
//        }
//    }
}
