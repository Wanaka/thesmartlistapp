package com.example.jonas.thesmartlistapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, View.OnClickListener{

    private Toolbar toolbar;
    private FloatingActionButton createListButton;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        toolbar = findViewById(R.id.listToolbar);
        setSupportActionBar(toolbar);

        createListButton = findViewById(R.id.createListActionButton);
        createListButton.setOnClickListener(this);

        //set rv code
        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
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
