package com.example.digitalthermometer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalthermometer.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private ArrayList<Reading> mWordList;

    //private final mColor highTemp = new mColor(220, 20, 60);
    private final String highTemp = "#CD5C5C";
    private final Context context;
    private LayoutInflater mInflater;

    public WordListAdapter(Context context, ArrayList<Reading> wordList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }


    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            Reading element = mWordList.get(mPosition);
            // Change the word in the mWordList.

            // TODO: Create popup dialog when database item is clicked
            //context.startActivity(new Intent(context, ViewReading.class));

            // TODO: Throws up a random dialog right now
            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) view.getContext());

            alertDialog.setTitle("Id: " + element.id);
            alertDialog.setMessage("Temp: " + element.temp + "\n" + "Time: " + element.time);
            alertDialog.setIcon(R.drawable.thermometer);

            alertDialog.setPositiveButton(
                    "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );

            alertDialog.show();
        }
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        Reading mCurrent = WordListAdapter.this.mWordList.get(position);
        holder.wordItemView.setText(mCurrent.temp + " - " + mCurrent.time);
        if (mCurrent.temp > 100) {
            holder.wordItemView.setBackgroundColor(Color.parseColor(highTemp));
            holder.itemView.setBackgroundColor(Color.parseColor(highTemp));
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public Reading getReadingAtPosition(int position) {
        return mWordList.get(position);
    }

    void setReadings(ArrayList<Reading> readings){
        mWordList = readings;
        notifyDataSetChanged();
    }
}
