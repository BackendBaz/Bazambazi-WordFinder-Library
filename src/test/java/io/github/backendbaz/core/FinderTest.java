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

}
