package com.example.towtruck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Battry extends AppCompatActivity {
    private CircularProgressButton mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battry);

// spinner 1
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select battery type");
        spinnerArray.add("Willard");
        spinnerArray.add("Raylite");
        spinnerArray.add("Auto Zone");
        spinnerArray.add("Not Specified");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner12);
        sItems.setAdapter(adapter);


        // spinner 2
        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Select--");
        spinnerArray2.add("Yes");
        spinnerArray2.add("No");



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner32);
        sItems2.setAdapter(adapter2);


        mRegister = (CircularProgressButton) findViewById(R.id.cirRegisterButton);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Battry.this, "Submitted", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(Battry.this)
                        .setTitle("Thank you")
                        .setMessage(" Your request has been received  \n " +
                                "Help is on its way")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Battry.this,MainActivity.class);
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