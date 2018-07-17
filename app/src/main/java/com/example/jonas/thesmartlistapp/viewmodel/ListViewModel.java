package com.example.jonas.thesmartlistapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.repository.Repository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<Word>> mAllWords;

    public ListViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllWords = mRepository.getAllLists();
    }

    public LiveData<List<Word>> getAllLists() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }
}
