package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.DictionaryFileException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private final List<Word> words = new ArrayList<>();

    public List<Word> getWords(String filePath) throws DictionaryFileException,
            IOException, ParseException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path) ||
                !Files.isReadable(path))
            throw new DictionaryFileException("Dictionary file not found or " +
                    "is not readable");
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            // parse the JSON file:
            Object obj = parser.parse(reader);
            // cast the parsed object to a JSONArray:
            JSONArray jsonArray = (JSONArray) obj;
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                // access data from the JSONObject:
                String word = (String) jsonObject.get("word");
                long point = (long) jsonObject.get("point");
                words.add(new Word(word, point));
            }
        }
        return words;
    }

}
