package com.example.jonas.thesmartlistapp.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    //Creating a word (you can also edit and delete a word)
    @Insert
    void insert(Word word);

    //delete ALL words at once
    @Query("DELETE FROM word_table")
    void deleteAll();


    //Get all words with a special id
    @Query("SELECT * from word_table WHERE ownerId = :id ORDER BY word ASC")
    LiveData<List<Word>> getList(String id);


    //Get all words in an order
    @Query("SELECT * from word_table WHERE list = :list ORDER BY word ASC")
    LiveData<List<Word>> getAllWords(String list);
}
