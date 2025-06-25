package io.github.backendbaz.core;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private final List<Word> words = new ArrayList<>();

    public List<Word> getWords(String filePath) {
        return words;
    }

}
