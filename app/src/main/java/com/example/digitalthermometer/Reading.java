package com.example.digitalthermometer;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "readings")
public class Reading {

    @PrimaryKey
    @NonNull
    public Integer id;

    public Double temp;

    public Date time;

    public Reading getReading(){
        Reading mReading = new Reading();
        mReading.id = this.id;
        mReading.temp = this.temp;
        mReading.time = this.time;

        return mReading;
    }

}
