package com.example.digitalthermometer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalthermometer.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private ArrayList<Reading> mWordList;

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

            element.temp = "I Clicked .. " + element.temp;
            mWordList.set(mPosition, element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();

            // TODO: Create popup dialog when database item is clicked
            context.startActivity(new Intent(context, ViewReading.class));

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
