package com.example.jonas.thesmartlistapp.singleton;

import com.example.jonas.thesmartlistapp.DAO.Word;
import com.example.jonas.thesmartlistapp.constants.Constants;

public class DefaultWhiteCategory {

    private static DefaultWhiteCategory ourInstance = null;

    private DefaultWhiteCategory() {}

    public static DefaultWhiteCategory getInstance(){
        if (ourInstance == null){
            ourInstance = new DefaultWhiteCategory();
        }
        return ourInstance;
    }

    public Word GetWhiteCategory (){
        return new Word("no category", null, null, Constants.CATEGORY, null, 7);
    }
}
