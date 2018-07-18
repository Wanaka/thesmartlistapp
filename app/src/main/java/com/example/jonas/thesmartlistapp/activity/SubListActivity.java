package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.List;

public class SubListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerViewAdapter adapterVertical, adapter2;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getIntent().getExtras().getString("list_title");
        setContentView(R.layout.activity_sub_list);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(value);
        setSupportActionBar(toolbar);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);


        RecyclerView verticalRecyclerView = findViewById(R.id.verticalRV);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterVertical = new RecyclerViewAdapter(this);
        verticalRecyclerView.setAdapter(adapterVertical);

        /*
        RecyclerView horizontalRecyclerView = findViewById(R.id.horizontalRV);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(SubListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter2 = new RecyclerViewAdapter(this);
        horizontalRecyclerView.setAdapter(adapter2);
        */

        listViewModel.getAllLists().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapterVertical.setWords(words);
                //adapter2.setWords(words);
            }
        });
    }
}
