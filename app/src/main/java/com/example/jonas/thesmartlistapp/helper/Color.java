package com.example.jonas.thesmartlistapp.helper;

import com.example.jonas.thesmartlistapp.R;

public class Color {
    String mColor;

    public Color() {

    }

    public static int getColors(int colorNumber){
        switch(colorNumber){
            case 0:
                return R.color.colorYellow;
            case 1:
                return R.color.colorBlue;
            case 2:
                return R.color.colorRed;
            case 3:
                return R.color.colorBlack;
            case 4:
                return R.color.colorOrange;
            case 5:
                return R.color.colorPurple;
            case 6:
                return R.color.colorWhite;
            case 7:
                return R.color.colorGreen;
        }
        return 0;
    }
}
