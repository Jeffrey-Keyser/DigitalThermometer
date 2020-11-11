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

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private ArrayList<Reading> mWordList;

    private final String highTemp = "#CD5C5C";
    private final String lightGray = "#C0C0C0";
    private final String darkGray = "#808080";
    private boolean alternateColor = true;

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

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


        // TODO: Get html formatting to work
        holder.wordItemView.setText(HtmlCompat.fromHtml("<h1>Time : </h1>", HtmlCompat.FROM_HTML_MODE_COMPACT) + df.format(mCurrent.time) +
                "\n\n" + "Temperature : " + mCurrent.temp.toString());

        if (mCurrent.temp > 100) {
            holder.wordItemView.setBackgroundColor(Color.parseColor(highTemp));
            holder.itemView.setBackgroundColor(Color.parseColor(highTemp));
        }
        else
        {
            if (alternateColor) {
                alternateColor = false;
                holder.wordItemView.setBackgroundColor(Color.parseColor(lightGray));
                holder.itemView.setBackgroundColor(Color.parseColor(lightGray));
            }
            else
            {
                alternateColor = true;
                holder.wordItemView.setBackgroundColor(Color.parseColor(darkGray));
                holder.itemView.setBackgroundColor(Color.parseColor(darkGray));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public Reading getReadingAtPosition(int position) {
        return mWordList.get(position);
    }

    void setReadings(ArrayList<Reading> readings) {
        mWordList = readings;
        notifyDataSetChanged();
    }

}
