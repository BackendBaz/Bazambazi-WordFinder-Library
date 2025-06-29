package io.github.backendbaz.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a word entry with its metadata, including point value and formation path.
 * <p>
 * This record is used in two primary contexts:
 * <ol>
 *   <li><b>Dictionary storage:</b> Loaded from JSON (word and point only)</li>
 *   <li><b>Search results:</b> Enhanced with path data during grid traversal</li>
 * </ol>
 *
 * <h2>JSON Structure Example:</h2>
 * <pre>{@code
 * {
 *   "word": "ضرب",
 *   "point": 5
 * }
 * }</pre>
 *
 * <h2>Search Result Example:</h2>
 * <pre>{@code
 * Word[
 *   word = "ضرب",
 *   point = 5,
 *   path = [Point[row=0, col=0], Point[row=1, col=1], ...]
 * ]
 * }</pre>
 *
 * @param word  Persian word (non-null)
 * @param point Numerical value assigned to the word
 * @param path  Sequence of grid coordinates forming the word (null in dictionary entries)
 */
public record Word(String word, long point, List<Point> path) {

    /**
     * Custom constructor for Jackson deserialization.
     * <p>
     * Used exclusively when loading dictionary entries from JSON. Note that
     * the {@code path} parameter is always {@code null} in this context.
     * </p>
     *
     * @param word  Word text from JSON
     * @param point Point value from JSON
     * @param path  Ignored path parameter (required for record structure)
     */
    @JsonCreator
    public Word(@JsonProperty("word") String word,
                @JsonProperty("point") long point,
                List<Point> path) { // 'path' not in JSON
        this.word = word;
        this.point = point;
        this.path = path; // Null for dictionary entries
    }

}
