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

public class Tow extends AppCompatActivity {
    private CircularProgressButton mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow);

        // spinner 2
        List<String> spinnerArray1 =  new ArrayList<String>();
        spinnerArray1.add("Select--");
        spinnerArray1.add("Flat-bed Truck");
        spinnerArray1.add("Integrated Tow Truck");
        spinnerArray1.add("Hook and Chin Tow Truck");
        spinnerArray1.add("Wheel-Lift Tow Truck");


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray1);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems1 = (Spinner) findViewById(R.id.spinner1T);
        sItems1.setAdapter(adapter1);


        // spinner 2
        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Select--");
        spinnerArray2.add("Yes");
        spinnerArray2.add("No");



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.spinner2T);
        sItems2.setAdapter(adapter2);




        mRegister = (CircularProgressButton) findViewById(R.id.cirRegisterButton);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Tow.this, "Submited", Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(Tow.this)
                        .setTitle("Thank you")
                        .setMessage(" Your request has been received  \n " +
                                "Help is on its way")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Tow.this,MainActivity.class);
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