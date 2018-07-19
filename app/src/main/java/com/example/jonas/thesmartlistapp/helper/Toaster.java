package com.example.jonas.thesmartlistapp.helper;

import android.content.Context;

public class Toaster {

    public static void showShortToastMethod(Context context, String text) {
        android.widget.Toast.makeText(context, text,
                android.widget.Toast.LENGTH_SHORT).show();
    }

    public static void showLongToastMethod(Context context, String text) {
        android.widget.Toast.makeText(context, text,
                android.widget.Toast.LENGTH_LONG).show();
    }
}
