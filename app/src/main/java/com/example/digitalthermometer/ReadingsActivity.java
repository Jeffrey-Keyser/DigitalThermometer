package com.example.digitalthermometer;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReadingsActivity extends AppCompatActivity {

    private DbHelper mydb;
    private ListView obj;

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    TextView time, temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        time = (TextView) findViewById(R.id.time);
        mRecyclerView = findViewById(R.id.recyclerview);

        mydb = new DbHelper(this);
        ArrayList mydbAllReadings = mydb.getAllReadings();

        mAdapter = new WordListAdapter(this, useDummyData());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<String> useDummyData() {
        ArrayList<String> dummyData = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            dummyData.add("Dummy Data N: " + i);
        }

        return dummyData;
    }
}