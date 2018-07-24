package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.fragment.CategoryFragment;
import com.example.jonas.thesmartlistapp.fragment.ColorFragment;
import com.example.jonas.thesmartlistapp.helper.Color;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

public class CreateCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    Button mAddCategory;
    FloatingActionButton mColorButton;
    EditText mCategoryText;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(R.string.create_list_title);
        setSupportActionBar(toolbar);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        mCategoryText = findViewById(R.id.category_text);
        mAddCategory = findViewById(R.id.category_button);
        mColorButton = findViewById(R.id.category_chose_color);
        mAddCategory.setOnClickListener(this);
        mColorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.category_button) {
            if (mCategoryText.getText().toString().isEmpty()) {
                Toaster.showLongToastMethod(getApplicationContext(), getString(R.string.toast_add_category));
            } else {
                saveData(mCategoryText.getText().toString());
                finish();
            }
        } else if(v.getId() == R.id.category_chose_color){
            startColorFragment();
        }
    }

    public void saveData(String word) {
        Word setWord = new Word(word, null, null, Constants.CATEGORY, null);
        listViewModel.insert(setWord);
    }

    public void startColorFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new ColorFragment());
        ft.commit();
    }

}
