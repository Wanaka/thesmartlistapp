package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, View.OnClickListener{

    private Toolbar toolbar;
    private FloatingActionButton createListButton;
    RecyclerViewAdapter adapter;
    private ListViewModel listViewModel;

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
    }

    @Override
    public void onItemClick(View view, int position, Word word) {
        Intent intent = new Intent(this, SubListActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("list_title",adapter.getItem(position).getWord().toString());
        mBundle.putString("id",String.valueOf(adapter.getItem(position).getId()));
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
