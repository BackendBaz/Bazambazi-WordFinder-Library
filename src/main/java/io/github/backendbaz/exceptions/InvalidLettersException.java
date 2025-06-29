package io.github.backendbaz.exceptions;

/**
 * Exception thrown when invalid Persian letters are provided to the application.
 * <p>
 * This exception specifically indicates problems with input letter data, such as:
 * <ul>
 *   <li>Null or empty input</li>
 *   <li>Incorrect number of letters (not 16 for a 4x4 grid)</li>
 *   <li>Non-Persian characters in the input</li>
 * </ul>
 *
 * <h2>Typical Usage:</h2>
 * <pre>{@code
 * public void validateLetters(String input) {
 *   if (!isValid(input)) {
 *     throw new InvalidLettersException("Invalid letter: " + letter);
 *   }
 * }
 * }</pre>
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 1.1.0
 *
 * @see io.github.backendbaz.core.Finder
 */
public class InvalidLettersException extends RuntimeException {

    /**
     * Constructs a new exception with detailed error message.
     *
     * @param message Description of the validation failure. Should include:
     * <ul>
     *   <li>Specific invalid character(s)</li>
     *   <li>Nature of the error (missing, invalid, etc.)</li>
     * </ul>
     * Example: "Exactly 16 Persian letters required, found 12"
     */
    public InvalidLettersException(String message) {
        super(message);
    }

}
