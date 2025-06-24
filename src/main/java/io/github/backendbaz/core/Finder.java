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
        if (wordLength < 2 || wordLength > rows * cols)
            return false;
        if (!Finder.IsPersianWord(word)) return false;
        return true;
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
