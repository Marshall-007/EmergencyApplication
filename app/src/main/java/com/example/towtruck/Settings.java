package com.example.towtruck;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class Settings extends AppCompatActivity {
    private CircularProgressButton mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }
}
