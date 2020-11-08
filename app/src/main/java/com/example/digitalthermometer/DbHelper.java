package com.example.digitalthermometer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "MyReadings.db";
    public static final String READINGS_TABLE_NAME = "readings";
    public static final String READINGS_COLUMN_ID = "id";
    public static final String READINGS_COLUMN_TIME = "time";
    public static final String READINGS_COLUMN_TEMP = "temperature";

    private final String DELETE_FROM_DB = "DELETE FROM readings WHERE id = ";
    // Sorts all the columns
    private final String SORT_DB_BY = "SELECT * FROM readings ORDER BY ";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table readings" + "(id integer primary key, time text, temperature text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS readings");
        onCreate(db);
    }

    // WORKING
    public boolean insertReading(String time, String temperature){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("temperature", temperature);
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

    // WORKING
    public void deleteReading (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(DELETE_FROM_DB + Integer.toString(id));

        /*return db.delete("readings",
                "id = " + Integer.toString(id), null); */
    }

    // WORKING
    public ArrayList<Reading> getAllReadings() {
        ArrayList<Reading> array_list = new ArrayList<Reading>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from readings", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Reading mReading = new Reading();
            mReading.temp = res.getString(res.getColumnIndex(READINGS_COLUMN_TEMP));
            mReading.time = res.getString(res.getColumnIndex(READINGS_COLUMN_TIME));
            mReading.id = res.getInt(res.getColumnIndex(READINGS_COLUMN_ID));

            array_list.add(mReading);
            res.moveToNext();
        }

        return array_list;
    }

    // WORKING
    public ArrayList<Reading> sortReadings(String column) {
        ArrayList<Reading> array_list = new ArrayList<Reading>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(SORT_DB_BY + column, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Reading mReading = new Reading();
            mReading.temp = res.getString(res.getColumnIndex(READINGS_COLUMN_TEMP));
            mReading.time = res.getString(res.getColumnIndex(READINGS_COLUMN_TIME));
            mReading.id = res.getInt(res.getColumnIndex(READINGS_COLUMN_ID));

            array_list.add(mReading);
            res.moveToNext();
        }

        return array_list;
    }


    // Currently exporting data to downloads folder
    public boolean exportDBtoCSV(Context context) {
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        Log.e("DbHelper", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
        if (!exportDir.exists()){
            exportDir.mkdir();
        }

        File file = new File(exportDir, "myExportedReadings.csv");

        try {
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM readings", null);
            csvWriter.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                // Specify exporting columns here
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                csvWriter.writeNext(arrStr);
            }
            csvWriter.close();
            curCSV.close();

            return true;
        }
        catch (Exception ex)
        {
            Log.e("DbHelper", ex.getMessage(), ex);
            return false;
        }

    }

}
