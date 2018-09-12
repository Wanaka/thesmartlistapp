package com.example.jonas.thesmartlistapp.fragment;

import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.activity.CreateCategoryActivity;
import com.example.jonas.thesmartlistapp.adapter.ColorAdapter;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;

import java.util.List;

public class ColorFragment extends DialogFragment implements ColorAdapter.ItemClickListener{

    ColorAdapter adapter;
    Button mCloseButton;
    private ListViewModel listViewModel;
    private ActivityCommunicator activityCommunicator;
    CreateCategoryActivity activity;

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
        activity = (CreateCategoryActivity) getActivity();
        mCloseButton = rootView.findViewById(R.id.color_close_button);

        //ViewModel
        listViewModel = ViewModelProviders.of(activity).get(ListViewModel.class);

        // set up the RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.colorRV);
        recyclerView.setLayoutManager(new GridLayoutManager(activity,Constants.SPAN_COUNT));
        adapter = new ColorAdapter(getActivity());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        listViewModel.getColors(activity).observe(activity, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> list) {
                adapter.setColors(list);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
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
        activityCommunicator =(ActivityCommunicator)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //send data to parent activity
    public interface ActivityCommunicator{
        void passDataToActivity(View view, int color);
    }

    @Override
    public void onItemClick(View view, int position) {
        activityCommunicator.passDataToActivity(view, position);
        getDialog().dismiss();
    }
}
