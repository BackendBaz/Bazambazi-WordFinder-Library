package io.github.backendbaz.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.backendbaz.exceptions.DictionaryFileException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private final Map<String, Long> wordMap = new HashMap<>();
    private final TrieNode trieRoot = new TrieNode();

    public Dictionary load() throws DictionaryFileException,
            IOException {
        String resourcePath = "/dictionary/words.json";
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        if (inputStream == null)
            throw new DictionaryFileException("Dictionary file not found");
        ObjectMapper mapper = new ObjectMapper();
        try (InputStreamReader reader = new InputStreamReader(inputStream,
                StandardCharsets.UTF_8)) {
            var entries = mapper.readValue(reader,
                    new TypeReference<List<Word>>() {});
            for (Word entry : entries) {
                wordMap.put(entry.word(), entry.point());
                insertIntoTrie(entry.word());
            }
        } catch (Exception e) {
            throw new IOException("Failed to process the JSON file");
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
