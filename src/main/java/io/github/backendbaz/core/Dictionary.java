package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.DictionaryFileException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private final Map<String, Long> wordMap = new HashMap<>();
    private final TrieNode trieRoot = new TrieNode();

    public Dictionary load(String resourcePath) throws DictionaryFileException,
            IOException, ParseException {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        // اگر با مسیر نسبی پیدا نشد
        if (inputStream == null) {
            inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath.startsWith("/")
                    ? resourcePath.substring(1)
                    : resourcePath);
        }
        // اگر هنوز فایل پیدا نشد
        if (inputStream == null)
            throw new DictionaryFileException("Dictionary file not found");
        JSONParser parser = new JSONParser();
        try (InputStreamReader reader = new InputStreamReader(inputStream,
                StandardCharsets.UTF_8)) {
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                String word = (String) jsonObj.get("word");
                long point = (long) jsonObj.get("point");
                wordMap.put(word, point);
                insertIntoTrie(word);
            }
        }
        return this;
    }

    private void insertIntoTrie(String word) {
        TrieNode current = trieRoot;
        for (char c : word.toCharArray()) {
            String ch = String.valueOf(c);
            current = current.getChildren().computeIfAbsent(ch, k ->
                    new TrieNode());
        }
        current.setEndOfWord(true);
    }

    public TrieNode getTrieRoot() {
        return trieRoot;
    }

    public Long getPoint(String word) {
        return wordMap.get(word);
    }
}

class TrieNode {

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
