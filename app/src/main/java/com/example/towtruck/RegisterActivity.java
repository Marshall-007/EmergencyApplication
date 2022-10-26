package com.example.towtruck;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
    private EditText mName, mSurname, mEmail, mPassword, mMobile ;

    private CircularProgressButton mRegister;

    private  String Name;
    private  String Surname;
    private  String Mobile;
    private  String Email;
    private  String Password;

    private Uri resultUri;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();


        mEmail = (EditText) findViewById(R.id.editTextEmail);
        mPassword = (EditText) findViewById(R.id.editTextPassword);
        mName = (EditText) findViewById(R.id.editTextName);
        mSurname = (EditText) findViewById(R.id.editTextSurname);
        mMobile = (EditText) findViewById(R.id.editTextMobile);


        mRegister = (CircularProgressButton) findViewById(R.id.cirRegisterButton);


        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String surname = mSurname.getText().toString().trim();
        String mobile = mMobile.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null) {
            userID = mAuth.getCurrentUser().getUid();
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(userID);
        }


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onSignupFailed();

                } else {

                    final String email = mEmail.getText().toString();
                    final String password = mPassword.getText().toString();
              mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                      if(task.isSuccessful()) {
                          RegisterdUsers registerdUsers = new RegisterdUsers(name, surname, email, mobile, password);
                          FirebaseDatabase.getInstance().getReference()
                                  .child("Users")
                                  .child(FirebaseAuth.getInstance().getUid())
                                  .setValue(registerdUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NotNull Task<Void> task) {
                                          saveUserInformation();
                                          if (task.isSuccessful()) {
                                              Toast.makeText(RegisterActivity.this, "User has been successfully Registerd", Toast.LENGTH_LONG).show();
                                              AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                                      .setTitle("Thank you for Registering")
                                                      .setMessage("Welcome  " + mName.getText().toString() +
                                                              ".\nYour registered email is  \n " + email)// email
                                                      .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                              startActivity(intent);
                                                              Toast.makeText(RegisterActivity.this, "User Successfully saved", Toast.LENGTH_SHORT).show();

                                                          }
                                                      })
                                                      .create();
                                              dialog.show();

                                          } else {
                                              AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                                      .setTitle("Sorry")
                                                      .setMessage("Your account has not been created.\nCheck your internet connection or\nPlease contact 0813471724 for assistance")
                                                      .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                              startActivity(intent);
                                                              finish();
                                                          }
                                                      })
                                                      .create();
                                              dialog.show();

                                              Toast.makeText(RegisterActivity.this, "User Registration Failed. Email already exists", Toast.LENGTH_LONG).show();
                                              mEmail.findFocus();
                                              Toast.makeText(RegisterActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                                          }
                                      }
                                  });

                      }

                  }
              });

                }
            }
    });
    }
    

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    // Validateing the user input
    public boolean validate() {
        boolean valid = true;

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String surname = mSurname.getText().toString().trim();
        String mobile = mMobile.getText().toString().trim();




        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Please enter a valid email address");
            mEmail.requestFocus();
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError(" Must have between 4 and 10 alphanumeric characters");
            mPassword.requestFocus();
            valid = false;
        } else {
            mPassword.setError(null);
        }


        if (name.isEmpty() || name.length() < 3 || name.length() > 15) {
            mName.setError("Name must be 3 or more letters");
            mName.requestFocus();
            valid = false;
        } else {
            mName.setError(null);
        }
        if (surname.isEmpty() || surname.length() < 3 || surname.length() > 15) {
            mSurname.setError("Surname must be 3 or more letters");
            mSurname.requestFocus();
            valid = false;
        } else {
            mSurname.setError(null);
        }
        if (mobile.length() != 10) {
            mMobile.setError("Mobile number must be a 10 digit number");
            valid = false;
        } else {
            mMobile.setError(null);
        }
        return valid;
    }

    private void getUserInfo(){
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null&&map.get("email")!=null){
                        // when the users info has been saved to the database successfully
                        Name = map.get("name").toString();
                        Email = map.get("email").toString();


                        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("Thank you for Registering")
                                .setMessage("Welcome" +Name+ "\n " +
                                        "Your registered email is \n" + Email)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "User Successfully saved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        dialog.show();




                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


// Saveing the users information to the database
    private void saveUserInformation() {
        Name = mName.getText().toString();
        Mobile = mMobile.getText().toString();
        Surname = mSurname.getText().toString();
        Email = mEmail.getText().toString();



        Map userInfo = new HashMap();
        userInfo.put("name", Name);
        userInfo.put("surname", Surname);
        userInfo.put("mobile", Mobile);
        userInfo.put("email", Email);
        finish();

      mCustomerDatabase.updateChildren(userInfo);
        // Check this code
        // If it does not work use -->   finish();  <--

           getUserInfo();


    }



// When user fails to signup

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), " Sorry Log-in Failed ", Toast.LENGTH_LONG).show();


    }

// Redirect back so that the user can go to login page
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }



}