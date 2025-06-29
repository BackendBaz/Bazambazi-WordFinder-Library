package io.github.backendbaz.core;

/**
 * Represents an immutable coordinate point in a two-dimensional grid.
 * <p>
 * This record is used to track cell positions (row and column) during grid traversal operations,
 * such as word finding in the {@link Finder} class.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Creating a point
 * Point p = new Point(2, 3);
 *
 * // Accessing components
 * int row = p.row();
 * int col = p.col();
 * }</pre>
 *
 * @param row 0-based row index (vertical position)
 * @param col 0-based column index (horizontal position)
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 1.1.0
 *
 * @see Finder
 */
public record Point(int row, int col) {
    // Implicitly contains:
    // - public final int row
    // - public final int col
    // - Constructor Point(int row, int col)
    // - Accessors row() and col()
    // - equals(), hashCode(), toString()
}
