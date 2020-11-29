package com.example.digitalthermometer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomDialogReading extends Dialog implements android.view.View.OnClickListener {

    public Reading mReading;
    public Activity c;
    public Dialog d;

    private TextView temp, time;

    public CustomDialogReading(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_details);

        temp = (TextView) findViewById(R.id.view_reading_temp);
        time = (TextView) findViewById(R.id.view_reading_time);

        temp.setText(mReading.temp.toString());
        time.setText(mReading.time.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
        dismiss();
    }
}
