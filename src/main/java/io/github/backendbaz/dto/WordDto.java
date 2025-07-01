package io.github.backendbaz.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) representing a word with its associated point value.
 * <p>
 * This immutable record is designed to transfer word-score data between application layers,
 * particularly useful for JSON serialization/deserialization in REST APIs. It leverages
 * Jackson annotations for seamless integration with JSON processing frameworks.
 * </p>
 *
 * @param word  The word text (non-null, case sensitivity depends on usage context)
 * @param point The point value associated with the word (non-negative)
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 2.0.0
 */
public record WordDto(String word, long point) {

    /**
     * Constructs a {@code WordDto} instance for JSON deserialization.
     * <p>
     * This constructor is annotated with Jackson annotations to map JSON properties
     * to record components during deserialization. Performs validation to ensure:
     * </p>
     * <ul>
     *   <li>{@code word} is not null</li>
     *   <li>{@code point} is non-negative</li>
     * </ul>
     *
     * @param word  JSON property "word" mapped to record component
     * @param point JSON property "point" mapped to record component
     *
     * @throws NullPointerException if {@code word} is null
     * @throws IllegalArgumentException if {@code point} is negative
     *
     * @example Example JSON input:
     * <pre>
     * {
     *   "word": "عمادی",
     *   "point": 6
     * }
     * </pre>
     */
    @JsonCreator
    public WordDto(@JsonProperty("word") String word,
                @JsonProperty("point") long point) {
        if (word == null || word.isBlank())
            throw new IllegalArgumentException("word of dictionary file cannot " +
                    "be null or empty");
        if (point <= 0) throw new IllegalArgumentException("point must be " +
                "positive number");
        this.word = word;
        this.point = point;
    }

}
