package com.example.digitalthermometer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class NegativeReadingActivity extends AppCompatActivity {

    private Button btn_redirect_home;
    private ImageButton btn_more_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negative_reading);

        btn_redirect_home = (Button) findViewById(R.id.redirect_home_btn);
        btn_more_info = (ImageButton) findViewById(R.id.more_info_btn);

        btn_redirect_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NegativeReadingActivity.this, MainActivity.class));
            }
        });

        // Modal
        btn_more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogSymptoms dialogSymptoms = new CustomDialogSymptoms(NegativeReadingActivity.this);
                dialogSymptoms.show();
            }
        });

    }
}