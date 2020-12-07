package com.example.digitalthermometer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.main_activity_layout);
        constraintLayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(MainActivity.this, "capture", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ThermalActivity.class));
            }
            public void onSwipeLeft() {
                //Toast.makeText(MainActivity.this, " database", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ReadingsActivity.class));
            }
            public void onSwipeBottom() {
               // Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }
}