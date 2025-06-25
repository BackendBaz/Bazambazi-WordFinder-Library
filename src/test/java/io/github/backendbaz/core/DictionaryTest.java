package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.DictionaryFileException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DictionaryTest {

    @Test
    @Tag("Dictionary:getWords")
    @DisplayName("Throws the DictionaryFileException if path of " +
            "file not exists or is not readable or is not a valid path")
    public void getWords_WhenFileNotExistsOrNotValidOrNotReadablePath_ShouldThrowsDictionaryFileException() {
        assertThatThrownBy(() -> new Dictionary()
                .getWords("/src/main/resources/dictionary/words.json"))
                .isInstanceOf(DictionaryFileException.class)
                .hasMessageContaining("Dictionary file not found or is not readable");
    }

    @Test
    @Tag("Dictionary:getWords")
    @DisplayName("Returns the list of words from JSON file")
    public void getWords_WhenFileExistsAndOk_ShouldReturnsWordsList() throws IOException, ParseException {
        List<Word> words = new ArrayList<>();
        words.add(new Word("ابتنی", 8));
        words.add(new Word("ابانبار", 7));
        words.add(new Word("اببها", 5));
        words.add(new Word("ابجو", 3));
        assertThat(new Dictionary()
                .getWords("./src/main/resources/dictionary/words.test.json"))
                .isEqualTo(words);
    }

}
