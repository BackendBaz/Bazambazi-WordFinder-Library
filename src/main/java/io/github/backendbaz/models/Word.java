package io.github.backendbaz.models;

import io.github.backendbaz.core.Point;
import java.util.List;

/**
 * Represents a word found in a word search puzzle with its associated metadata.
 * <p>
 * This immutable record encapsulates a discovered word, its calculated point value,
 * and the precise path through the puzzle grid where the word is located. Typically
 * used in word search game implementations to store and process solution elements.
 * </p>
 *
 * @param word  The discovered word text (case-sensitive, non-blank)
 * @param point The point value calculated for the word (non-negative)
 * @param path  The sequence of grid coordinates tracing the word's location (non-null, non-empty)
 *
 * @see io.github.backendbaz.core.Point
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 2.0.0
 */
public record Word(String word, long point, List<Point> path) {}
