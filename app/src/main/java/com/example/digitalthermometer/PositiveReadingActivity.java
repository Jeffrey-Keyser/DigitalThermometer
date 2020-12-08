package com.example.digitalthermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.util.List;

public class PositiveReadingActivity extends AppCompatActivity {

    private ImageButton btn_redirect_home;
    private ImageButton btn_google_maps;
    private DbHelper mydb;
    private ImageButton add_symptoms;

    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_reading);

        btn_google_maps = (ImageButton) findViewById(R.id.redirect_google_maps_btn);
        add_symptoms = (ImageButton) findViewById(R.id.add_symptoms);
        btn_redirect_home = (ImageButton) findViewById(R.id.redirect_home_btn);

        mydb = new DbHelper(this);

        Reading positiveReading = new Reading();
        String readingId = getIntent().getStringExtra(positiveReading.INTENT_IDENTIFIER_READING_ID);
        positiveReading = mydb.getReading(Integer.parseInt(readingId));

        title = (TextView) findViewById(R.id.positive_reading_title);
        title.setText(Double.toString(positiveReading.temp));

        btn_google_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PositiveReadingActivity.this, MapsActivity.class));
            }
        });

        // Modal
        add_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch reading again as it might've been updated
                Reading updatedReading = new Reading();
                updatedReading = mydb.getReading(Integer.parseInt(readingId));
                CustomDialogSymptoms dialogSymptoms = new CustomDialogSymptoms(PositiveReadingActivity.this, Integer.parseInt(readingId), updatedReading.symptoms);
                dialogSymptoms.show();
            }
        });

        btn_redirect_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PositiveReadingActivity.this, MainActivity.class));
            }
        });


    }
}