package io.github.backendbaz.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {

    @Test
    @DisplayName("Dictionary file not found")
    public void load_dictionaryFileNotFound_throwsIOException() {
        IOException exception = assertThrows(IOException.class, () ->
                new Dictionary().load("/dictionary/wrd.txt"),
                "It Should have thrown an IOException");
        assertTrue(
                exception.getMessage().contains("Dictionary file not found"),
                "Expected error message to contain 'Dictionary file not " +
                        "found' but was: " + exception.getMessage()
        );
    }

    @Test
    @DisplayName("JSON Dictionary file is not valid")
    public void load_jsonDictionaryFileIsNotValid_throwsIOException() {
        assertThrows(IOException.class, () ->
                new Dictionary().load("/dictionary/words.test.json"),
                "It Should have thrown an IOException");
    }

    @Test
    @DisplayName("Get words from the JSON dictionary file")
    public void load_getWordsFromDicFile_returnsDictionaryClass() throws IOException {
        assertEquals(6, new Dictionary().load(Dictionary.PATH)
                .getPoint("عمادی"));
    }

}
