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

    //If it is created as a LIST
    @ColumnInfo(name = "list")
    private String mList = Constants.LIST;

    //If it is created as an ITEM inside a list
    @ColumnInfo(name = "ownerId")
    private String mOwnerId;

    //If it is created as a CATEGORY
    @ColumnInfo(name = "category")
    private String mCategory;

    //If it is an item with a type of category
    @ColumnInfo(name = "categoryId")
    private String mCategoryId;

    public Word(@NonNull String word, String list, String ownerId, String category, String categoryId) {
        this.mWord = word;
        this.mList = list;
        this.mOwnerId = ownerId;
        this.mCategory = category;
        this.mCategoryId = categoryId;
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String mCategoryId) {
        this.mCategoryId = mCategoryId;
    }
}
