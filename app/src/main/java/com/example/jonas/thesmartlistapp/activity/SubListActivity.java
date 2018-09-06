package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.fragment.CategoryFragment;
import com.example.jonas.thesmartlistapp.helper.Color;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, CategoryFragment.ActivityCommunicator {

    private Toolbar toolbar;
    private String mCategoryId;
    private int mCategoryColor;

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
        final RecyclerViewAdapter adapterVertical = new RecyclerViewAdapter(this);
        verticalRecyclerView.setAdapter(adapterVertical);

        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterVertical.setClickListener(this);


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
                Collections.sort(words, new Comparator<Word>() {
                    @Override
                    public int compare(Word c1, Word c2) {
                        //You should ensure that list doesn't contain null values!
                        return c1.getCategoryId().compareTo(c2.getCategoryId());
                    }
                });
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

        final ItemTouchHelper.SimpleCallback helper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    int position = viewHolder.getAdapterPosition();
                    Word myWord = adapterVertical.getWordAtPosition(position);
                    listViewModel.deleteWord(myWord);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helper);
        itemTouchHelper.attachToRecyclerView(verticalRecyclerView);
    }

    public void startCategoryFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.your_placeholder, new CategoryFragment());
        ft.commit();
    }

    public void saveData(String word, String id) {
        if(mCategoryId == null){
            mCategoryId = Constants.DEFAULT_CATEGORY;
            mCategoryColor = Constants.DEFAULT_CATEGORY_NUMBER;
        }
        Word setWord = new Word(word, null, id, null, mCategoryId, mCategoryColor);
        listViewModel.insert(setWord);
    }

    @Override
    public void onItemClick(View view, int position, Word word) {
        Toaster.showShortToastMethod(view.getContext(), word.getOwnerId());
    }

    @Override
    public void passDataToActivity(View view, Word word) {
        mCategoryId = word.getWord();
        mCategoryColor = word.getColorCategory();
        mCategoryButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), Color.getColors(word.getColorCategory()))));
    }
}
