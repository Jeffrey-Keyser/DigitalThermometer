package com.example.digitalthermometer;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ReadingsActivity extends AppCompatActivity {

    private DbHelper mydb;
    private ListView obj;

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    TextView word;

    private ArrayList<Reading> mydbAllReadings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("My Readings");

        word = (TextView) findViewById(R.id.word);
        mRecyclerView = findViewById(R.id.recyclerview);

        mydb = new DbHelper(this);

        createDbDummyData();

        mydbAllReadings = mydb.getAllReadings();

        mAdapter = new WordListAdapter(this, mydbAllReadings);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper helper= new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Reading myItem = mAdapter.getReadingAtPosition(position);
                        Toast.makeText(ReadingsActivity.this, "Deleting Reading", Toast.LENGTH_LONG).show();
                        // Delete the word
                        mydb.deleteReading(myItem.id);
                        mAdapter.setReadings(mydb.getAllReadings());
                        //mAdapter.notifyDataSetChanged();
                    }

                });
        helper.attachToRecyclerView(mRecyclerView);

    }

    private ArrayList<String> useDummyData() {
        ArrayList<String> dummyData = new ArrayList<String>();

        for (int i = 0; i < 20; i++) {
            dummyData.add("Dummy Data N: " + i);
        }

        return dummyData;
    }

    private void createDbDummyData() {

        mydb.insertReading("6:03 PM 11/2/2020", "97.3");
        mydb.insertReading("6:23 PM 11/4/2020", "99.3");
        mydb.insertReading("7:45 PM 11/22/2020", "100.3");
        mydb.insertReading("4:42 PM 11/4/2020", "92.3");

    }
}