package com.example.towtruck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
public class Forgot_Password extends AppCompatActivity {
    private TextView Submit;

    private EditText email;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mCustomerDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);


        mAuth = FirebaseAuth.getInstance();


        Submit = (TextView)findViewById(R.id.SubmitButton);

        email = (EditText)findViewById(R.id.txtEmail) ;

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();

                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      Toast.makeText(Forgot_Password.this, "Your password has been sent to your email", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Forgot_Password.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Forgot_Password.this, "User has not been registered in our database", Toast.LENGTH_SHORT).show();

                            }
                        });
                if (mail.isEmpty()){

                    email.setError("Please enter a valid email address");
                }else {

                }
            }
        });


    }
}