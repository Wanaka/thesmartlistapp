package com.example.jonas.thesmartlistapp.helper;

import com.example.jonas.thesmartlistapp.DAO.Word;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortWords {

    public SortWords() {
    }

    public static int getSortedWords(List<Word> words){
        Collections.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word c1, Word c2) {
                return c1.getCategoryId().compareTo(c2.getCategoryId());
            }
        });
        return 0;
    }
}
