package com.example.jonas.thesmartlistapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.R;
import com.example.jonas.thesmartlistapp.activity.MainListActivity;
import com.example.jonas.thesmartlistapp.constants.Constants;
import com.example.jonas.thesmartlistapp.viewmodel.ListViewModel;
import com.google.gson.Gson;

public class MyAlertDialog extends DialogFragment {
    private ListViewModel listViewModel;

    public MyAlertDialog(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainListActivity activity = (MainListActivity) getActivity();
        Bundle mArgs = getArguments();
        String wordFromActivity = mArgs.getString(Constants.WORD);
        final Word word = new Gson().fromJson(wordFromActivity, Word.class);
        listViewModel = ViewModelProviders.of(activity).get(ListViewModel.class);

        return new android.app.AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.deleting) + " " + word.getWord())
                .setMessage(getResources().getString(R.string.deleting_are_you_sure) +  " "  + word.getWord() + " ?")
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    listViewModel.deleteCategoryWords(word.getWord());
                    listViewModel.deleteWord(word);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
