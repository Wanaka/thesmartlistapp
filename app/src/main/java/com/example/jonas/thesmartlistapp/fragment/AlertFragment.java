package com.example.jonas.thesmartlistapp.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.activity.MainListActivity;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;
import com.google.gson.Gson;


public class AlertFragment extends DialogFragment {
    private ListViewModel listViewModel;

    public  AlertFragment(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainListActivity activity = (MainListActivity) getActivity();
        Bundle mArgs = getArguments();
        String wordFromActivity = mArgs.getString(Constants.WORD);
        final Word word = new Gson().fromJson(wordFromActivity, Word.class);
        listViewModel = ViewModelProviders.of(activity).get(ListViewModel.class);

        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                /*.setIcon(android.R.drawable.stat_notify_error)*/
                // set Dialog Title
                .setTitle(getResources().getString(R.string.deleting) + " " + word.getWord())
                // Set Dialog Message
                .setMessage(getResources().getString(R.string.deleting_are_you_sure) +  " "  + word.getWord() + " ?")

                // positive button
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    listViewModel.deleteCategoryWords(word.getWord());
                    listViewModel.deleteWord(word);
                    }
                })
                // negative button
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
