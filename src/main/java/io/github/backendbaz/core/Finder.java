package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;
import io.github.backendbaz.models.Word;
import java.util.*;

/**
 * Provides word finding functionality in a 4x4 grid of Persian letters using
 * depth-first search (DFS) with backtracking.
 * <p>
 * The finder processes an input string of 16 space-separated Persian letters,
 * constructs a grid, and searches for valid words by traversing adjacent cells
 * (including diagonals). Words must:
 * </p>
 * <ul>
 *   <li>Exist in the provided {@link Dictionary}</li>
 *   <li>Be at least 2 characters long</li>
 *   <li>Follow valid traversal paths without reusing cells</li>
 * </ul>
 *
 * <h2>Operation Workflow:</h2>
 * <ol>
 *   <li><b>Initialization:</b> Validate input and build grid</li>
 *   <li><b>Search:</b> DFS traversal from every grid cell</li>
 *   <li><b>Ranking:</b> Sort found words by point value</li>
 * </ol>
 *
 * <h3>Example Usage:</h3>
 * <pre>{@code
 * String letters = "ض ص ث ق ف غ ع ه خ ح ج چ ش س ی ب";
 * Finder finder = new Finder(letters);
 * List<Word> topWords = finder.findTopWords(dictionary, 10);
 * }</pre>
 *
 * @author BackendBaz (Amirhossein Emadi)
 * @version 1.1.0
 * @see Dictionary
 * @see Point
 */
public class Finder {

    /** Fixed grid row count. */
    private static final int ROWS = 4;

    /** Fixed grid column count. */
    private static final int COLS = 4;

    /** 4x4 letter grid (row-major order). */
    private final String[][] grid = new String[ROWS][COLS];

    /** Internal record for tracking found words with their paths. */
    private record FoundWord(String word, List<Point> path) {}

    /**
     * Constructs a Finder instance with validated Persian letters.
     *
     * @param input Space-separated string of 16 Persian letters
     * @throws InvalidLettersException If:
     * <ul>
     *   <li>Input is null/empty</li>
     *   <li>Incorrect letter count (not 16)</li>
     *   <li>Contains invalid Persian characters</li>
     * </ul>
     */
    public Finder(String input) throws InvalidLettersException {
        validateInput(input);
        initializeGrid(input.split(" "));
    }

    /**
     * Validates input format and letter validity.
     *
     * @param input Raw letter string
     * @throws InvalidLettersException On validation failures
     */
    private void validateInput(String input) throws InvalidLettersException {
        if (input == null || input.isBlank())
            throw new InvalidLettersException("Input cannot be null or empty");

        String[] letters = input.split("\\s+");
        if (letters.length != ROWS * COLS)
            throw new InvalidLettersException("Exactly " + (ROWS * COLS) +
                    " letters required");

        for (String letter : letters)
            if (!isValidPersianLetter(letter))
                throw new InvalidLettersException("Invalid Persian letter: " +
                        letter);
    }

    /**
     * Initializes the grid from validated letters.
     *
     * @param letters Array of 16 valid Persian letters
     */
    private void initializeGrid(String[] letters) {
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = letters[index++];
            }
        }
    }

    /**
     * Finds top-scoring words in the grid using dictionary lookup.
     *
     * @param dictionary Preloaded word dictionary
     * @param topN Number of top results to return
     * @return Sorted list of top {@code topN} words by point value (descending),
     *         or empty list if none found
     */
    public List<Word> findTopWords(Dictionary dictionary, int topN) {
        Set<FoundWord> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                searchWords(dictionary.getTrieRoot(), i, j, visited,
                        new StringBuilder(), new ArrayList<>(), foundWords);
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

    /**
     * Recursive DFS implementation for word search.
     * <p>
     * Explores 8-directional neighbors (including diagonals) while:
     * </p>
     * <ul>
     *   <li>Tracking visited cells</li>
     *   <li>Building current word path</li>
     *   <li>Checking dictionary trie for valid prefixes</li>
     * </ul>
     *
     * @param node Current trie node
     * @param i Current row index
     * @param j Current column index
     * @param visited Grid visitation state
     * @param currentWord Accumulated characters
     * @param currentPath Accumulated cell coordinates
     * @param results Found word accumulator
     */
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

    /**
     * Validates single-character Persian letters.
     *
     * @param letter Character to validate
     * @return {@code true} if valid Persian letter, {@code false} otherwise
     */
    private boolean isValidPersianLetter(String letter) {
        return letter != null && letter.length() == 1
                && "ضصثقفغعهخحجچشسیبلاتنمکگپظطزرذدوژ".contains(letter);
    }

}
