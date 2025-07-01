package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;
import io.github.backendbaz.models.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinderTest {

    @Test
    @DisplayName("If input is null, throws an InvalidLettersException")
    public void finderConstructor_inputIsNull_ThrowsInvalidLettersException() {
        InvalidLettersException exp = assertThrows(InvalidLettersException.class,
                () -> new Finder(null));
        assertTrue(exp.getMessage().contains("Input cannot be null or empty"));
    }

    @Test
    @DisplayName("If input is blank or empty, throws an InvalidLettersException")
    public void finderConstructor_inputIsBlankOrEmpty_ThrowsInvalidLettersException() {
        InvalidLettersException exp = assertThrows(InvalidLettersException.class,
                () -> new Finder(" "));
        assertTrue(exp.getMessage().contains("Input cannot be null or empty"));
    }

    @Test
    @DisplayName("If input is not valid, throws an InvalidLettersException")
    public void finderConstructor_inputIsNotValid_ThrowsInvalidLettersException() {
        InvalidLettersException exp = assertThrows(InvalidLettersException.class,
                () -> new Finder("س ل ف ب ر"));
        assertTrue(exp.getMessage().contains("Exactly " + 16 + " letters required"));
    }

    @Test
    @DisplayName("If input has invalid letters, throws an InvalidLettersException")
    public void finderConstructor_inputHasInvalidLetters_ThrowsInvalidLettersException() {
        String invalidLetter = "e";
        InvalidLettersException exp = assertThrows(InvalidLettersException.class,
                () -> new Finder("س ل ف ب ر " + invalidLetter + " ب ف غ ا ذ د ت ا د ب"));
        assertTrue(exp.getMessage().contains("Invalid Persian letter: " + invalidLetter));
    }

    @Test
    @DisplayName("Get 3 high-score words from dictionary words")
    public void findTopWords_get3HighScoreWords_returnsListOfWords() throws IOException {
        var dictionary = new Dictionary().load(Dictionary.PATH);
        List<Word> allWords = getWords();
        assertEquals(new Finder("ا ب ر ظ ظ ظ ظ ظ ظ ظ ظ ظ ظ پ د ر")
                        .findTopWords(dictionary, 3, "همه"),
                allWords, "List of words not matched");
    }

    private static List<Word> getWords() {
        List<Point> points1 = new ArrayList<>();
        points1.add(new Point(0, 0));
        points1.add(new Point(0, 1));
        points1.add(new Point(0, 2));
        List<Point> points2 = new ArrayList<>();
        points2.add(new Point(3, 1));
        points2.add(new Point(3, 2));
        points2.add(new Point(3, 3));
        List<Point> points3 = new ArrayList<>();
        points3.add(new Point(0, 2));
        points3.add(new Point(0, 1));
        points3.add(new Point(0, 0));
        List<Word> allWords = new ArrayList<>();
        allWords.add(new Word("پدر", 4, points2));
        allWords.add(new Word("ابر", 3, points1));
        allWords.add(new Word("ربا", 3, points3));
        return allWords;
    }

    @Test
    @DisplayName("Get a high-score word from dictionary words")
    public void findTopWords_getAHighScoreWord_returnsListOfWords() throws IOException {
        var dictionary = new Dictionary().load(Dictionary.PATH);
        List<Point> points = new ArrayList<>();
        points.add(new Point(3, 1));
        points.add(new Point(3, 2));
        points.add(new Point(3, 3));
        List<Word> allWords = new ArrayList<>();
        allWords.add(new Word("پدر", 4, points));
        assertEquals(new Finder("ا ب ر ظ ظ ظ ظ ظ ظ ظ ظ ظ ظ پ د ر")
                        .findTopWords(dictionary, 1, "abc"),
                allWords, "List of words not matched");
    }

    @Test
    @DisplayName("Get a high-score word from dictionary words by high-score-letter filtering")
    public void findTopWords_getAHighScoreWordByHighScoreLetterFiltering_returnsListOfWords() throws IOException {
        var dictionary = new Dictionary().load(Dictionary.PATH);
        List<Point> points = new ArrayList<>();
        points.add(new Point(3, 1));
        points.add(new Point(3, 2));
        points.add(new Point(3, 3));
        List<Word> allWords = new ArrayList<>();
        allWords.add(new Word("پدر", 4, points));
        assertEquals(new Finder("ا ب ر ظ ظ ظ ظ ظ ظ ظ ظ ظ ظ پ د ر")
                        .findTopWords(dictionary, 3, "14"),
                allWords, "List of words not matched");
    }

}
