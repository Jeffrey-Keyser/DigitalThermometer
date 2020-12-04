package com.example.digitalthermometer;

import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FormatHelpers {

    @NotNull
    public ArrayList<String> fromSerializedSymptomsToStrings(@NotNull String symptoms) {

        if (symptoms.equals("None")) {
            ArrayList<String> noSymptoms = new ArrayList<String>();
            noSymptoms.add("None");
            return noSymptoms;
        }

        Type type = new TypeToken<ArrayList<Symptoms>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Symptoms> outputArray = gson.fromJson(symptoms, type);
        ArrayList<String> output = new ArrayList<String>();

        for (Symptoms symptom: outputArray) {
            output.add(symptom.name());
        }

        return output;
    }

    @NotNull
    public ArrayList<Symptoms> fromSerializedSymptomsToSymptoms(@NotNull String symptoms) {

        if (symptoms.equals("None")) {
            ArrayList<Symptoms> noSymptoms = new ArrayList<Symptoms>();
            noSymptoms.add(Symptoms.None);
            return noSymptoms;
        }

        Type type = new TypeToken<ArrayList<Symptoms>>() {}.getType();
        Gson gson = new Gson();
        ArrayList<Symptoms> outputArray = gson.fromJson(symptoms, type);
        ArrayList<Symptoms> output = new ArrayList<Symptoms>();

        for (Symptoms symptom: outputArray) {
            output.add(symptom);
        }

        return output;
    }
}
