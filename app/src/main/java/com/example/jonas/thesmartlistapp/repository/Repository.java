package com.example.jonas.thesmartlistapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.DAO.WordDao;
import com.example.jonas.thesmartlistapp.DAO.WordRoomDatabase;

import java.util.ArrayList;
import java.util.List;


public class Repository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;
    private MutableLiveData<List<Word>> fruitList;

    public Repository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();

        fruitList = new MutableLiveData<>();

        Word w = new Word("hello");

        List<Word> list = new ArrayList<>();

        list.add(w);

        fruitList.setValue(list);
        //set rv code
        // data to populate the RecyclerView with

    }

    public LiveData<List<Word>> getAllLists() {
        return mAllWords;
    }


    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }


    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
