package com.example.jonas.thesmartlistapp.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.activity.CreateCategoryActivity;
import com.example.jonas.thesmartlistapp.activity.CreateListActivity;
import com.example.jonas.thesmartlistapp.adapter.RecyclerViewAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.List;

public class CategoryFragment extends Fragment implements View.OnClickListener {

    FragmentActivity listener;
    Button mAddNewItemButton;
    RecyclerViewAdapter adapter;
    private ListViewModel listViewModel;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        mAddNewItemButton = rootView.findViewById(R.id.add_new_category);
        mAddNewItemButton.setOnClickListener(this);

        //ViewModel
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.categoryRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        listViewModel.getCategory(Constants.CATEGORY).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_new_category){
            Intent intent = new Intent(getContext(), CreateCategoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    public interface OnFragmentInteractionListener {
    }
}
