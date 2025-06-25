package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.DictionaryFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private final List<Word> words = new ArrayList<>();

    public List<Word> getWords(String filePath) throws DictionaryFileException,
            IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path) ||
                !Files.isReadable(path))
            throw new DictionaryFileException("Dictionary file not found or is not readable");
        return words;
    }

}
