package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

public class CreateCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    Button mAddCategory;
    EditText mCategoryText;
    private ListViewModel listViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddCategory = findViewById(R.id.category_button);
        mAddCategory.setOnClickListener(this);
        setContentView(R.layout.activity_create_category);
        setContentView(R.layout.activity_create_list);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(R.string.create_list_title);
        setSupportActionBar(toolbar);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);



        mCategoryText = findViewById(R.id.category_text);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.category_button){
            saveData(mCategoryText.getText().toString());
            finish();
        }
    }

    public void saveData(String word){
        Word setWord = new Word(word, null, null, Constants.CATEGORY, null);
        listViewModel.insert(setWord);
    }
}
