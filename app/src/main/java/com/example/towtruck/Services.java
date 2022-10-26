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

public class Services extends AppCompatActivity {
    private CircularProgressButton mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);


// spinner 1
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Select emergency type");
        spinnerArray.add("Car Collision");
        spinnerArray.add("Medical Assistance");
        spinnerArray.add("Road Accident");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner1S);
        sItems.setAdapter(adapter);


        // spinner 2
        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Select--");
        spinnerArray2.add("Yes");
        spinnerArray2.add("No");



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner3S);
        sItems2.setAdapter(adapter2);

        // spinner 2
        List<String> spinnerArray3 =  new ArrayList<String>();
        spinnerArray3.add("Select--");
        spinnerArray3.add("1");
        spinnerArray3.add("2");
        spinnerArray3.add("3");
        spinnerArray3.add("4");
        spinnerArray3.add("More than 4");


        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray3);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems3 = (Spinner) findViewById(R.id.spinner2S);
        sItems3.setAdapter(adapter3);




        mRegister = (CircularProgressButton) findViewById(R.id.cirRegisterButton);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Services.this, "Submited", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(Services.this)
                        .setTitle("Thank you")
                        .setMessage(" Your request has been received  \n " +
                                "Help is on its way")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Services.this,MainActivity.class);
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