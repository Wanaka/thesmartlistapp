package com.example.jonas.thesmartlistapp.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Word> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Word textData;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setWords(List<Word> words){
        mData = words;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        textData = mData.get(position);
        holder.myTextView.setText(textData.getWord());
        holder.categoryTextView.setText(textData.getCategoryId());
        holder.mCategoryColor.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), com.example.jonas.thesmartlistapp.helper.Color.getColors(textData.getColorCategory()))));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(mData == null){
            List<Word> datalist = new ArrayList<>();
            return datalist.size();
        } else {
            return mData.size();
        }
    }

    // convenience method for getting data at click position
    public Word getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Word word);
    }

    public Word getWordAtPosition (int position) {
        return mData.get(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView categoryTextView;
        ImageView mCategoryColor;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvListName);
            categoryTextView = itemView.findViewById(R.id.category_name);
            mCategoryColor = itemView.findViewById(R.id.category_color_rv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), mData.get(getAdapterPosition()));
        }
    }
}
