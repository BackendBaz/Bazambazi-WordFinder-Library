package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;

import java.util.*;

public class Finder {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private final String[][] grid = new String[ROWS][COLS];
    private record FoundWord(String word, List<Point> path) {}

    public Finder(String input) throws InvalidLettersException {
        validateInput(input);
        initializeGrid(input.split(" "));
    }

    private void validateInput(String input) throws InvalidLettersException {
        if (input == null || input.isBlank())
            throw new InvalidLettersException("Input cannot be empty");
        String[] letters = input.split("\\s+");
        if (letters.length != ROWS * COLS)
            throw new InvalidLettersException("Exactly " + (ROWS * COLS) +
                    " letters required");
        for (String letter : letters)
            if (!isValidPersianLetter(letter))
                throw new InvalidLettersException("Invalid Persian letter: " +
                        letter);
    }

    private void initializeGrid(String[] letters) {
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = letters[index++];
            }
        }
    }

    public List<Word> findTopWords(Dictionary dictionary, int topN) {
        Set<FoundWord> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                searchWords(dictionary.getTrieRoot(), i, j, visited,
                        new StringBuilder(), new ArrayList<>(), foundWords);
            }
        }
        return foundWords.stream()
                .map(foundWord -> new Word(
                        foundWord.word(),
                        dictionary.getPoint(foundWord.word()),
                        foundWord.path()
                ))
                .sorted(Comparator.comparingLong(Word::point).reversed())
                .limit(topN)
                .toList();
    }

    private void searchWords(TrieNode node, int i, int j,
            boolean[][] visited, StringBuilder currentWord,
                             List<Point> currentPath, Set<FoundWord> results) {
        if (i < 0 || i >= ROWS || j < 0 || j >= COLS || visited[i][j]) return;
        String letter = grid[i][j];
        TrieNode nextNode = node.getChildren().get(letter);
        if (nextNode == null) return;
        visited[i][j] = true;
        currentWord.append(letter);
        currentPath.add(new Point(i, j));
        if (nextNode.isEndOfWord() && currentWord.length() >= 2 &&
                results.stream().noneMatch(word -> word.word()
                        .contentEquals(currentWord)))
            results.add(new FoundWord(currentWord.toString(),
                    new ArrayList<>(currentPath)));
        // 8 جهت : بالا ، چپ ، راست ، پایین و مورب ها (4)
        int[][] directions = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            searchWords(nextNode, ni, nj, visited, currentWord, currentPath,
                    results);
        }
        // Backtracking:
        visited[i][j] = false;
        currentWord.deleteCharAt(currentWord.length() - 1);
        currentPath.removeLast();
    }

    public static boolean isValidPersianLetter(String letter) {
        return letter != null && letter.length() == 1
                && "ضصثقفغعهخحجچشسیبلاتنمکگپظطزرذدوژ".contains(letter);
    }

}
