package com.example.jonas.thesmartlistapp.activity;

import android.app.FragmentManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.fragment.AlertFragment;
import com.example.jonas.thesmartlistapp.fragment.CategoryFragment;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, View.OnClickListener{

    private Toolbar toolbar;
    private FloatingActionButton createListButton;
    RecyclerViewAdapter adapter;
    private ListViewModel listViewModel;
    private static final float buttonWidth = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        toolbar = findViewById(R.id.listToolbar);
        setSupportActionBar(toolbar);

        createListButton = findViewById(R.id.createListActionButton);
        createListButton.setOnClickListener(this);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);


        //set rv code
        // data to populate the RecyclerView with


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        listViewModel.getAllLists(Constants.LIST).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        final ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(Canvas c,
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                drawButtons(c, viewHolder);
            }
            private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder) {
                float buttonWidthWithoutPadding = buttonWidth - 20;
                float corners = 0;

                View itemView = viewHolder.itemView;
                Paint p = new Paint();

                RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                p.setColor(Color.RED);
                c.drawRoundRect(rightButton, corners, corners, p);
                drawText(getResources().getString(R.string.delete), c, rightButton, p);
            }

            private void drawText(String text, Canvas c, RectF button, Paint p) {
                float textSize = 60;
                p.setColor(Color.WHITE);
                p.setAntiAlias(true);
                p.setTextSize(textSize);

                float textWidth = p.measureText(text);
                c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    Word myWord = adapter.getWordAtPosition(position);
                    startAlertFragment(myWord);
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void startAlertFragment(Word word) {
        Bundle args = new Bundle();
        args.putString(Constants.WORD, new Gson().toJson(word));
        FragmentManager fm = getFragmentManager();
        AlertFragment dialogFragment = new AlertFragment ();
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "AlertFragment");
    }

    @Override
    public void onItemClick(View view, int position, Word word) {
        Intent intent = new Intent(this, SubListActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString(Constants.LIST_TITLE, adapter.getItem(position).getWord().toString());
        mBundle.putString(Constants.ID, String.valueOf(adapter.getItem(position).getWord())); //adapter.getItem(position).getId()
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        createListAction();
    }

    private void createListAction(){
        Intent intent = new Intent(this, CreateListActivity.class);
        startActivity(intent);
    }

}
