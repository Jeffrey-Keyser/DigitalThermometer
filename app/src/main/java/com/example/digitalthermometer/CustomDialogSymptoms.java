package com.example.digitalthermometer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class CustomDialogSymptoms extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button btn_submit;
    private ChipGroup symptom_list;

    public CustomDialogSymptoms(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.symptom_list);
        btn_submit = (Button) findViewById(R.id.submit_btn);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                symptom_list = (ChipGroup) findViewById(R.id.symptom_list);
                final List<Integer> selected_symptoms = symptom_list.getCheckedChipIds();

                // TODO: associate symptoms with reading
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}
