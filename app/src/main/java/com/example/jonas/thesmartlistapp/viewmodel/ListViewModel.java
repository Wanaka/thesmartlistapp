package com.example.jonas.thesmartlistapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.repository.Repository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private Repository mRepository;

    public ListViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public LiveData<List<Word>> getAllLists(String list) {
        return mRepository.getAllLists(list);
    }

    public LiveData<List<Word>> getCategory(String category) {
        return mRepository.getCategory(category);
    }

    public LiveData<List<Word>> getList(String id) {
        return mRepository.getList(id);
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public LiveData<List<Word>> getColors(Context context) {
        return mRepository.getColor(context);
    }

}
