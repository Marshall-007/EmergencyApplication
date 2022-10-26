package com.example.towtruck;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private CircularProgressButton mLogin;
    private ImageView _forgotLink;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        // To show on boarding screen for First time users
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //Check if we need to show on boarding fragment or not ( the welcome screen)

       // Auto log in if user is nit signed out
       mAuth = FirebaseAuth.getInstance();


        _forgotLink = (ImageView) findViewById(R.id.txtForgot);

        _forgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Forgot_Password.class);
                startActivity(intent);
                finish();
            }
        });


    mEmail = (EditText) findViewById(R.id.LTextEmail);
    mPassword = (EditText) findViewById(R.id.LTextPassword);

    mLogin = (CircularProgressButton) findViewById(R.id.cirLoginButton);


        mLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           mLogin.setEnabled(false) ;
           if (validate()) {

                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                     /////////////New Proghress Dialoge

                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Hello.."); // Setting Message
                        progressDialog.setTitle("Welcome back"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {

                                try {
                                    Thread.sleep(10000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();

                            }
                        }).start();
                        android.content.Intent intent = new android.content.Intent(LoginActivity.this , MainActivity.class);
                        startActivity(intent);
//                        finish();


                    }
                });
/// end of Dialoge new ?????????????????????????????








               // Loading dialog from login activity

//                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                                R.style.Theme_AppCompat_DayNight_Dialog);
//                        progressDialog.setIndeterminate(true);
//                        progressDialog.setMessage("Validating");
//                        progressDialog.show();

//                        new android.os.Handler().postDelayed(
//                                new Runnable() {
//                                    public void run() {
//                                        // On complete call  onLogout Success
//                                        Toast.makeText(getBaseContext(), "Validating...", Toast.LENGTH_LONG);
//                                        progressDialog.dismiss();
//                                        //  // onLoginFailed();
//                                        android.content.Intent intent = new android.content.Intent(LoginActivity.this , MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                        Toast.makeText(LoginActivity.this, "Welcome " , Toast.LENGTH_SHORT).show();
//
//
//
//                                    }
//                                }, 3000);
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                new AlertDialog.Builder(LoginActivity.this)
//                                        .setTitle("Sorry Denied!")
//                                        .setMessage("Error "+e.getMessage())
//                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                            }
//                                        })
//                                        .create()
//                                        .show();
//
//                            }
//                        });
            } else {
                onSignupFailed();

            }
       }
   });
}



    // Validateing the user input
    public boolean validate() {
        boolean valid = true;



        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Please enter a valid email address");
            mEmail.requestFocus();
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("Wrong password. Please make sure your password is valid");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;

    }


// When user fails to signup

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), " Sorry Log-in Failed. Check your internet connection.", Toast.LENGTH_LONG).show();

    }


    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}