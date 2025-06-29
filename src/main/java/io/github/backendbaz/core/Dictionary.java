package io.github.backendbaz.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an in-memory dictionary loaded from a JSON resource, supporting word lookup
 * and prefix-based searches via a trie data structure.
 * <p>
 * The dictionary initializes by loading word entries from a JSON file where each entry
 * consists of a word and its associated point value. After loading, words are indexed
 * in both a hash map (for O(1) point lookups) and a trie (for efficient prefix searches).
 * </p>
 *
 * <h2>Typical Usage:</h2>
 * <pre>{@code
 * Dictionary dictionary = new Dictionary();
 * dictionary.load(Dictionary.PATH);
 *
 * // Get points for a word
 * Long points = dictionary.getPoint("example");
 *
 * // Access trie root for prefix searches
 * TrieNode root = dictionary.getTrieRoot();
 * }</pre>
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 1.1.0
 */
public class Dictionary {

    /**
     * Internal storage for word-point pairs.
     * Key: Word (case-sensitive)
     * Value: Associated point value
     */
    private final Map<String, Long> wordMap = new HashMap<>();

    /**
     * Root node of the trie structure used for prefix-based searches.
     */
    private final TrieNode trieRoot = new TrieNode();

    /**
     * Default path to the dictionary JSON resource in classpath.
     * <p>
     * Path format: {@code "/dictionary/words.json"}
     * </p>
     */
    public static final String PATH = "/dictionary/words.json";

    /**
     * Loads and parses dictionary data from the specified JSON resource.
     * <p>
     * The JSON file must contain an array of objects with fields:
     * {@code { "word": "عمادی", "point": 6 }}
     * </p>
     *
     * <h3>Loading Process:</h3>
     * <ol>
     *   <li>Opens resource stream from classpath</li>
     *   <li>Parses JSON using Jackson ObjectMapper</li>
     *   <li>Populates word-point map</li>
     *   <li>Builds trie structure for word prefixes</li>
     * </ol>
     *
     * @param resource Absolute path to JSON resource in classpath (e.g., {@value #PATH})
     * @return Current dictionary instance (fluent interface)
     * @throws IOException If the resource is not found or JSON parsing fails
     * @see Word
     */
    public Dictionary load(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        if (inputStream == null) throw new IOException("Dictionary file not found");
        ObjectMapper mapper = new ObjectMapper();
        try (InputStreamReader reader = new InputStreamReader(inputStream,
                StandardCharsets.UTF_8)) {
            var entries = mapper.readValue(reader,
                    new TypeReference<List<Word>>() {});
            for (Word entry : entries) {
                wordMap.put(entry.word(), entry.point());
                insertIntoTrie(entry.word());
            }
        }
        return this;
    }

    /**
     * Inserts a word into the trie structure character-by-character.
     * <p>
     * This method is automatically called during dictionary loading.
     * </p>
     *
     * @param word Word to insert into trie (non-null)
     */
    private void insertIntoTrie(String word) {
        TrieNode current = trieRoot;
        for (char c : word.toCharArray()) {
            String ch = String.valueOf(c);
            current = current.getChildren().computeIfAbsent(ch, k ->
                    new TrieNode());
        }
        current.setEndOfWord(true);
    }

    /**
     * Provides access to the root node of the constructed trie.
     * <p>
     * Used for traversing the trie to implement prefix-based searches
     * (e.g., finding all words starting with "app").
     * </p>
     *
     * @return Root node of the dictionary trie
     */
    public TrieNode getTrieRoot() {
        return trieRoot;
    }

    /**
     * Retrieves the point value associated with a word.
     *
     * @param word Target word (case-sensitive)
     * @return Point value if word exists, {@code null} otherwise
     */
    public Long getPoint(String word) {
        return wordMap.get(word);
    }

}
