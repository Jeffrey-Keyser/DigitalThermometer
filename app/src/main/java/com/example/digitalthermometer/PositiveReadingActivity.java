package com.example.digitalthermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PositiveReadingActivity extends AppCompatActivity {

    private Button btn_google_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_reading);

        btn_google_maps = (Button) findViewById(R.id.redirect_google_maps_btn);

        btn_google_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PositiveReadingActivity.this, MapsActivity.class));
            }
        });

    }
}