package com.example.towtruck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class tire extends AppCompatActivity {
    private CircularProgressButton mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tire);

        mRegister = (CircularProgressButton) findViewById(R.id.cirRegisterButton);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tire.this, "Submited", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(tire.this)
                        .setTitle("Thank you")
                        .setMessage(" Your request has been received  \n " +
                                "Help is on its way")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(tire.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create();
                dialog.show();


            }
        });

    }
}