package com.example.jonas.thesmartlistapp.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.activity.CreateCategoryActivity;
import com.example.jonas.thesmartlistapp.adapter.ColorAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.helper.Toaster;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.List;

public class ColorFragment extends Fragment implements ColorAdapter.ItemClickListener{

    ColorAdapter adapter;
    private ListViewModel listViewModel;

    public ColorFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_color, container, false);

        //ViewModel
        listViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        // set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.colorRV);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),Constants.SPAN_COUNT));
        adapter = new ColorAdapter(getActivity());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        listViewModel.getColors(getContext()).observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> list) {
                adapter.setColors(list);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toaster.showShortToastMethod(view.getContext(), adapter.getItem(position));
    }

}
