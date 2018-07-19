package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.fragment.CategoryFragment;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.List;

public class SubListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RecyclerViewAdapter adapterVertical, adapter2;
    private ListViewModel listViewModel;
    FloatingActionButton mAddItemButton, mCategoryButton;
    EditText addItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String listTitle = getIntent().getExtras().getString("list_title");
        final String wordId = getIntent().getExtras().getString("id");

        setContentView(R.layout.activity_sub_list);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(listTitle);
        setSupportActionBar(toolbar);

        mAddItemButton = findViewById(R.id.add_item_button);
        addItemText = findViewById(R.id.add_item_text);
        mCategoryButton = findViewById(R.id.category_button);

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

        listViewModel.getList(wordId).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapterVertical.setWords(words);
                //adapter2.setWords(words);
            }
        });

        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItemText.getText().toString().isEmpty()) {
                    Toaster.showLongToastMethod(getApplicationContext(), getString(R.string.toast_write_something));
                } else {
                    saveData(addItemText.getText().toString(), wordId);
                    addItemText.getText().clear();
                }
            }
        });
        mCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategoryFragment();
            }
        });
    }

    public void startCategoryFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new CategoryFragment());
        ft.commit();
    }

    public void saveData(String word, String id) {
        Word setWord = new Word(word, null, id, null, null);
        listViewModel.insert(setWord);
    }
}
