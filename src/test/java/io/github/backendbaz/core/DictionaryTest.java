package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.DictionaryFileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DictionaryTest {

    @Test
    @Tag("Dictionary:getWords")
    @DisplayName("Throws the DictionaryFileException if path of " +
            "file not exists or is not readable or is not a valid path")
    public void getWords_WhenFileNotExistsOrNotValidOrNotReadablePath_ShouldThrowsDictionaryFileException() throws IOException {
        assertThatThrownBy(() -> new Dictionary()
                .getWords("/src/main/resources/dictionary/words.json"))
                .isInstanceOf(DictionaryFileException.class)
                .hasMessageContaining("Dictionary file not found or is not readable");
    }

}
