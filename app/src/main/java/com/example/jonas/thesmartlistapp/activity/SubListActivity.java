package com.example.jonas.thesmartlistapp.activity;

import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.fragment.CategoryFragment;
import com.example.jonas.thesmartlistapp.helper.Color;
import com.example.jonas.thesmartlistapp.helper.SortWords;
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

        listViewModel.getList(wordId).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                SortWords.getSortedWords(words);
                adapterVertical.setWords(words);
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
                hideKeyboard(v);
                startCategoryFragment();
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
                float buttonWidthWithoutPadding = Constants.BUTTON_WIDTH_DELETE_DRAW - 20;
                float corners = 0;

                View itemView = viewHolder.itemView;
                Paint p = new Paint();

                RectF rightButton = new RectF(itemView.getRight() - buttonWidthWithoutPadding, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                p.setColor(android.graphics.Color.RED);
                c.drawRoundRect(rightButton, corners, corners, p);
                drawText(getResources().getString(R.string.delete), c, rightButton, p);
            }

            private void drawText(String text, Canvas c, RectF button, Paint p) {
                float textSize = 60;
                p.setColor(android.graphics.Color.WHITE);
                p.setAntiAlias(true);
                p.setTextSize(textSize);

                float textWidth = p.measureText(text);
                c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
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
        FragmentManager fm = getFragmentManager();
        CategoryFragment dialogFragment = new CategoryFragment();
        dialogFragment.show(fm, "Category Fragment");
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
        hideKeyboard(view);
    }

    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    @Override
    public void passDataToActivity(View view, Word word) {
        mCategoryId = word.getWord();
        mCategoryColor = word.getColorCategory();
        mCategoryButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), Color.getColors(word.getColorCategory()))));
    }
}
