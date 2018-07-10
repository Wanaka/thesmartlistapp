package com.example.jonas.thesmartlistapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.example.jonas.thesmartlistapp.R;

public class CreateListActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(R.string.create_list_title);
        setSupportActionBar(toolbar);

        createButton = findViewById(R.id.closeButton);
    }



}
