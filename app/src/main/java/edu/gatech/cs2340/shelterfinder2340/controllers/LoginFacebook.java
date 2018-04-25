package edu.gatech.cs2340.shelterfinder2340.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import edu.gatech.cs2340.shelterfinder2340.R;
import edu.gatech.cs2340.shelterfinder2340.model.Admin;
import edu.gatech.cs2340.shelterfinder2340.model.HomelessPerson;
import edu.gatech.cs2340.shelterfinder2340.model.Model;
import edu.gatech.cs2340.shelterfinder2340.model.User;
import edu.gatech.cs2340.shelterfinder2340.model.UserDao;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.logging.Logger;

public class LoginFacebook extends AppCompatActivity {

    FirebaseAuth mAuth;
    CallbackManager mCallbackManager;
    Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        appContext = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_facebook);
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("debug", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("debug", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("debug", "facebook:onError", error);
                // ...
            }
        });
    }
    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d("debug", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("debug", "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            UserDao dao = new UserDao();
                            dao.homelessUserQuery(mAuth.getUid(),new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot snapshot = task.getResult();
                                        if (snapshot.getDocuments().size() == 0) {
                                            //go to login Facebook details
                                            updateUI(false);
                                        } else {
                                            // pull user details
                                            User currentUser = snapshot.getDocuments().get(0).toObject(HomelessPerson.class);
                                            Model.getInstance().set_currentUser(currentUser);
                                            updateUI(true);
                                        }
                                    } else {
                                        // ERRRORRRRRRRRR
                                        Log.d("debug", "fuck");
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("debug", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginFacebook.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    private void updateUI(boolean userFound) {
            if (userFound) {
                Intent loginSuccess = new Intent(getApplicationContext(), Login_Success.class);
                appContext.startActivity(loginSuccess);
                finish();
            }
            else {
                Intent loginSuccess = new Intent(getApplicationContext(), LoginFacebookDetails.class);
                loginSuccess.putExtra("facebook",true);
                appContext.startActivity(loginSuccess);
            }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
