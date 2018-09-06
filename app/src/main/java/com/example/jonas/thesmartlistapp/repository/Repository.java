package com.example.jonas.thesmartlistapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.DAO.WordDao;
import com.example.jonas.thesmartlistapp.DAO.WordRoomDatabase;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.helper.Color;

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

    public void deleteWord(Word word)  {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    public void deleteCategoryWords(String word)  {
        new deleteCategoryWordsAsyncTask(mWordDao).execute(word);
    }

    //Add a word
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

    //Delete a word
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }

    //Delete list
    private static class deleteCategoryWordsAsyncTask extends AsyncTask<String, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteCategoryWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteCategoryWords(params[0]);
            return null;
        }
    }


    // ***** Color DB *****
    public LiveData<List<Word>> getColor(Context context){
        MutableLiveData<List<Word>> fruitList = new MutableLiveData<>();
        List<Word> fruitsStringList = new ArrayList<>();

        //The colors are in strings and not from resources because it didnt work, got weird numbers instead...
        //String.valueOf(ContextCompat.getColor(context, R.color.colorAccent)
        fruitsStringList.add(new Word("#0" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#1" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#2" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#3" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#4" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#5" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#6" ,null ,null ,null ,null, 0));
        fruitsStringList.add(new Word("#7" ,null ,null ,null ,null, 0));

        fruitList.setValue(fruitsStringList);
        return fruitList;
    }
}
