package com.example.jonas.thesmartlistapp.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.helper.Toaster;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {


    private List<String> mColors;
    private LayoutInflater mInflater;
    private RecyclerViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    public ColorAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setColors(List<String> colors){
        mColors = colors;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.color_recyclerview_row, parent, false);
        return new ColorAdapter.ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String colorData = mColors.get(position);
        holder.img.setColorFilter(Integer.valueOf(colorData));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if(mColors == null){
            List<String> datalist = new ArrayList<>();
            return datalist.size();
        } else {
            return mColors.size();
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mColors.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ColorAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = (RecyclerViewAdapter.ItemClickListener) itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;

        ViewHolder(final View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.c_img);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
