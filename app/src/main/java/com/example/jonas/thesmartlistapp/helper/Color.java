package com.example.jonas.thesmartlistapp.helper;

import com.example.jonas.thesmartlistapp.R;

public class Color {
    String mColor;

    public Color() {

    }

    public static int getColors(int colorNumber){
        switch(colorNumber){
            case 0:
                return R.color.colorBlueMain;
            case 1:
            return R.color.colorBlueHighlited;
            case 2:
                return R.color.colorBlack;
            case 3:
                return R.color.colorPrimary;
            case 4:
                return R.color.colorWhite;
            case 5:
                return R.color.colorAccent;
        }
        return 1;
    }
}
