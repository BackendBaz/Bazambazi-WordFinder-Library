package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;

import java.util.Arrays;

public class Finder {

    private int rows = 4;
    private int cols = 4;
    private final String input;

    public Finder(String input) {
        this.input = input;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public String[][] getLetters() throws InvalidLettersException {
        if (input == null || input.isBlank())
            throw new InvalidLettersException("Input is null or blank");
        String[] letters = input.split(" ");
        if (letters.length != rows * cols || !Arrays.stream(letters)
                .allMatch(Finder::IsPersianWord))
            throw new InvalidLettersException("Some letters are not valid");
        String[][] gridRowsCols = new String[rows][cols];
        int counterJ = 0;
        for (int i = 0; i < gridRowsCols.length; i++)
            for (int j = 0; j < gridRowsCols[i].length; j++) {
                gridRowsCols[i][j] = letters[counterJ];
                counterJ++;
            }
        return gridRowsCols;
    }

    public boolean exists(String[][] grid, String word) {
        int wordLength = word.length();
        if (wordLength < 2 || wordLength > rows * cols) return false;
        if (!Finder.IsPersianWord(word)) return false;
        Boolean[][][] memo = new Boolean[rows][cols][1 << (rows * cols)];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (grid[i][j].equals(word.substring(0, 1))) {
                    int initialMask = 1 << (i * cols + j);
                    if (dfs(grid, word, i, j, initialMask, memo)) return true;
                }
        return false;
    }

    private boolean dfs(String[][] grid, String word, int i, int j, int mask,
                        Boolean[][][] memo) {
        int charIndex = Integer.bitCount(mask) - 1;
        if (charIndex + 1 == word.length()) return true;
        if (memo[i][j][mask] != null) return memo[i][j][mask];
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
        boolean found = false;
        for (int[] dir : directions) {
            int ni = i + dir[0];
            int nj = j + dir[1];
            if (ni < 0 || ni >= rows || nj < 0 || nj >= cols) continue;
            int bitPos = ni * cols + nj;
            if ((mask & (1 << bitPos)) != 0) continue;
            String nextChar = grid[ni][nj];
            String expectedChar = word.substring(charIndex + 1, charIndex + 2);
            if (nextChar.equals(expectedChar)) {
                int newMask = mask | (1 << bitPos);
                found = dfs(grid, word, ni, nj, newMask, memo);
                if (found) break;
            }
        }
        memo[i][j][mask] = found;
        return found;
    }

    public static boolean IsPersianWord(String word) {
        if (word == null || word.isBlank()) return false;
        char[] letters = word.toCharArray();
        int counter = 0;
        for (char letter : letters) {
            switch (letter) {
                case 'ض':
                case 'ص':
                case 'ث':
                case 'ق':
                case 'ف':
                case 'غ':
                case 'ع':
                case 'ه':
                case 'خ':
                case 'ح':
                case 'ج':
                case 'چ':
                case 'ش':
                case 'س':
                case 'ی':
                case 'ب':
                case 'ل':
                case 'ا':
                case 'ت':
                case 'ن':
                case 'م':
                case 'ک':
                case 'گ':
                case 'پ':
                case 'ظ':
                case 'ط':
                case 'ز':
                case 'ژ':
                case 'ر':
                case 'ذ':
                case 'د':
                case 'و':
                    break;
                default:
                    counter++;
            }
        }
        return counter == 0;
    }

}
