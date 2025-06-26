package io.github.backendbaz.core;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    private final Map<String, TrieNode> children = new HashMap<>();
    private boolean endOfWord;

    public Map<String, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

}
