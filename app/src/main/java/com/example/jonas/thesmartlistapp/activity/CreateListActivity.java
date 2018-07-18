package com.example.jonas.thesmartlistapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

public class CreateListActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Button createButton, closebutton;
    private EditText listName;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_list);
        toolbar = findViewById(R.id.listToolbar);
        toolbar.setTitle(R.string.create_list_title);
        setSupportActionBar(toolbar);

        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        createButton = findViewById(R.id.create);
        closebutton = findViewById(R.id.closeButton);
        listName = findViewById(R.id.inputListName);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter a name",
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData(listName.getText().toString());
                }
            }
        });
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveData(String word){
        Word setWord = new Word(word, Constants.LIST, null);
        listViewModel.insert(setWord);
        Intent intent = new Intent(this, MainListActivity.class);
        startActivity(intent);
    }
}
