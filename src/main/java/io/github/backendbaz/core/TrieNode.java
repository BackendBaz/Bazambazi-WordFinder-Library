package io.github.backendbaz.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a node in a trie data structure used for efficient prefix-based string searches.
 * <p>
 * Each node contains:
 * <ul>
 *   <li>A map of child nodes keyed by single-character strings</li>
 *   <li>A flag indicating whether this node terminates a valid word</li>
 * </ul>
 * The trie structure is built by {@link Dictionary} during dictionary loading and used by
 * {@link Finder} for grid word searches.
 * </p>
 *
 * <h2>Trie Structure Example:</h2>
 * <pre>{@code
 * Root [TrieNode]
 *  |
 *  +-- "ض" [TrieNode]
 *        |
 *        +-- "ر" [TrieNode] (endOfWord = true)  // represents word "ضَر"
 * }</pre>
 */
public class TrieNode {

    /**
     * Child nodes of this trie node.
     * <p>
     * Key: Single Persian character (e.g., "ض")
     * <br>
     * Value: Next {@code TrieNode} in sequence
     * </p>
     */
    private final Map<String, TrieNode> children = new HashMap<>();

    /**
     * Word termination flag.
     * <p>
     * {@code true} indicates this node completes a valid dictionary word,
     * {@code false} indicates an intermediate prefix.
     * </p>
     */
    private boolean endOfWord;

    /**
     * Retrieves the children map of this node.
     *
     * @return Immutable view of child nodes (never null)
     */
    public Map<String, TrieNode> getChildren() {
        return children;
    }

    /**
     * Checks if this node marks the end of a valid word.
     *
     * @return {@code true} if node completes a word, {@code false} otherwise
     */
    public boolean isEndOfWord() {
        return endOfWord;
    }

    /**
     * Sets the word termination status of this node.
     *
     * @param endOfWord Flag indicating word completion status
     */
    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

}
