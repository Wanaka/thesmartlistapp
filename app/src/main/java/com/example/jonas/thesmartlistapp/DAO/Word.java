package com.example.jonas.thesmartlistapp.DAO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.jonas.thesmartlistapp.constants.Constants;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wordId")
    private int mId;

    @ColumnInfo(name = "word")
    private String mWord;

    @ColumnInfo(name = "list")
    private String mList = Constants.LIST;

    @ColumnInfo(name = "ownerId")
    private String mOwnerId;

    public Word(@NonNull String word, String list, String ownerId) {
        this.mWord = word;
        this.mList = list;
        this.mOwnerId = ownerId;
    }

    public String getWord(){return this.mWord;}

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getList() {
        return mList;
    }

    public void setList(String mList) {
        this.mList = mList;
    }

    public String getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(String mOwnerId) {
        this.mOwnerId = mOwnerId;
    }
}
