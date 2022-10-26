package com.example.towtruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(MapsFragment.newInstance("", ""));






// Animated floating button
        ImageView icon = new ImageView(this);
        // Create an icon
        icon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_add));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setTheme(FloatingActionButton.THEME_LIGHT)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                .build();


        // Building the buttons for the animated center section
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

//      Button 1
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.accident));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon)
                
                .build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Services.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
// Button 2
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.ambulance));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon).build();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ambulence.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });



//Button 3
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.tow));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon).build();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Tow.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

// Button 4
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.tire));
        SubActionButton button4 = itemBuilder.setContentView(itemIcon).build();

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,tire.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });



        //Button 5
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable( ContextCompat.getDrawable(getApplicationContext(),R.drawable.battry));
        SubActionButton button5 = itemBuilder.setContentView(itemIcon).build();


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Battry.class));
                overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });


//adding everything
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(0)
                .setEndAngle(-180)
              //  .setAnimationHandler(new SlideInAnimationHandler())
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                 .addSubActionView(button4)
                .addSubActionView(button5)
                .attachTo(actionButton)
                .build();
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            startActivity(new Intent(MainActivity.this, NotificationFragment.class));

                            return true;
                        case R.id.navigation_sms:

                            return true;
                        case R.id.navigation_notifications:
                            startActivity(new Intent(MainActivity.this,More.class));

                            return true;
                    }
                    return false;
                }
            };
}