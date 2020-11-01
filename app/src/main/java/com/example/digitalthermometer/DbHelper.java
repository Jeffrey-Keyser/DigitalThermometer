package com.example.digitalthermometer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "MyReadings.db";
    private static final String READINGS_TABLE_NAME = "readings";
    private static final String READINGS_COLUMN_ID = "id";
    private static final String READINGS_COLUMN_TIME = "time";
    private static final String READINGS_COLUMN_TEMP = "temp";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table readings" + "(id integer primary key, time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS readings");
        onCreate(db);
    }

    public boolean insertReading(String time, String temp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("temp", temp);
        db.insert("readings", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from readings where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, READINGS_TABLE_NAME);
        return numRows;
    }

    public Integer deleteReading (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("readings",
                "id = ? ", new String[] {Integer.toString(id)});
    }

    public ArrayList<String> getAllReadings() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from readings", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(READINGS_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

}
