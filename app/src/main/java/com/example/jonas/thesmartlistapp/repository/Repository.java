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

    public Repository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
    }

    public LiveData<List<Word>> getAllLists(String list) {
        return mWordDao.getAllWords(list);
    }

    public LiveData<List<Word>> getCategory(String category) {
        return mWordDao.getCategory(category);
    }

    public LiveData<List<Word>> getList(String id) {
        return mWordDao.getList(id);
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
