package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FinderTest {

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the input is null")
    public void getLetters_WhenInputIsNull_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() -> new Finder(null).getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Input is null or blank");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the input is blank or empty")
    public void getLetters_WhenInputIsBlank_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() -> new Finder("").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Input is null or blank");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the letters array's length is invalid")
    public void getLetters_WhenLettersArrayLengthIsInvalid_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the letters array is invalid")
    public void getLetters_WhenLettersArrayIsInvalid_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا ب ف ظ a ل ذ ل ب س ش ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

    @Test
    @Tag("Finder:getLetters")
    @DisplayName("Throws the InvalidLettersException when the Persian uppercase 'a' letter exists in the letters array")
    public void getLetters_WhenPersianUppercaseAExistsInTheLettersArray_ShouldThrowsInvalidLettersException() {
        assertThatThrownBy(() ->
                new Finder("س ع ق ف ا ب ف ظ آ ل ذ ل ب س ش ا").getLetters())
                .isInstanceOf(InvalidLettersException.class)
                .hasMessageContaining("Some letters are not valid");
    }

}
